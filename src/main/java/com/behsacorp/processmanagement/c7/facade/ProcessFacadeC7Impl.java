package com.behsacorp.processmanagement.c7.facade;

import com.behsacorp.processmanagement.pm.controller.facade.ProcessFacade;
import com.behsacorp.processmanagement.pm.controller.model.process.ProcessInstanceResponse;
import com.behsacorp.processmanagement.pm.controller.model.process.ProcessResponse;
import com.behsacorp.processmanagement.pm.controller.model.process.ProcessVariableRequest;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

public class ProcessFacadeC7Impl implements ProcessFacade {

    @Autowired
    public RepositoryService repositoryService;

    @Autowired
    public RuntimeService runtimeService;

    @Autowired
    public TaskService taskService;

    @Override
    public ProcessInstanceResponse startProcess(String processName) {
        ProcessInstanceResponse processInstanceResponse = new ProcessInstanceResponse();
        ProcessInstance processInstanceResult = runtimeService
                .createProcessInstanceByKey(processName)
                .execute();
        processInstanceResponse.setProcessInstanceId(processInstanceResult.getRootProcessInstanceId());
        return processInstanceResponse;
    }

    @Override
    public ProcessResponse addVariableToProcess(ProcessVariableRequest processVariableRequest) {
        Map<String, String> variables = new HashMap<>();
        variables.put(processVariableRequest.getVariableName(), processVariableRequest.getVariableValue());
        runtimeService.setVariables(processVariableRequest.getProcessInstanceId(), variables);
        return null;
    }

    @Override
    public ProcessResponse deployProcess(String processName) {
        ProcessResponse processResponse = new ProcessResponse();

        Deployment deployResult = repositoryService.createDeployment()
                .addClasspathResource("process/" + processName + ".bpmn") // Filename of the process model
                .enableDuplicateFiltering(true) // No redeployment when process model remains unchanged
                .deploy();
        processResponse.setDeploymentKey(deployResult.getId());
        return processResponse;
    }

    @Override
    public void claimUserTask(String taskId, String userId) {
        taskService.claim(taskId, userId);
    }

    @Override
    public void completeUserTask(String taskId, Map<String, Object> variables) {
        if (CollectionUtils.isEmpty(variables)) {
            taskService.complete(taskId);
        } else {
            taskService.complete(taskId, variables);
        }
    }
}
