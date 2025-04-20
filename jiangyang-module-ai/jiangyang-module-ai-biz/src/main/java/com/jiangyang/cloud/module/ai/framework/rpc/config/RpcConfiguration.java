package com.jiangyang.cloud.module.ai.framework.rpc.config;

import com.jiangyang.cloud.module.infra.api.file.FileApi;
import com.jiangyang.cloud.module.system.api.dict.DictDataApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {DictDataApi.class, FileApi.class})
public class RpcConfiguration {
}
