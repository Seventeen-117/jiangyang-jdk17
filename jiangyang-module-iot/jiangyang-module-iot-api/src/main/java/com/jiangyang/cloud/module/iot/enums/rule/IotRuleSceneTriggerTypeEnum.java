package com.jiangyang.cloud.module.iot.enums.rule;

import com.jiangyang.cloud.framework.common.core.ArrayValuable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * IoT 场景流转的触发类型枚举
 *
 * @author 江阳科技
 */
@RequiredArgsConstructor
@Getter
public enum IotRuleSceneTriggerTypeEnum implements ArrayValuable<Integer> {

    DEVICE(1), // 设备触发
    TIMER(2); // 定时触发

    private final Integer type;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(IotRuleSceneTriggerTypeEnum::getType).toArray(Integer[]::new);

    @Override
    public Integer[] array() {
        return ARRAYS;
    }

}
