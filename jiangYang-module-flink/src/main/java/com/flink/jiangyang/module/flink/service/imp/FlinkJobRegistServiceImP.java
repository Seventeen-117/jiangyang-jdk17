package com.flink.jiangyang.module.flink.service.imp;


import com.flink.jiangyang.module.flink.job.FlinkRestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlinkJobRegistServiceImP {
    private final FlinkRestClient flinkRestClient;

    @Value("${flink.jar-poll-interval:5000}")
    private long pollInterval;

    // 已注册的JAR缓存
    private final Map<String, FlinkRestClient.JarInfo> registeredJars = new ConcurrentHashMap<>();

    // 作业状态缓存
    private final Map<String, FlinkRestClient.JobStatus> jobStatusCache = new ConcurrentHashMap<>();

    // 作业提交历史
    private final Map<String, JobSubmission> jobSubmissions = new ConcurrentHashMap<>();

    /**
     * 初始化定时任务
     */
    @PostConstruct
    public void init() {
        log.info("Starting Flink job service with poll interval: {} ms", pollInterval);
        pollRegisteredJars();
    }

    /**
     * 定时轮询已注册的JAR
     */
    @Scheduled(fixedRateString = "${flink.jar-poll-interval:5000}")
    public void pollRegisteredJars() {
        try {
            List<FlinkRestClient.JarInfo> jars = flinkRestClient.getRegisteredJars();
            jars.forEach(jar -> registeredJars.put(jar.id(), jar));
            log.debug("Updated registered JARs cache. Count: {}", registeredJars.size());
        } catch (Exception e) {
            log.error("Error polling registered JARs", e);
        }
    }

    /**
     * 提交Flink作业
     */
    public JobSubmission submitJob(File jarFile, String entryClass, String... args) {
        // 上传JAR
        String jarId = flinkRestClient.uploadJar(jarFile);

        // 执行Job
        String jobId = flinkRestClient.executeJob(jarId, entryClass, args);

        // 创建提交记录
        JobSubmission submission = new JobSubmission(
                jobId,
                jarId,
                entryClass,
                Arrays.asList(args),
                System.currentTimeMillis()
        );

        jobSubmissions.put(jobId, submission);
        return submission;
    }

    /**
     * 获取所有已注册的JAR信息
     */
    public List<FlinkRestClient.JarInfo> getRegisteredJars() {
        return new ArrayList<>(registeredJars.values());
    }

    /**
     * 根据ID获取JAR信息
     */
    public FlinkRestClient.JarInfo getJarById(String jarId) {
        return registeredJars.get(jarId);
    }

    /**
     * 获取作业状态（带缓存）
     */
    public FlinkRestClient.JobStatus getJobStatus(String jobId) {
        // 检查缓存
        FlinkRestClient.JobStatus cachedStatus = jobStatusCache.get(jobId);
        if (cachedStatus != null &&
                System.currentTimeMillis() - cachedStatus.startTime() < 30000) {
            return cachedStatus;
        }

        // 刷新状态
        FlinkRestClient.JobStatus newStatus = flinkRestClient.getJobStatus(jobId);
        jobStatusCache.put(jobId, newStatus);
        return newStatus;
    }

    /**
     * 获取作业提交历史
     */
    public List<JobSubmission> getJobSubmissions() {
        return new ArrayList<>(jobSubmissions.values());
    }

    /**
     * 根据ID获取作业提交记录
     */
    public JobSubmission getJobSubmission(String jobId) {
        return jobSubmissions.get(jobId);
    }

    // 作业提交记录
    public record JobSubmission(
            String jobId,
            String jarId,
            String entryClass,
            List<String> arguments,
            long submitTime
    ) {}
}
