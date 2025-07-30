package com.flink.jiangyang.module.flink.controller;

import com.flink.jiangyang.module.flink.job.FlinkRestClient;
import com.flink.jiangyang.module.flink.service.imp.FlinkJobRegistServiceImP;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/flink")
@RequiredArgsConstructor
@Validated
public class FlinkController {

    private final FlinkJobRegistServiceImP flinkJobService;

    /**
     * 提交Flink作业
     */
    @PostMapping("/submit")
    public ResponseEntity<?> submitJob(
            @RequestParam("jar") MultipartFile jarFile,
            @RequestParam @NotBlank String entryClass,
            @RequestParam(required = false) String args) {

        try {
            // 创建临时文件
            Path tempDir = Files.createTempDirectory("flink-jars-");
            File tempFile = new File(tempDir.toFile(),
                    UUID.randomUUID() + "-" + jarFile.getOriginalFilename());

            // 保存上传的文件
            jarFile.transferTo(tempFile);

            // 提交作业
            FlinkJobRegistServiceImP.JobSubmission submission = flinkJobService.submitJob(
                    tempFile,
                    entryClass,
                    args != null ? args.split(" ") : new String[0]
            );

            // 返回提交结果
            return ResponseEntity.ok(Map.of(
                    "jobId", submission.jobId(),
                    "jarId", submission.jarId(),
                    "message", "Job submitted successfully"
            ));
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "File upload failed: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Job submission failed: " + e.getMessage()));
        }
    }

    /**
     * 获取已注册的JAR列表
     */
    @GetMapping("/jars")
    public ResponseEntity<List<FlinkRestClient.JarInfo>> getRegisteredJars() {
        return ResponseEntity.ok(flinkJobService.getRegisteredJars());
    }

    /**
     * 获取特定JAR信息
     */
    @GetMapping("/jars/{jarId}")
    public ResponseEntity<?> getJarInfo(@PathVariable String jarId) {
        FlinkRestClient.JarInfo jar = flinkJobService.getJarById(jarId);
        if (jar != null) {
            return ResponseEntity.ok(jar);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 获取作业状态
     */
    @GetMapping("/jobs/{jobId}/status")
    public ResponseEntity<?> getJobStatus(@PathVariable String jobId) {
        return ResponseEntity.ok(flinkJobService.getJobStatus(jobId));
    }

    /**
     * 获取作业提交历史
     */
    @GetMapping("/jobs")
    public ResponseEntity<List<FlinkJobRegistServiceImP.JobSubmission>> getJobSubmissions() {
        return ResponseEntity.ok(flinkJobService.getJobSubmissions());
    }

    /**
     * 获取特定作业提交记录
     */
    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<?> getJobSubmission(@PathVariable String jobId) {
        FlinkJobRegistServiceImP.JobSubmission submission = flinkJobService.getJobSubmission(jobId);
        if (submission != null) {
            return ResponseEntity.ok(submission);
        }
        return ResponseEntity.notFound().build();
    }
}