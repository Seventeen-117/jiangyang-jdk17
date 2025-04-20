package com.jiangyang.cloud.framework.tracer.config;

import com.jiangyang.cloud.framework.common.enums.WebFilterOrderEnum;
import com.jiangyang.cloud.framework.tracer.core.aop.BizTraceAspect;
import com.jiangyang.cloud.framework.tracer.core.filter.TraceFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Tracer 配置类
 *
 * @author mashu
 */
@AutoConfiguration
@ConditionalOnClass(value = {BizTraceAspect.class}, name = "jakarta.servlet.Filter")
@EnableConfigurationProperties(TracerProperties.class)
@ConditionalOnProperty(prefix = "jiangyang.tracer", value = "enable", matchIfMissing = true)
public class JiangYangTracerAutoConfiguration {


    /**
     * 创建 TraceFilter 过滤器，响应 header 设置 traceId
     */
    @Bean
    public FilterRegistrationBean<TraceFilter> traceFilter() {
        FilterRegistrationBean<TraceFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TraceFilter());
        registrationBean.setOrder(WebFilterOrderEnum.TRACE_FILTER);
        return registrationBean;
    }

}
