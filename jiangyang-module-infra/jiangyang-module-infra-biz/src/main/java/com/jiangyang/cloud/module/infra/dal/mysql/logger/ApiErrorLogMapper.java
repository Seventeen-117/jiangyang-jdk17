package com.jiangyang.cloud.module.infra.dal.mysql.logger;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiangyang.cloud.framework.common.pojo.PageResult;
import com.jiangyang.cloud.module.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogPageReqVO;
import com.jiangyang.cloud.module.infra.dal.dataobject.logger.ApiErrorLogDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * API 错误日志 Mapper
 *
 * @author 江阳科技
 */
@Mapper
public interface ApiErrorLogMapper extends BaseMapper<ApiErrorLogDO> {

    default PageResult<ApiErrorLogDO> selectPage(ApiErrorLogPageReqVO reqVO) {
        // 创建MyBatis Plus的Page对象
        Page<ApiErrorLogDO> page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<ApiErrorLogDO> queryWrapper = new LambdaQueryWrapper<>();
        // 处理状态
        if (reqVO.getProcessStatus() != null) {
            queryWrapper.eq(ApiErrorLogDO::getProcessStatus, reqVO.getProcessStatus());
        }
        // 用户编号
        if (reqVO.getUserId() != null) {
            queryWrapper.eq(ApiErrorLogDO::getUserId, reqVO.getUserId());
        }
        // 用户类型
        if (reqVO.getUserType() != null) {
            queryWrapper.eq(ApiErrorLogDO::getUserType, reqVO.getUserType());
        }
        // 应用名
        if (reqVO.getApplicationName() != null) {
            queryWrapper.eq(ApiErrorLogDO::getApplicationName, reqVO.getApplicationName());
        }
        // 请求地址
        if (reqVO.getRequestUrl() != null) {
            queryWrapper.like(ApiErrorLogDO::getRequestUrl, reqVO.getRequestUrl());
        }
        // 异常发生时间
        if (reqVO.getExceptionTime() != null && reqVO.getExceptionTime().length == 2) {
            queryWrapper.between(ApiErrorLogDO::getExceptionTime, 
                    reqVO.getExceptionTime()[0], reqVO.getExceptionTime()[1]);
        }
        // 排序方式
        queryWrapper.orderByDesc(ApiErrorLogDO::getId);
        
        // 执行分页查询
        Page<ApiErrorLogDO> resultPage = selectPage(page, queryWrapper);
        
        // 将结果转换为PageResult格式
        return new PageResult<>(resultPage.getRecords(), resultPage.getTotal());
    }

    /**
     * 物理删除指定时间之前的错误日志
     *
     * @param createTime 最大时间
     * @param limit 删除条数，防止一次删除太多
     * @return 删除条数
     */
    @Delete("DELETE FROM infra_api_error_log WHERE create_time < #{createTime} LIMIT #{limit}")
    Integer deleteByCreateTimeLt(@Param("createTime") LocalDateTime createTime, @Param("limit") Integer limit);
}
