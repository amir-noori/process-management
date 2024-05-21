package com.behsacorp.processmanagement.pm.controller.model.task;

import com.behsacorp.processmanagement.pm.controller.model.process.ProcessInstanceRequest;

import java.util.Map;

public class CompleteUserTaskRequest extends ProcessInstanceRequest {
    private String taskId;
    private Map<String, Object> variables;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
}
