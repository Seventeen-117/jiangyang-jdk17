package com.jiangyang.cloud.module.infra.dal.mysql.file;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiangyang.cloud.framework.common.pojo.PageResult;
import com.jiangyang.cloud.module.infra.controller.admin.file.vo.file.FilePageReqVO;
import com.jiangyang.cloud.module.infra.dal.dataobject.file.FileDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件 Mapper
 *
 * @author 江阳科技
 */
@Mapper
public interface FileMapper extends BaseMapper<FileDO> {

    default PageResult<FileDO> selectPage(FilePageReqVO reqVO) {
        // 创建MyBatis Plus的Page对象
        Page<FileDO> page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<FileDO> queryWrapper = new LambdaQueryWrapper<>();
        // 文件路径
        if (reqVO.getPath() != null) {
            queryWrapper.like(FileDO::getPath, reqVO.getPath());
        }
        // 文件类型
        if (reqVO.getType() != null) {
            queryWrapper.eq(FileDO::getType, reqVO.getType());
        }
        // 创建时间范围
        if (reqVO.getCreateTime() != null && reqVO.getCreateTime().length == 2) {
            queryWrapper.between(FileDO::getCreateTime, 
                    reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]);
        }
        // 排序方式
        queryWrapper.orderByDesc(FileDO::getId);
        
        // 执行分页查询
        Page<FileDO> resultPage = selectPage(page, queryWrapper);
        
        // 将结果转换为PageResult格式
        return new PageResult<>(resultPage.getRecords(), resultPage.getTotal());
    }
}
