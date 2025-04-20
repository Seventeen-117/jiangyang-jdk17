package com.jiangyang.cloud.module.infra.dal.mysql.codegen;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiangyang.cloud.module.infra.dal.dataobject.codegen.CodegenColumnDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成的字段 Mapper
 */
@Mapper
public interface CodegenColumnMapper extends BaseMapper<CodegenColumnDO> {

    default List<CodegenColumnDO> selectListByTableId(Long tableId) {
        return selectList(new LambdaQueryWrapper<CodegenColumnDO>()
                .eq(CodegenColumnDO::getTableId, tableId)
                .orderByAsc(CodegenColumnDO::getOrdinalPosition));
    }

    default void deleteListByTableId(Long tableId) {
        // Use deleteByMap which is provided by BaseMapper
        Map<String, Object> map = new HashMap<>();
        map.put("table_id", tableId);
        deleteByMap(map);
    }
}
