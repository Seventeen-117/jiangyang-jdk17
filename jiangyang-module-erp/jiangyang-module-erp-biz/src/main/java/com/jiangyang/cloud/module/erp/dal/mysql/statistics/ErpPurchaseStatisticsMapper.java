package com.jiangyang.cloud.module.erp.dal.mysql.statistics;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ERP 采购统计 Mapper
 *
 * @author 江阳科技
 */
@Mapper
public interface ErpPurchaseStatisticsMapper {

    BigDecimal getPurchasePrice(@Param("beginTime") LocalDateTime beginTime,
                                @Param("endTime") LocalDateTime endTime);

}
