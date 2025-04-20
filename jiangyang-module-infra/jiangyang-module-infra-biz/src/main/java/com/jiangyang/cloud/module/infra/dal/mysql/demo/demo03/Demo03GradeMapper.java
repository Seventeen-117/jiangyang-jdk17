package com.jiangyang.cloud.module.infra.dal.mysql.demo.demo03;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiangyang.cloud.framework.common.pojo.PageParam;
import com.jiangyang.cloud.framework.common.pojo.PageResult;
import com.jiangyang.cloud.module.infra.dal.dataobject.demo.demo03.Demo03GradeDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学生班级 Mapper
 *
 * @author 江阳科技
 */
@Mapper
public interface Demo03GradeMapper extends BaseMapper<Demo03GradeDO> {

    default PageResult<Demo03GradeDO> selectPage(PageParam reqVO, Long studentId) {
        // 创建MyBatis Plus的Page对象
        Page<Demo03GradeDO> page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<Demo03GradeDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Demo03GradeDO::getStudentId, studentId);
        queryWrapper.orderByDesc(Demo03GradeDO::getId);
        
        // 执行分页查询
        Page<Demo03GradeDO> resultPage = selectPage(page, queryWrapper);
        
        // 将结果转换为PageResult格式
        return new PageResult<>(resultPage.getRecords(), resultPage.getTotal());
    }

    default Demo03GradeDO selectByStudentId(Long studentId) {
        return selectOne(new LambdaQueryWrapper<Demo03GradeDO>()
                .eq(Demo03GradeDO::getStudentId, studentId));
    }

    default int deleteByStudentId(Long studentId) {
        // Use standard MyBatis Plus deleteByMap method
        java.util.Map<String, Object> map = new java.util.HashMap<>();
        map.put("student_id", studentId);
        return deleteByMap(map);
    }
}