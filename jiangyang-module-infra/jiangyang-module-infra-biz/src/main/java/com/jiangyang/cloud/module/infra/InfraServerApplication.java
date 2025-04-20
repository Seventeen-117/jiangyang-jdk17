package com.jiangyang.cloud.module.infra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;

/**
 * 项目的启动类
 * <p>
 * 如果你碰到启动的问题，请认真阅读 https://cloud.iocoder.cn/quick-start/ 文章
 * 如果你碰到启动的问题，请认真阅读 https://cloud.iocoder.cn/quick-start/ 文章
 * 如果你碰到启动的问题，请认真阅读 https://cloud.iocoder.cn/quick-start/ 文章
 *
 * @author 江阳科技
 */
@SpringBootApplication
@MapperScan("com.jiangyang.cloud.module.infra.dal.mysql")
@EnableAutoConfiguration(exclude = {
    de.codecentric.boot.admin.server.config.AdminServerAutoConfiguration.class,
    de.codecentric.boot.admin.server.cloud.config.AdminServerDiscoveryAutoConfiguration.class,
    de.codecentric.boot.admin.server.ui.config.AdminServerUiAutoConfiguration.class,
    de.codecentric.boot.admin.client.config.SpringBootAdminClientAutoConfiguration.class
})
public class InfraServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(InfraServerApplication.class, args);


    }

}
