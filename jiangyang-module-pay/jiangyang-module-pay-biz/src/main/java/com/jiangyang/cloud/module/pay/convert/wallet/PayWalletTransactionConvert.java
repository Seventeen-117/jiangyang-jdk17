package com.jiangyang.cloud.module.pay.convert.wallet;

import com.jiangyang.cloud.framework.common.pojo.PageResult;
import com.jiangyang.cloud.module.pay.controller.admin.wallet.vo.transaction.PayWalletTransactionRespVO;
import com.jiangyang.cloud.module.pay.dal.dataobject.wallet.PayWalletTransactionDO;
import com.jiangyang.cloud.module.pay.service.wallet.bo.WalletTransactionCreateReqBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayWalletTransactionConvert {

    PayWalletTransactionConvert INSTANCE = Mappers.getMapper(PayWalletTransactionConvert.class);

    PageResult<PayWalletTransactionRespVO> convertPage2(PageResult<PayWalletTransactionDO> page);

    PayWalletTransactionDO convert(WalletTransactionCreateReqBO bean);

}
