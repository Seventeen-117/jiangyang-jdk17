package com.jiangyang.cloud.module.erp.dal.mysql.stock;

import com.jiangyang.cloud.framework.mybatis.core.mapper.BaseMapperX;
import com.jiangyang.cloud.module.erp.dal.dataobject.stock.ErpStockMoveItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * ERP 库存调拨单项 Mapper
 *
 * @author 江阳科技
 */
@Mapper
public interface ErpStockMoveItemMapper extends BaseMapperX<ErpStockMoveItemDO> {

    default List<ErpStockMoveItemDO> selectListByMoveId(Long moveId) {
        return selectList(ErpStockMoveItemDO::getMoveId, moveId);
    }

    default List<ErpStockMoveItemDO> selectListByMoveIds(Collection<Long> moveIds) {
        return selectList(ErpStockMoveItemDO::getMoveId, moveIds);
    }

    default int deleteByMoveId(Long moveId) {
        return delete(ErpStockMoveItemDO::getMoveId, moveId);
    }

}