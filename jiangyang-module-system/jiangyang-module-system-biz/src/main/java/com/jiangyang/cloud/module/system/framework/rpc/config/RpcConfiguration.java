package com.jiangyang.cloud.module.system.framework.rpc.config;

import com.jiangyang.cloud.module.infra.api.config.ConfigApi;
import com.jiangyang.cloud.module.infra.api.file.FileApi;
import com.jiangyang.cloud.module.infra.api.websocket.WebSocketSenderApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {FileApi.class, WebSocketSenderApi.class, ConfigApi.class})
public class RpcConfiguration {
}
