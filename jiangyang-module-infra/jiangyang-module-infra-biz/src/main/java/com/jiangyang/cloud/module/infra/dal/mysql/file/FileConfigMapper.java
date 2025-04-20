package com.jiangyang.cloud.module.infra.dal.mysql.file;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiangyang.cloud.framework.common.pojo.PageResult;
import com.jiangyang.cloud.module.infra.controller.admin.file.vo.config.FileConfigPageReqVO;
import com.jiangyang.cloud.module.infra.dal.dataobject.file.FileConfigDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileConfigMapper extends BaseMapper<FileConfigDO> {

    default PageResult<FileConfigDO> selectPage(FileConfigPageReqVO reqVO) {
        // 创建MyBatis Plus的Page对象
        Page<FileConfigDO> page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<FileConfigDO> queryWrapper = new LambdaQueryWrapper<>();
        // 配置名
        if (reqVO.getName() != null) {
            queryWrapper.like(FileConfigDO::getName, reqVO.getName());
        }
        // 存储器
        if (reqVO.getStorage() != null) {
            queryWrapper.eq(FileConfigDO::getStorage, reqVO.getStorage());
        }
        // 创建时间范围
        if (reqVO.getCreateTime() != null && reqVO.getCreateTime().length == 2) {
            queryWrapper.between(FileConfigDO::getCreateTime, 
                    reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]);
        }
        // 排序方式
        queryWrapper.orderByDesc(FileConfigDO::getId);
        
        // 执行分页查询
        Page<FileConfigDO> resultPage = selectPage(page, queryWrapper);
        
        // 将结果转换为PageResult格式
        return new PageResult<>(resultPage.getRecords(), resultPage.getTotal());
    }

    default FileConfigDO selectByMaster() {
        return selectOne(new LambdaQueryWrapper<FileConfigDO>()
                .eq(FileConfigDO::getMaster, true));
    }
}
