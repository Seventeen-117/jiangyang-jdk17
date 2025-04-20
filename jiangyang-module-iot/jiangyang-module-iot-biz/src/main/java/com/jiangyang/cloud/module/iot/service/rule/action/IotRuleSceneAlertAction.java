package com.jiangyang.cloud.module.iot.service.rule.action;

import com.jiangyang.cloud.module.iot.dal.dataobject.rule.IotRuleSceneDO;
import com.jiangyang.cloud.module.iot.enums.rule.IotRuleSceneActionTypeEnum;
import com.jiangyang.cloud.module.iot.mq.message.IotDeviceMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

/**
 * IoT 告警的 {@link IotRuleSceneAction} 实现类
 *
 * @author 江阳科技
 */
@Component
public class IotRuleSceneAlertAction implements IotRuleSceneAction {

    @Override
    public void execute(@Nullable IotDeviceMessage message, IotRuleSceneDO.ActionConfig config) {
        // TODO @芋艿：待实现
    }

    @Override
    public IotRuleSceneActionTypeEnum getType() {
        return IotRuleSceneActionTypeEnum.ALERT;
    }

}
