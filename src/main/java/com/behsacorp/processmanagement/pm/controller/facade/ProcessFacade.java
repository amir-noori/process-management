package com.behsacorp.processmanagement.pm.controller.facade;

import com.behsacorp.processmanagement.pm.controller.model.process.ProcessInstanceExecutionHistoryResponse;
import com.behsacorp.processmanagement.pm.controller.model.process.ProcessInstanceResponse;
import com.behsacorp.processmanagement.pm.controller.model.process.ProcessResponse;
import com.behsacorp.processmanagement.pm.controller.model.process.ProcessVariableRequest;
import com.behsacorp.processmanagement.pm.exception.PMException;

import java.util.Map;

public interface ProcessFacade {
    public ProcessInstanceResponse startProcess(String processName);

    public ProcessResponse addVariableToProcess(ProcessVariableRequest processVariableRequest);

    public ProcessResponse deployProcess(String processName);

    public void claimUserTask(String taskId, String userId);

    public void completeUserTask(String taskId, Map<String, Object> variables);

    public ProcessInstanceExecutionHistoryResponse getProcessInstanceData(String instanceId) throws PMException;

    public void resolveIncident(String instanceId) throws PMException;

    public void retryFailedInstance(String instanceId) throws PMException;
}
