package com.jiangyang.cloud.module.infra.api.config;

import com.jiangyang.cloud.framework.common.pojo.CommonResult;
import com.jiangyang.cloud.module.infra.dal.dataobject.config.ConfigDO;
import com.jiangyang.cloud.module.infra.service.config.ConfigService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import static com.jiangyang.cloud.framework.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class ConfigApiImpl implements ConfigApi {

    @Resource
    private ConfigService configService;

    @Override
    public CommonResult<String> getConfigValueByKey(String key) {
        ConfigDO config = configService.getConfigByKey(key);
        return success(config != null ? config.getValue() : null);
    }

}
