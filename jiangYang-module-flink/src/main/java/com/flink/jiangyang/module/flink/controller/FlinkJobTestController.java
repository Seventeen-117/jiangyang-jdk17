package com.flink.jiangyang.module.flink.controller;

import com.flink.jiangyang.module.flink.config.FlinkJobConfig;
import com.flink.jiangyang.module.flink.utils.FlinkJobBuilder;
import com.jiangyang.cloud.framework.common.pojo.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Flink Job 测试控制器
 * 
 * @author 江阳科技
 */
@RestController
@RequestMapping("/flink/test")
public class FlinkJobTestController {
    
    @Autowired
    private FlinkJobConfig flinkJobConfig;
    
    @Autowired
    private FlinkJobBuilder flinkJobBuilder;
    
    /**
     * 测试 JAR 包查找功能
     */
    @GetMapping("/jar-info")
    public CommonResult<Map<String, Object>> getJarInfo() {
        Map<String, Object> info = new HashMap<>();
        
        // 当前工作目录
        info.put("currentWorkingDir", System.getProperty("user.dir"));
        
        // 配置信息
        info.put("jarPath", flinkJobConfig.getJarPath());
        info.put("autoFindJar", flinkJobConfig.isAutoFindJar());
        info.put("jarSearchPaths", flinkJobConfig.getJarSearchPaths());
        
        // 检查各个路径是否存在
        Map<String, Object> pathStatus = new HashMap<>();
        String[] paths = {
            "target",
            "../jiangYang-module-flink/target",
            "../../jiangYang-module-flink/target"
        };
        
        for (String path : paths) {
            File dir = new File(path);
            pathStatus.put(path, dir.exists() && dir.isDirectory());
            
            if (dir.exists() && dir.isDirectory()) {
                File[] jars = dir.listFiles((file, name) -> 
                    name.startsWith("jiangyang-module-flink-") && name.endsWith(".jar"));
                pathStatus.put(path + "_hasJars", jars != null && jars.length > 0);
                if (jars != null && jars.length > 0) {
                    pathStatus.put(path + "_jarCount", jars.length);
                    pathStatus.put(path + "_latestJar", jars[0].getName());
                }
            }
        }
        info.put("pathStatus", pathStatus);
        
        return CommonResult.success(info);
    }
    
    /**
     * 测试构建 WordCount Job
     */
    @GetMapping("/build-wordcount")
    public CommonResult<Object> testBuildWordCountJob() {
        try {
            return CommonResult.success(flinkJobBuilder.buildSimpleWordCountJob());
        } catch (Exception e) {
            return CommonResult.error(500, "构建 WordCount Job 失败: " + e.getMessage());
        }
    }
} 