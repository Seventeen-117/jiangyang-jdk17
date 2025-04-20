package com.jiangyang.cloud.module.infra.dal.mysql.codegen;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiangyang.cloud.framework.common.pojo.PageResult;
import com.jiangyang.cloud.module.infra.controller.admin.codegen.vo.table.CodegenTablePageReqVO;
import com.jiangyang.cloud.module.infra.dal.dataobject.codegen.CodegenTableDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CodegenTableMapper extends BaseMapper<CodegenTableDO> {

    default CodegenTableDO selectByTableNameAndDataSourceConfigId(String tableName, Long dataSourceConfigId) {
        return selectOne(new LambdaQueryWrapper<CodegenTableDO>()
                .eq(CodegenTableDO::getTableName, tableName)
                .eq(CodegenTableDO::getDataSourceConfigId, dataSourceConfigId));
    }

    default PageResult<CodegenTableDO> selectPage(CodegenTablePageReqVO pageReqVO) {
        // 创建MyBatis Plus的Page对象
        Page<CodegenTableDO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<CodegenTableDO> queryWrapper = new LambdaQueryWrapper<>();
        // 条件：表名
        if (pageReqVO.getTableName() != null) {
            queryWrapper.like(CodegenTableDO::getTableName, pageReqVO.getTableName());
        }
        // 条件：表注释
        if (pageReqVO.getTableComment() != null) {
            queryWrapper.like(CodegenTableDO::getTableComment, pageReqVO.getTableComment());
        }
        // 条件：类名
        if (pageReqVO.getClassName() != null) {
            queryWrapper.like(CodegenTableDO::getClassName, pageReqVO.getClassName());
        }
        // 条件：创建时间范围
        if (pageReqVO.getCreateTime() != null && pageReqVO.getCreateTime().length == 2) {
            queryWrapper.between(CodegenTableDO::getCreateTime, 
                    pageReqVO.getCreateTime()[0], pageReqVO.getCreateTime()[1]);
        }
        // 排序：按更新时间倒序
        queryWrapper.orderByDesc(CodegenTableDO::getUpdateTime);
        
        // 执行分页查询
        Page<CodegenTableDO> resultPage = selectPage(page, queryWrapper);
        
        // 将结果转换为PageResult格式
        return new PageResult<>(resultPage.getRecords(), resultPage.getTotal());
    }

    default List<CodegenTableDO> selectListByDataSourceConfigId(Long dataSourceConfigId) {
        return selectList(new LambdaQueryWrapper<CodegenTableDO>()
                .eq(CodegenTableDO::getDataSourceConfigId, dataSourceConfigId));
    }

    default List<CodegenTableDO> selectListByTemplateTypeAndMasterTableId(Integer templateType, Long masterTableId) {
        return selectList(new LambdaQueryWrapper<CodegenTableDO>()
                .eq(CodegenTableDO::getTemplateType, templateType)
                .eq(CodegenTableDO::getMasterTableId, masterTableId));
    }
}
