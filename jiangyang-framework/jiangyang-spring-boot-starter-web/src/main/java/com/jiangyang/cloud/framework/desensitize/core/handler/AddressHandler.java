package com.jiangyang.cloud.framework.desensitize.core.handler;

import com.jiangyang.cloud.framework.desensitize.core.base.handler.DesensitizationHandler;
import com.jiangyang.cloud.framework.desensitize.core.annotation.Address;

/**
 * {@link Address} 的脱敏处理器
 *
 * @author gaibu
 */
public class AddressHandler implements DesensitizationHandler<Address> {

    @Override
    public String desensitize(String origin, Address annotation) {
        return origin + annotation.replacer();
    }

} 