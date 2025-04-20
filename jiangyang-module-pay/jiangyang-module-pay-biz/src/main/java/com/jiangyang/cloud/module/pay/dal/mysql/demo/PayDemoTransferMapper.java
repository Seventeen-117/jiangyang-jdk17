package com.jiangyang.cloud.module.pay.dal.mysql.demo;

import com.jiangyang.cloud.framework.common.pojo.PageParam;
import com.jiangyang.cloud.framework.common.pojo.PageResult;
import com.jiangyang.cloud.framework.mybatis.core.mapper.BaseMapperX;
import com.jiangyang.cloud.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.jiangyang.cloud.module.pay.dal.dataobject.demo.PayDemoTransferDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayDemoTransferMapper extends BaseMapperX<PayDemoTransferDO> {

    default  PageResult<PayDemoTransferDO> selectPage(PageParam pageParam){
        return selectPage(pageParam, new LambdaQueryWrapperX<PayDemoTransferDO>()
                .orderByDesc(PayDemoTransferDO::getId));
    }
}