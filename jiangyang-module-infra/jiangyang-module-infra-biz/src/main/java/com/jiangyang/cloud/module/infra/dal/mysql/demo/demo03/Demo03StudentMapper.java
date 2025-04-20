package com.jiangyang.cloud.module.infra.dal.mysql.demo.demo03;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiangyang.cloud.framework.common.pojo.PageResult;
import com.jiangyang.cloud.module.infra.controller.admin.demo.demo03.vo.Demo03StudentPageReqVO;
import com.jiangyang.cloud.module.infra.dal.dataobject.demo.demo03.Demo03StudentDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学生 Mapper
 *
 * @author 江阳科技
 */
@Mapper
public interface Demo03StudentMapper extends BaseMapper<Demo03StudentDO> {

    default PageResult<Demo03StudentDO> selectPage(Demo03StudentPageReqVO reqVO) {
        // 创建MyBatis Plus的Page对象
        Page<Demo03StudentDO> page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<Demo03StudentDO> queryWrapper = new LambdaQueryWrapper<>();
        // 名称
        if (reqVO.getName() != null) {
            queryWrapper.like(Demo03StudentDO::getName, reqVO.getName());
        }
        // 性别
        if (reqVO.getSex() != null) {
            queryWrapper.eq(Demo03StudentDO::getSex, reqVO.getSex());
        }
        // 描述
        if (reqVO.getDescription() != null) {
            queryWrapper.eq(Demo03StudentDO::getDescription, reqVO.getDescription());
        }
        // 创建时间范围
        if (reqVO.getCreateTime() != null && reqVO.getCreateTime().length == 2) {
            queryWrapper.between(Demo03StudentDO::getCreateTime, 
                    reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]);
        }
        // 排序方式
        queryWrapper.orderByDesc(Demo03StudentDO::getId);
        
        // 执行分页查询
        Page<Demo03StudentDO> resultPage = selectPage(page, queryWrapper);
        
        // 将结果转换为PageResult格式
        return new PageResult<>(resultPage.getRecords(), resultPage.getTotal());
    }
}