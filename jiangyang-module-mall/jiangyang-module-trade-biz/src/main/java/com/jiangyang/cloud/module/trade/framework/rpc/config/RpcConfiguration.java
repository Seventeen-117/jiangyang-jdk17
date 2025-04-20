package com.jiangyang.cloud.module.trade.framework.rpc.config;

import com.jiangyang.cloud.module.member.api.address.MemberAddressApi;
import com.jiangyang.cloud.module.member.api.config.MemberConfigApi;
import com.jiangyang.cloud.module.member.api.level.MemberLevelApi;
import com.jiangyang.cloud.module.member.api.point.MemberPointApi;
import com.jiangyang.cloud.module.member.api.user.MemberUserApi;
import com.jiangyang.cloud.module.pay.api.order.PayOrderApi;
import com.jiangyang.cloud.module.pay.api.refund.PayRefundApi;
import com.jiangyang.cloud.module.pay.api.transfer.PayTransferApi;
import com.jiangyang.cloud.module.pay.api.wallet.PayWalletApi;
import com.jiangyang.cloud.module.product.api.category.ProductCategoryApi;
import com.jiangyang.cloud.module.product.api.comment.ProductCommentApi;
import com.jiangyang.cloud.module.product.api.sku.ProductSkuApi;
import com.jiangyang.cloud.module.product.api.spu.ProductSpuApi;
import com.jiangyang.cloud.module.promotion.api.bargain.BargainActivityApi;
import com.jiangyang.cloud.module.promotion.api.bargain.BargainRecordApi;
import com.jiangyang.cloud.module.promotion.api.combination.CombinationRecordApi;
import com.jiangyang.cloud.module.promotion.api.coupon.CouponApi;
import com.jiangyang.cloud.module.promotion.api.discount.DiscountActivityApi;
import com.jiangyang.cloud.module.promotion.api.point.PointActivityApi;
import com.jiangyang.cloud.module.promotion.api.reward.RewardActivityApi;
import com.jiangyang.cloud.module.promotion.api.seckill.SeckillActivityApi;
import com.jiangyang.cloud.module.system.api.notify.NotifyMessageSendApi;
import com.jiangyang.cloud.module.system.api.social.SocialClientApi;
import com.jiangyang.cloud.module.system.api.social.SocialUserApi;
import com.jiangyang.cloud.module.system.api.user.AdminUserApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {
        BargainActivityApi.class, BargainRecordApi.class, CombinationRecordApi.class,
        CouponApi.class, DiscountActivityApi.class, RewardActivityApi.class, SeckillActivityApi.class, PointActivityApi.class,
        MemberUserApi.class, MemberPointApi.class, MemberLevelApi.class, MemberAddressApi.class, MemberConfigApi.class,
        ProductSpuApi.class, ProductSkuApi.class, ProductCommentApi.class, ProductCategoryApi.class,
        PayOrderApi.class, PayRefundApi.class, PayTransferApi.class, PayWalletApi.class,
        AdminUserApi.class, NotifyMessageSendApi.class, SocialClientApi.class, SocialUserApi.class
})
public class RpcConfiguration {
}
