package com.jiangyang.cloud.module.bpm.enums.definition;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * BPM 流程监听器的值类型
 *
 * @author 江阳科技
 */
@Getter
@AllArgsConstructor
public enum BpmProcessListenerValueTypeEnum {

    CLASS("class", "Java 类"),
    DELEGATE_EXPRESSION("delegateExpression", "代理表达式"),
    EXPRESSION("expression", "表达式");

    private final String type;
    private final String name;

}
