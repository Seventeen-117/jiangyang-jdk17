package com.jiangyang.cloud.module.infra.dal.mysql.logger;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiangyang.cloud.framework.common.pojo.PageResult;
import com.jiangyang.cloud.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogPageReqVO;
import com.jiangyang.cloud.module.infra.dal.dataobject.logger.ApiAccessLogDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * API 访问日志 Mapper
 *
 * @author 江阳科技
 */
@Mapper
public interface ApiAccessLogMapper extends BaseMapper<ApiAccessLogDO> {

    default PageResult<ApiAccessLogDO> selectPage(ApiAccessLogPageReqVO reqVO) {
        // 创建MyBatis Plus的Page对象
        Page<ApiAccessLogDO> page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<ApiAccessLogDO> queryWrapper = new LambdaQueryWrapper<>();
        // 用户编号
        if (reqVO.getUserId() != null) {
            queryWrapper.eq(ApiAccessLogDO::getUserId, reqVO.getUserId());
        }
        // 用户类型
        if (reqVO.getUserType() != null) {
            queryWrapper.eq(ApiAccessLogDO::getUserType, reqVO.getUserType());
        }
        // 应用名
        if (reqVO.getApplicationName() != null) {
            queryWrapper.eq(ApiAccessLogDO::getApplicationName, reqVO.getApplicationName());
        }
        // 请求地址
        if (reqVO.getRequestUrl() != null) {
            queryWrapper.like(ApiAccessLogDO::getRequestUrl, reqVO.getRequestUrl());
        }
        // 开始时间范围
        if (reqVO.getBeginTime() != null && reqVO.getBeginTime().length == 2) {
            queryWrapper.between(ApiAccessLogDO::getBeginTime, 
                    reqVO.getBeginTime()[0], reqVO.getBeginTime()[1]);
        }
        // 执行时长
        if (reqVO.getDuration() != null) {
            queryWrapper.ge(ApiAccessLogDO::getDuration, reqVO.getDuration());
        }
        // 结果码
        if (reqVO.getResultCode() != null) {
            queryWrapper.eq(ApiAccessLogDO::getResultCode, reqVO.getResultCode());
        }
        // 排序方式
        queryWrapper.orderByDesc(ApiAccessLogDO::getId);
        
        // 执行分页查询
        Page<ApiAccessLogDO> resultPage = selectPage(page, queryWrapper);
        
        // 将结果转换为PageResult格式
        return new PageResult<>(resultPage.getRecords(), resultPage.getTotal());
    }

    /**
     * 物理删除指定时间之前的访问日志
     *
     * @param createTime 最大时间
     * @param limit 删除条数，防止一次删除太多
     * @return 删除条数
     */
    @Delete("DELETE FROM infra_api_access_log WHERE create_time < #{createTime} LIMIT #{limit}")
    Integer deleteByCreateTimeLt(@Param("createTime") LocalDateTime createTime, @Param("limit")Integer limit);
}
