package com.jiangyang.cloud.module.promotion.framework.rpc.config;

import com.jiangyang.cloud.module.infra.api.websocket.WebSocketSenderApi;
import com.jiangyang.cloud.module.member.api.user.MemberUserApi;
import com.jiangyang.cloud.module.product.api.category.ProductCategoryApi;
import com.jiangyang.cloud.module.product.api.sku.ProductSkuApi;
import com.jiangyang.cloud.module.product.api.spu.ProductSpuApi;
import com.jiangyang.cloud.module.system.api.social.SocialClientApi;
import com.jiangyang.cloud.module.system.api.user.AdminUserApi;
import com.jiangyang.cloud.module.trade.api.order.TradeOrderApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {ProductSkuApi.class, ProductSpuApi.class, ProductCategoryApi.class,
        MemberUserApi.class, TradeOrderApi.class, AdminUserApi.class, SocialClientApi.class,
        WebSocketSenderApi.class})
public class RpcConfiguration {
}
