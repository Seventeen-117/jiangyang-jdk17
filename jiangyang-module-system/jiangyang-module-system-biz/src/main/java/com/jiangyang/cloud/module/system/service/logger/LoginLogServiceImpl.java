package com.jiangyang.cloud.module.system.service.logger;

import com.jiangyang.cloud.framework.common.pojo.PageResult;
import com.jiangyang.cloud.framework.common.util.object.BeanUtils;
import com.jiangyang.cloud.module.system.api.logger.dto.LoginLogCreateReqDTO;
import com.jiangyang.cloud.module.system.controller.admin.logger.vo.loginlog.LoginLogPageReqVO;
import com.jiangyang.cloud.module.system.dal.dataobject.logger.LoginLogDO;
import com.jiangyang.cloud.module.system.dal.mysql.logger.LoginLogMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;

/**
 * 登录日志 Service 实现
 */
@Service
@Validated
public class LoginLogServiceImpl implements LoginLogService {

    @Resource
    private LoginLogMapper loginLogMapper;

    @Override
    public PageResult<LoginLogDO> getLoginLogPage(LoginLogPageReqVO pageReqVO) {
        return loginLogMapper.selectPage(pageReqVO);
    }

    @Override
    public void createLoginLog(LoginLogCreateReqDTO reqDTO) {
        LoginLogDO loginLog = BeanUtils.toBean(reqDTO, LoginLogDO.class);
        loginLogMapper.insert(loginLog);
    }

}
