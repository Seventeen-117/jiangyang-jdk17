package com.jiangyang.cloud.framework.mq.redis.config;

import com.jiangyang.cloud.framework.mq.redis.core.RedisMQTemplate;
import com.jiangyang.cloud.framework.mq.redis.core.interceptor.RedisMessageInterceptor;
import com.jiangyang.cloud.framework.redis.config.JiangYangRedisAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * Redis 消息队列 Producer 配置类
 *
 * @author 江阳科技
 */
@Slf4j
@AutoConfiguration(after = JiangYangRedisAutoConfiguration.class)
public class JiangYangRedisMQProducerAutoConfiguration {

    @Bean
    public RedisMQTemplate redisMQTemplate(StringRedisTemplate redisTemplate,
                                           List<RedisMessageInterceptor> interceptors) {
        RedisMQTemplate redisMQTemplate = new RedisMQTemplate(redisTemplate);
        // 添加拦截器
        interceptors.forEach(redisMQTemplate::addInterceptor);
        return redisMQTemplate;
    }

}
