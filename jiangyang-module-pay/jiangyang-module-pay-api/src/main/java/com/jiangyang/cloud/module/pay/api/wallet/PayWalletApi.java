package com.jiangyang.cloud.module.pay.api.wallet;

import com.jiangyang.cloud.framework.common.pojo.CommonResult;
import com.jiangyang.cloud.module.pay.api.wallet.dto.PayWalletAddBalanceReqDTO;
import com.jiangyang.cloud.module.pay.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${spring.application.name}", path = ApiConstants.PREFIX) // 直接使用配置属性
@Tag(name = "RPC 服务 - 钱包")
public interface PayWalletApi {

    String PREFIX = "/wallet";

    @PostMapping(PREFIX + "/add-balance")
    @Operation(summary = "添加钱包余额")
    CommonResult<Boolean> addWalletBalance(@Valid @RequestBody PayWalletAddBalanceReqDTO reqDTO);

}
