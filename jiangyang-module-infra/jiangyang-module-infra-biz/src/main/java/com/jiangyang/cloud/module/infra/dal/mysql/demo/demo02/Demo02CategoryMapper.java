package com.jiangyang.cloud.module.infra.dal.mysql.demo.demo02;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiangyang.cloud.module.infra.controller.admin.demo.demo02.vo.Demo02CategoryListReqVO;
import com.jiangyang.cloud.module.infra.dal.dataobject.demo.demo02.Demo02CategoryDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 示例分类 Mapper
 *
 * @author 江阳科技
 */
@Mapper
public interface Demo02CategoryMapper extends BaseMapper<Demo02CategoryDO> {

    default List<Demo02CategoryDO> selectList(Demo02CategoryListReqVO reqVO) {
        LambdaQueryWrapper<Demo02CategoryDO> queryWrapper = new LambdaQueryWrapper<>();
        // 名称
        if (reqVO.getName() != null) {
            queryWrapper.like(Demo02CategoryDO::getName, reqVO.getName());
        }
        // 父ID
        if (reqVO.getParentId() != null) {
            queryWrapper.eq(Demo02CategoryDO::getParentId, reqVO.getParentId());
        }
        // 创建时间范围
        if (reqVO.getCreateTime() != null && reqVO.getCreateTime().length == 2) {
            queryWrapper.between(Demo02CategoryDO::getCreateTime, 
                    reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]);
        }
        // 排序方式
        queryWrapper.orderByDesc(Demo02CategoryDO::getId);
        
        return selectList(queryWrapper);
    }

	default Demo02CategoryDO selectByParentIdAndName(Long parentId, String name) {
	    return selectOne(new LambdaQueryWrapper<Demo02CategoryDO>()
                .eq(Demo02CategoryDO::getParentId, parentId)
                .eq(Demo02CategoryDO::getName, name));
	}

    default Long selectCountByParentId(Long parentId) {
        return selectCount(new LambdaQueryWrapper<Demo02CategoryDO>()
                .eq(Demo02CategoryDO::getParentId, parentId));
    }
}