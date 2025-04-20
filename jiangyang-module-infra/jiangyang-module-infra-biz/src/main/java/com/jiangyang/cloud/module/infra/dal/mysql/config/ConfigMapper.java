package com.jiangyang.cloud.module.infra.dal.mysql.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiangyang.cloud.framework.common.pojo.PageResult;
import com.jiangyang.cloud.module.infra.controller.admin.config.vo.ConfigPageReqVO;
import com.jiangyang.cloud.module.infra.dal.dataobject.config.ConfigDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConfigMapper extends BaseMapper<ConfigDO> {

    default ConfigDO selectByKey(String key) {
        return selectOne(new LambdaQueryWrapper<ConfigDO>()
                .eq(ConfigDO::getConfigKey, key));
    }

    default PageResult<ConfigDO> selectPage(ConfigPageReqVO reqVO) {
        // 创建MyBatis Plus的Page对象
        Page<ConfigDO> page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<ConfigDO> queryWrapper = new LambdaQueryWrapper<>();
        // 条件：名称
        if (reqVO.getName() != null) {
            queryWrapper.like(ConfigDO::getName, reqVO.getName());
        }
        // 条件：配置键
        if (reqVO.getKey() != null) {
            queryWrapper.like(ConfigDO::getConfigKey, reqVO.getKey());
        }
        // 条件：类型
        if (reqVO.getType() != null) {
            queryWrapper.eq(ConfigDO::getType, reqVO.getType());
        }
        // 条件：创建时间范围
        if (reqVO.getCreateTime() != null && reqVO.getCreateTime().length == 2) {
            queryWrapper.between(ConfigDO::getCreateTime, 
                    reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]);
        }
        
        // 执行分页查询
        Page<ConfigDO> resultPage = selectPage(page, queryWrapper);
        
        // 将结果转换为PageResult格式
        return new PageResult<>(resultPage.getRecords(), resultPage.getTotal());
    }
}
