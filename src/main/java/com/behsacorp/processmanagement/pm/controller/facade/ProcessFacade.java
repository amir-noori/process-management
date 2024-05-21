package com.behsacorp.processmanagement.pm.controller.facade;

import com.behsacorp.processmanagement.pm.controller.model.process.ProcessInstanceResponse;
import com.behsacorp.processmanagement.pm.controller.model.process.ProcessResponse;
import com.behsacorp.processmanagement.pm.controller.model.process.ProcessVariableRequest;

import java.util.Map;

public interface ProcessFacade {
    public ProcessInstanceResponse startProcess(String processName);

    public ProcessResponse addVariableToProcess(ProcessVariableRequest processVariableRequest);

    public ProcessResponse deployProcess(String processName);

    public void claimUserTask(String taskId, String userId);

    public void completeUserTask(String taskId, Map<String, Object> variables);
}
