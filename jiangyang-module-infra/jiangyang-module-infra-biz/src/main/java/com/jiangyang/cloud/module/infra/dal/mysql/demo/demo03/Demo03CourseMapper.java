package com.jiangyang.cloud.module.infra.dal.mysql.demo.demo03;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiangyang.cloud.framework.common.pojo.PageParam;
import com.jiangyang.cloud.framework.common.pojo.PageResult;
import com.jiangyang.cloud.module.infra.dal.dataobject.demo.demo03.Demo03CourseDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 学生课程 Mapper
 *
 * @author 江阳科技
 */
@Mapper
public interface Demo03CourseMapper extends BaseMapper<Demo03CourseDO> {

    default PageResult<Demo03CourseDO> selectPage(PageParam reqVO, Long studentId) {
        // 创建MyBatis Plus的Page对象
        Page<Demo03CourseDO> page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<Demo03CourseDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Demo03CourseDO::getStudentId, studentId);
        queryWrapper.orderByDesc(Demo03CourseDO::getId);
        
        // 执行分页查询
        Page<Demo03CourseDO> resultPage = selectPage(page, queryWrapper);
        
        // 将结果转换为PageResult格式
        return new PageResult<>(resultPage.getRecords(), resultPage.getTotal());
    }

    default List<Demo03CourseDO> selectListByStudentId(Long studentId) {
        return selectList(new LambdaQueryWrapper<Demo03CourseDO>()
                .eq(Demo03CourseDO::getStudentId, studentId));
    }

    default int deleteByStudentId(Long studentId) {
        // Use standard MyBatis Plus deleteByMap method
        java.util.Map<String, Object> map = new java.util.HashMap<>();
        map.put("student_id", studentId);
        return deleteByMap(map);
    }
}