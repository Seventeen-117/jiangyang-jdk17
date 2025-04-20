package com.jiangyang.cloud.module.crm.framework.rpc.config;

import com.jiangyang.cloud.module.bpm.api.task.BpmProcessInstanceApi;
import com.jiangyang.cloud.module.system.api.dept.DeptApi;
import com.jiangyang.cloud.module.system.api.dept.PostApi;
import com.jiangyang.cloud.module.system.api.user.AdminUserApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {AdminUserApi.class, DeptApi.class, PostApi.class,
        BpmProcessInstanceApi.class})
public class RpcConfiguration {
}
