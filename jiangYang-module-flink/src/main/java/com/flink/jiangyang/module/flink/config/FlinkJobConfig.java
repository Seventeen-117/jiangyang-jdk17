package com.flink.jiangyang.module.flink.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Flink Job 配置
 * 
 * @author 江阳科技
 */
@Data
@Component
@ConfigurationProperties(prefix = "flink.job")
public class FlinkJobConfig {
    
    /**
     * JAR 包路径，如果为空则自动查找
     */
    private String jarPath;
    
    /**
     * 是否启用自动查找 JAR 包
     */
    private boolean autoFindJar = true;
    
    /**
     * 自定义 JAR 包查找路径列表
     */
    private String[] jarSearchPaths = {
        "target",
        "../jiangYang-module-flink/target",
        "../../jiangYang-module-flink/target"
    };
} 