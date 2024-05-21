package com.behsacorp.processmanagement.pm.controller.model.process;

import com.behsacorp.processmanagement.pm.controller.model.BaseResponse;

public class ProcessInstanceRequest extends BaseResponse {

    protected String processInstanceId;

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
}
