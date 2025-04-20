package com.jiangyang.cloud.module.infra.dal.mysql.demo.demo01;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiangyang.cloud.framework.common.pojo.PageResult;
import com.jiangyang.cloud.module.infra.controller.admin.demo.demo01.vo.Demo01ContactPageReqVO;
import com.jiangyang.cloud.module.infra.dal.dataobject.demo.demo01.Demo01ContactDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 示例联系人 Mapper
 *
 * @author 江阳科技
 */
@Mapper
public interface Demo01ContactMapper extends BaseMapper<Demo01ContactDO> {

    default PageResult<Demo01ContactDO> selectPage(Demo01ContactPageReqVO reqVO) {
        // 创建MyBatis Plus的Page对象
        Page<Demo01ContactDO> page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<Demo01ContactDO> queryWrapper = new LambdaQueryWrapper<>();
        // 名字
        if (reqVO.getName() != null) {
            queryWrapper.like(Demo01ContactDO::getName, reqVO.getName());
        }
        // 性别
        if (reqVO.getSex() != null) {
            queryWrapper.eq(Demo01ContactDO::getSex, reqVO.getSex());
        }
        // 创建时间范围
        if (reqVO.getCreateTime() != null && reqVO.getCreateTime().length == 2) {
            queryWrapper.between(Demo01ContactDO::getCreateTime, 
                    reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]);
        }
        // 排序方式
        queryWrapper.orderByDesc(Demo01ContactDO::getId);
        
        // 执行分页查询
        Page<Demo01ContactDO> resultPage = selectPage(page, queryWrapper);
        
        // 将结果转换为PageResult格式
        return new PageResult<>(resultPage.getRecords(), resultPage.getTotal());
    }
}