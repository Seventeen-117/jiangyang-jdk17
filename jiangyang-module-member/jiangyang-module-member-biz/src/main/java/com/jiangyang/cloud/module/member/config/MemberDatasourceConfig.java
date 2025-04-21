package com.jiangyang.cloud.module.member.config;

import org.springframework.context.annotation.Configuration;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;

@Configuration
@AutoConfigureBefore(DynamicDataSourceAutoConfiguration.class)
public class MemberDatasourceConfig {
    // 配置类保持为空，仅用于确保动态数据源的自动配置顺序
} 