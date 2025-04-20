package com.jiangyang.cloud.module.iot.enums.rule;

import com.jiangyang.cloud.framework.common.core.ArrayValuable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * IoT 数据桥接的方向枚举
 *
 * @author 江阳科技
 */
@RequiredArgsConstructor
@Getter
public enum IotDataBridgeDirectionEnum implements ArrayValuable<Integer> {

    INPUT(1), // 输入
    OUTPUT(2); // 输出

    private final Integer type;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(IotDataBridgeDirectionEnum::getType).toArray(Integer[]::new);

    @Override
    public Integer[] array() {
        return ARRAYS;
    }

}
