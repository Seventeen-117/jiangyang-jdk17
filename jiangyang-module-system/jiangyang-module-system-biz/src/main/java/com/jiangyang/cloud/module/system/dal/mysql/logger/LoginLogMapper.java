package com.jiangyang.cloud.module.system.dal.mysql.logger;

import com.jiangyang.cloud.framework.common.pojo.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiangyang.cloud.module.system.controller.admin.logger.vo.loginlog.LoginLogPageReqVO;
import com.jiangyang.cloud.module.system.dal.dataobject.logger.LoginLogDO;
import com.jiangyang.cloud.module.system.enums.logger.LoginResultEnum;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLogDO> {

    default PageResult<LoginLogDO> selectPage(LoginLogPageReqVO reqVO) {
        // 创建MyBatis Plus的Page对象
        Page<LoginLogDO> page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<LoginLogDO> query = new LambdaQueryWrapper<>();
        // 用户IP
        if (reqVO.getUserIp() != null) {
            query.like(LoginLogDO::getUserIp, reqVO.getUserIp());
        }
        // 用户名
        if (reqVO.getUsername() != null) {
            query.like(LoginLogDO::getUsername, reqVO.getUsername());
        }
        // 创建时间范围
        if (reqVO.getCreateTime() != null) {
            query.between(LoginLogDO::getCreateTime, reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]);
        }
        // 登录状态
        if (Boolean.TRUE.equals(reqVO.getStatus())) {
            query.eq(LoginLogDO::getResult, LoginResultEnum.SUCCESS.getResult());
        } else if (Boolean.FALSE.equals(reqVO.getStatus())) {
            query.gt(LoginLogDO::getResult, LoginResultEnum.SUCCESS.getResult());
        }
        // 降序排序
        query.orderByDesc(LoginLogDO::getId);
        
        // 执行分页查询
        Page<LoginLogDO> resultPage = selectPage(page, query);
        
        // 将结果转换为PageResult格式
        return new PageResult<>(resultPage.getRecords(), resultPage.getTotal());
    }
}
