package com.jiangyang.cloud.module.bpm.service.oa.listener;

import com.jiangyang.cloud.module.bpm.event.BpmProcessInstanceStatusEvent;
import com.jiangyang.cloud.module.bpm.event.BpmProcessInstanceStatusEventListener;
import com.jiangyang.cloud.module.bpm.service.oa.BpmOALeaveService;
import com.jiangyang.cloud.module.bpm.service.oa.BpmOALeaveServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * OA 请假单的结果的监听器实现类
 *
 * @author 江阳科技
 */
@Component
public class BpmOALeaveStatusListener extends BpmProcessInstanceStatusEventListener {

    @Resource
    private BpmOALeaveService leaveService;

    @Override
    protected String getProcessDefinitionKey() {
        return BpmOALeaveServiceImpl.PROCESS_KEY;
    }

    @Override
    protected void onEvent(BpmProcessInstanceStatusEvent event) {
        leaveService.updateLeaveStatus(Long.parseLong(event.getBusinessKey()), event.getStatus());
    }

}
