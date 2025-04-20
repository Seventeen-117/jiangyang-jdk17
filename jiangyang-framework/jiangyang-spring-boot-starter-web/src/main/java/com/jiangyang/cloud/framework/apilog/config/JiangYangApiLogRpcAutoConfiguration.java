package com.jiangyang.cloud.framework.apilog.config;

import com.jiangyang.cloud.module.infra.api.logger.ApiAccessLogApi;
import com.jiangyang.cloud.module.infra.api.logger.ApiErrorLogApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * API 日志使用到 Feign 的配置项
 *
 * @author 江阳科技
 */
@AutoConfiguration
@EnableFeignClients(clients = {ApiAccessLogApi.class, ApiErrorLogApi.class}) // 主要是引入相关的 API 服务
public class JiangYangApiLogRpcAutoConfiguration {
}
