package com.behsacorp.processmanagement.c7.facade;

import com.behsacorp.processmanagement.c7.util.DtoUtil;
import com.behsacorp.processmanagement.pm.controller.facade.ProcessFacade;
import com.behsacorp.processmanagement.pm.controller.model.process.ProcessInstanceExecutionHistoryResponse;
import com.behsacorp.processmanagement.pm.controller.model.process.ProcessInstanceResponse;
import com.behsacorp.processmanagement.pm.controller.model.process.ProcessResponse;
import com.behsacorp.processmanagement.pm.controller.model.process.ProcessVariableRequest;
import com.behsacorp.processmanagement.pm.exception.PMException;
import com.behsacorp.processmanagement.pm.exception.PMIncidentTypeHandleException;
import com.behsacorp.processmanagement.pm.exception.PMNoIncidentException;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.runtime.Incident;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessFacadeC7Impl implements ProcessFacade {

    @Autowired
    public RepositoryService repositoryService;

    @Autowired
    public RuntimeService runtimeService;

    @Autowired
    public TaskService taskService;

    @Autowired
    public ManagementService managementService;

    @Autowired
    public HistoryService historyService;

    @Override
    public ProcessInstanceResponse startProcess(String processName) {
        ProcessInstanceResponse processInstanceResponse = new ProcessInstanceResponse();
//        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
//                .processDefinitionKey("invoice")
//                .processDefinitionVersion(1).singleResult();
        ProcessInstance processInstanceResult = runtimeService
                .createProcessInstanceByKey(processName)
                .execute();
        processInstanceResponse.setProcessInstanceId(processInstanceResult.getProcessInstanceId());
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

    @Override
    public void resolveIncident(String instanceId) throws PMException {
        Incident incident = runtimeService
                .createIncidentQuery()
                .processInstanceId(instanceId)
                .singleResult();
        if (incident == null) {
            throw new PMNoIncidentException();
        } else {
            runtimeService.resolveIncident(incident.getId());
        }
    }

    @Override
    public ProcessInstanceExecutionHistoryResponse getProcessInstanceData(String instanceId) throws PMException {
        ProcessInstanceExecutionHistoryResponse response = new ProcessInstanceExecutionHistoryResponse();
        List<HistoricActivityInstance> historicActivityInstances = historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(instanceId)
                .orderByHistoricActivityInstanceStartTime().asc()
                .list();

        response.setActivityInstanceDtoList(DtoUtil.copyFromHistoricActivityInstances(historicActivityInstances));
        return response;
    }

    @Override
    public void retryFailedInstance(String instanceId) throws PMException {
        Incident incident = runtimeService
                .createIncidentQuery()
                .processInstanceId(instanceId)
                .singleResult();

        if (incident == null) {
            throw new PMNoIncidentException();
        } else {
            if (incident.getIncidentType().equals("failedJob")) {
                Job job = managementService
                        .createJobQuery()
                        .processInstanceId(instanceId)
                        .singleResult();

                String jobId = job.getId();

                managementService.setJobRetries(jobId, 10);
            } else {
                throw new PMIncidentTypeHandleException("incident of type " + incident.getIncidentType() + " is not handled.");
            }
        }
    }
}
