package com.behsacorp.processmanagement.pm.controller.model.process;

import com.behsacorp.processmanagement.pm.controller.model.BaseResponse;

public class ProcessResponse extends BaseResponse {

    protected String processName;
    protected String deploymentKey;

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getDeploymentKey() {
        return deploymentKey;
    }

    public void setDeploymentKey(String deploymentKey) {
        this.deploymentKey = deploymentKey;
    }
}
