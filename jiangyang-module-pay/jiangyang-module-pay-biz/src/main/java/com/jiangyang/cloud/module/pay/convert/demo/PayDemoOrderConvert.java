package com.jiangyang.cloud.module.pay.convert.demo;

import com.jiangyang.cloud.framework.common.pojo.PageResult;
import com.jiangyang.cloud.module.pay.controller.admin.demo.vo.order.PayDemoOrderCreateReqVO;
import com.jiangyang.cloud.module.pay.controller.admin.demo.vo.order.PayDemoOrderRespVO;
import com.jiangyang.cloud.module.pay.dal.dataobject.demo.PayDemoOrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 示例订单 Convert
 *
 * @author 江阳科技
 */
@Mapper
public interface PayDemoOrderConvert {

    PayDemoOrderConvert INSTANCE = Mappers.getMapper(PayDemoOrderConvert.class);

    PayDemoOrderDO convert(PayDemoOrderCreateReqVO bean);

    PayDemoOrderRespVO convert(PayDemoOrderDO bean);

    PageResult<PayDemoOrderRespVO> convertPage(PageResult<PayDemoOrderDO> page);

}
