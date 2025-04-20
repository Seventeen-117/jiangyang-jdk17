package com.jiangyang.cloud.module.pay.convert.transfer;

import com.jiangyang.cloud.framework.common.pojo.PageResult;
import com.jiangyang.cloud.framework.pay.core.client.dto.transfer.PayTransferUnifiedReqDTO;
import com.jiangyang.cloud.module.pay.api.transfer.dto.PayTransferCreateReqDTO;
import com.jiangyang.cloud.module.pay.controller.admin.demo.vo.transfer.PayDemoTransferCreateReqVO;
import com.jiangyang.cloud.module.pay.controller.admin.transfer.vo.PayTransferCreateReqVO;
import com.jiangyang.cloud.module.pay.controller.admin.transfer.vo.PayTransferPageItemRespVO;
import com.jiangyang.cloud.module.pay.controller.admin.transfer.vo.PayTransferRespVO;
import com.jiangyang.cloud.module.pay.dal.dataobject.transfer.PayTransferDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayTransferConvert {

    PayTransferConvert INSTANCE = Mappers.getMapper(PayTransferConvert.class);

    PayTransferDO convert(PayTransferCreateReqDTO dto);

    PayTransferUnifiedReqDTO convert2(PayTransferDO dto);

    PayTransferCreateReqDTO convert(PayTransferCreateReqVO vo);

    PayTransferCreateReqDTO convert(PayDemoTransferCreateReqVO vo);

    PayTransferRespVO convert(PayTransferDO bean);

    PageResult<PayTransferPageItemRespVO> convertPage(PageResult<PayTransferDO> pageResult);

}
