package com.jiangyang.cloud.module.statistics.framework.rpc.config;

import com.jiangyang.cloud.module.product.api.spu.ProductSpuApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {ProductSpuApi.class})
public class RpcConfiguration {
}
