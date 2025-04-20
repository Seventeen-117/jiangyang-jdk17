package com.jiangyang.cloud.module.promotion.convert.discount;

import com.jiangyang.cloud.framework.common.pojo.PageResult;
import com.jiangyang.cloud.framework.common.util.object.BeanUtils;
import com.jiangyang.cloud.module.promotion.controller.admin.discount.vo.DiscountActivityBaseVO;
import com.jiangyang.cloud.module.promotion.controller.admin.discount.vo.DiscountActivityCreateReqVO;
import com.jiangyang.cloud.module.promotion.controller.admin.discount.vo.DiscountActivityRespVO;
import com.jiangyang.cloud.module.promotion.controller.admin.discount.vo.DiscountActivityUpdateReqVO;
import com.jiangyang.cloud.module.promotion.dal.dataobject.discount.DiscountActivityDO;
import com.jiangyang.cloud.module.promotion.dal.dataobject.discount.DiscountProductDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 限时折扣活动 Convert
 *
 * @author 江阳科技
 */
@Mapper
public interface DiscountActivityConvert {

    DiscountActivityConvert INSTANCE = Mappers.getMapper(DiscountActivityConvert.class);

    DiscountActivityDO convert(DiscountActivityCreateReqVO bean);

    DiscountActivityDO convert(DiscountActivityUpdateReqVO bean);

    DiscountActivityRespVO convert(DiscountActivityDO bean);

    List<DiscountActivityRespVO> convertList(List<DiscountActivityDO> list);

    List<DiscountActivityBaseVO.Product> convertList2(List<DiscountProductDO> list);

    PageResult<DiscountActivityRespVO> convertPage(PageResult<DiscountActivityDO> page);

    default PageResult<DiscountActivityRespVO> convertPage(PageResult<DiscountActivityDO> page,
                                                           List<DiscountProductDO> discountProductDOList) {
        PageResult<DiscountActivityRespVO> pageResult = convertPage(page);
        pageResult.getList().forEach(item -> item.setProducts(convertList2(discountProductDOList)));
        return pageResult;
    }

    DiscountProductDO convert(DiscountActivityBaseVO.Product bean);

    default DiscountActivityRespVO convert(DiscountActivityDO activity, List<DiscountProductDO> products) {
        return BeanUtils.toBean(activity, DiscountActivityRespVO.class).setProducts(convertList2(products));
    }

}