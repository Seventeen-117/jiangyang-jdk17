package com.flink.jiangyang.module.flink.job;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlinkRestClient {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final FlinkServiceDiscovery serviceDiscovery;

    @Value("${flink.job-timeout:300000}")
    private long jobTimeout;

    /**
     * 上传JAR文件到Flink集群
     */
    public String uploadJar(File jarFile) {
        String baseUrl = serviceDiscovery.getFlinkBaseUrl();
        log.info("Uploading JAR to Flink cluster at: {}", baseUrl);

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("jarfile", new FileSystemResource(jarFile));

        return webClient.post()
                .uri(baseUrl + "/jars/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> {
                    String filename = json.get("filename").asText();
                    return filename.substring(filename.lastIndexOf('/') + 1);
                })
                .doOnSuccess(jarId -> log.info("JAR uploaded successfully. ID: {}", jarId))
                .doOnError(e -> log.error("JAR upload failed", e))
                .block();
    }

    /**
     * 提交Job执行
     */
    public String executeJob(String jarId, String entryClass, String... args) {
        String baseUrl = serviceDiscovery.getFlinkBaseUrl();
        log.info("Executing job with JAR: {} on Flink cluster: {}", jarId, baseUrl);

        ObjectNode request = objectMapper.createObjectNode();
        request.put("entryClass", entryClass);
        request.put("programArgs", String.join(" ", args));

        return webClient.post()
                .uri(baseUrl + "/jars/{jarId}/run", jarId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> json.get("jobid").asText())
                .doOnSuccess(jobId -> log.info("Job started successfully. ID: {}", jobId))
                .doOnError(e -> log.error("Job execution failed", e))
                .block();
    }

    /**
     * 获取已注册的JAR列表
     */
    public List<JarInfo> getRegisteredJars() {
        String baseUrl = serviceDiscovery.getFlinkBaseUrl();
        log.debug("Fetching registered JARs from Flink cluster: {}", baseUrl);

        return webClient.get()
                .uri(baseUrl + "/jars")
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> {
                    JsonNode filesNode = json.get("files");
                    if (filesNode == null || filesNode.isNull()) {
                        return List.<JarInfo>of();
                    }
                    try {
                        return objectMapper.convertValue(filesNode,
                                objectMapper.getTypeFactory().constructCollectionType(List.class, JarInfo.class));
                    } catch (Exception e) {
                        log.warn("Failed to convert JAR files to JarInfo list: {}", e.getMessage());
                        return List.<JarInfo>of();
                    }
                })
                .doOnError(e -> log.error("Failed to fetch registered JARs", e))
                .onErrorReturn(List.of())
                .block();
    }

    /**
     * 获取Job状态
     */
    public JobStatus getJobStatus(String jobId) {
        String baseUrl = serviceDiscovery.getFlinkBaseUrl();
        log.debug("Fetching status for job: {} from Flink cluster: {}", jobId, baseUrl);

        return webClient.get()
                .uri(baseUrl + "/jobs/{jobId}", jobId)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> {
                    String state = json.get("state").asText();
                    String name = json.get("name").asText();
                    long startTime = json.get("start-time").asLong();

                    return new JobStatus(jobId, name, state, startTime);
                })
                .doOnError(e -> log.error("Failed to fetch job status", e))
                .onErrorReturn(new JobStatus(jobId, "UNKNOWN", "FAILED", 0))
                .block();
    }

    // 数据结构定义
    public record JarInfo(String id, String name, long uploaded, List<Entry> entry) {}
    public record Entry(String name, String description) {}
    public record JobStatus(String jobId, String name, String state, long startTime) {}
}