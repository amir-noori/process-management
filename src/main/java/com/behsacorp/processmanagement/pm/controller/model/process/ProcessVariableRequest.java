package com.behsacorp.processmanagement.pm.controller.model.process;

import com.behsacorp.processmanagement.pm.controller.model.BaseResponse;

public class ProcessVariableRequest extends ProcessInstanceRequest {

    private String variableName;
    private String variableValue;

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableValue() {
        return variableValue;
    }

    public void setVariableValue(String variableValue) {
        this.variableValue = variableValue;
    }
}
