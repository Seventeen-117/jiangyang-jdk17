package com.jiangyang.cloud.module.infra.framework.file.config;

import com.jiangyang.cloud.module.infra.framework.file.core.client.FileClientFactory;
import com.jiangyang.cloud.module.infra.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件配置类
 *
 * @author 江阳科技
 */
@Configuration(proxyBeanMethods = false)
public class JiangYangFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
