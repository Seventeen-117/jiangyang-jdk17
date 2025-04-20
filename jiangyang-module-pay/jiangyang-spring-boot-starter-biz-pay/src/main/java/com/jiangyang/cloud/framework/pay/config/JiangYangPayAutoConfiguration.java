package com.jiangyang.cloud.framework.pay.config;

import com.jiangyang.cloud.framework.pay.core.client.PayClientFactory;
import com.jiangyang.cloud.framework.pay.core.client.impl.PayClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 支付配置类
 *
 * @author 江阳科技
 */
@AutoConfiguration
public class JiangYangPayAutoConfiguration {

    @Bean
    public PayClientFactory payClientFactory() {
        return new PayClientFactoryImpl();
    }

}
