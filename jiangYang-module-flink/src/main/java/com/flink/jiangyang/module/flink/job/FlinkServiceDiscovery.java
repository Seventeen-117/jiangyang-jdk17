package com.flink.jiangyang.module.flink.job;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlinkServiceDiscovery {

    private final NacosServiceDiscovery nacosServiceDiscovery;
    private final NacosDiscoveryProperties nacosProperties;

    @Value("${jiangyang.flink.jobManagerUrl}")
    private String defaultFlinkUrl;

    /**
     * 获取可用的Flink JobManager实例URL
     */
    public String getFlinkBaseUrl() {
        try {
            List<ServiceInstance> instances = nacosServiceDiscovery.getInstances("flink-jobmanager");

            if (instances == null || instances.isEmpty()) {
                log.warn("No available Flink JobManager instances found in Nacos, using default URL: {}", defaultFlinkUrl);
                return defaultFlinkUrl;
            }

            // 简单负载均衡：随机选择实例
            ServiceInstance instance = instances.get(
                    ThreadLocalRandom.current().nextInt(instances.size())
            );

            String serviceUrl = instance.getUri().toString();
            log.debug("Using Flink JobManager from Nacos: {}", serviceUrl);
            return serviceUrl;
        } catch (Exception e) {
            log.warn("Failed to discover Flink service from Nacos, using default URL: {}. Error: {}", defaultFlinkUrl, e.getMessage());
            return defaultFlinkUrl;
        }
    }

    /**
     * 获取所有Flink实例
     */
    public List<ServiceInstance> getAllFlinkInstances() {
        try {
            return nacosServiceDiscovery.getInstances("flink-jobmanager");
        } catch (Exception e) {
            log.warn("Failed to discover Flink instances from Nacos: {}", e.getMessage());
            return List.of();
        }
    }
}