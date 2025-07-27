package com.flink.jiangyang.module.flink.utils;

import com.flink.jiangyang.module.flink.config.FlinkJobConfig;
import com.flink.jiangyang.module.flink.vo.FlinkJobSubmitReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;

/**
 * Flink Job 构建工具类
 * 
 * @author 江阳科技
 */
@Component
public class FlinkJobBuilder {
    
    @Autowired
    private FlinkJobConfig flinkJobConfig;
    
    /**
     * 构建简单的 WordCount 任务
     */
    public FlinkJobSubmitReqVO buildSimpleWordCountJob() {
        FlinkJobSubmitReqVO reqVO = new FlinkJobSubmitReqVO();
        reqVO.setJarPath(findLatestFlinkJar());
        reqVO.setEntryClass("com.flink.jiangyang.module.flink.job.SimpleWordCountJob");
        reqVO.setJobName("Simple WordCount Job");
        reqVO.setParallelism(1);
        return reqVO;
    }
    
    /**
     * 构建 Kafka 流处理任务
     */
    public FlinkJobSubmitReqVO buildKafkaStreamJob() {
        FlinkJobSubmitReqVO reqVO = new FlinkJobSubmitReqVO();
        reqVO.setJarPath(findLatestFlinkJar());
        reqVO.setEntryClass("com.flink.jiangyang.module.flink.job.KafkaStreamJob");
        reqVO.setJobName("Kafka Stream Processing Job");
        reqVO.setParallelism(2);
        return reqVO;
    }
    
    /**
     * 构建自定义任务
     */
    public FlinkJobSubmitReqVO buildCustomJob(String jarName, String entryClass, String jobName, 
                                            Integer parallelism, String programArgs) {
        FlinkJobSubmitReqVO reqVO = new FlinkJobSubmitReqVO();
        reqVO.setJarName(jarName);
        reqVO.setEntryClass(entryClass);
        reqVO.setJobName(jobName);
        reqVO.setParallelism(parallelism != null ? parallelism : 1);
        reqVO.setProgramArgs(programArgs);
        return reqVO;
    }

    /**
     * 自动查找 target 目录下最新的 jiangyang-module-flink-*.jar
     * 支持多种路径查找策略
     */
    private String findLatestFlinkJar() {
        // 如果配置了固定路径，优先使用
        if (flinkJobConfig.getJarPath() != null && !flinkJobConfig.getJarPath().trim().isEmpty()) {
            File jarFile = new File(flinkJobConfig.getJarPath());
            if (jarFile.exists() && jarFile.isFile()) {
                return jarFile.getAbsolutePath();
            } else {
                throw new RuntimeException("配置的 JAR 路径不存在: " + flinkJobConfig.getJarPath());
            }
        }
        
        // 如果禁用自动查找，抛出异常
        if (!flinkJobConfig.isAutoFindJar()) {
            throw new RuntimeException("已禁用自动查找 JAR 包，请在配置中指定 flink.job.jar-path");
        }
        
        // 使用配置的查找路径
        String[] searchPaths = flinkJobConfig.getJarSearchPaths();
        if (searchPaths == null || searchPaths.length == 0) {
            // 默认查找路径
            searchPaths = new String[]{
                "target",                                    // 当前目录下的 target
                "../jiangYang-module-flink/target",         // 上级目录下的模块 target
                "../../jiangYang-module-flink/target",      // 上两级目录下的模块 target
                System.getProperty("user.dir") + "/target", // 用户工作目录下的 target
                System.getProperty("user.dir") + "/jiangYang-module-flink/target" // 用户工作目录下的模块 target
            };
        }
        
        for (String path : searchPaths) {
            File targetDir = new File(path);
            if (targetDir.exists() && targetDir.isDirectory()) {
                File[] jars = targetDir.listFiles((dir, name) -> 
                    name.startsWith("jiangyang-module-flink-") && name.endsWith(".jar"));
                if (jars != null && jars.length > 0) {
                    Arrays.sort(jars, (a, b) -> Long.compare(b.lastModified(), a.lastModified()));
                    return jars[0].getAbsolutePath();
                }
            }
        }
        
        // 如果都找不到，提供详细的错误信息
        StringBuilder errorMsg = new StringBuilder("未找到 Flink Job Jar 包，请先执行 mvn package\n");
        errorMsg.append("已尝试的路径:\n");
        for (String path : searchPaths) {
            File targetDir = new File(path);
            errorMsg.append("- ").append(targetDir.getAbsolutePath())
                   .append(": ").append(targetDir.exists() ? "存在" : "不存在")
                   .append("\n");
        }
        errorMsg.append("当前工作目录: ").append(System.getProperty("user.dir"));
        errorMsg.append("\n\n解决方案:");
        errorMsg.append("\n1. 执行 mvn clean package 重新打包");
        errorMsg.append("\n2. 在配置文件中指定 JAR 路径: flink.job.jar-path=/path/to/your/jar");
        errorMsg.append("\n3. 禁用自动查找: flink.job.auto-find-jar=false");
        
        throw new RuntimeException(errorMsg.toString());
    }
} 