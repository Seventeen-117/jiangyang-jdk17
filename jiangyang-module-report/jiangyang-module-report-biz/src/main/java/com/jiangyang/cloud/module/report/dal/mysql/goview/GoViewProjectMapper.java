package com.jiangyang.cloud.module.report.dal.mysql.goview;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiangyang.cloud.framework.common.pojo.PageParam;
import com.jiangyang.cloud.framework.common.pojo.PageResult;
import com.jiangyang.cloud.module.report.dal.dataobject.goview.GoViewProjectDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoViewProjectMapper extends BaseMapper<GoViewProjectDO> {

    default PageResult<GoViewProjectDO> selectPage(PageParam reqVO, Long userId) {
        // 创建MyBatis Plus的Page对象
        Page<GoViewProjectDO> page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<GoViewProjectDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoViewProjectDO::getCreator, userId);
        queryWrapper.orderByDesc(GoViewProjectDO::getId);
        
        // 执行分页查询
        Page<GoViewProjectDO> resultPage = selectPage(page, queryWrapper);
        
        // 将结果转换为PageResult格式
        return new PageResult<>(resultPage.getRecords(), resultPage.getTotal());
    }

}
