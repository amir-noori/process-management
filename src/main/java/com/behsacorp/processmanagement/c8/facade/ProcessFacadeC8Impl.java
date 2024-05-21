package com.behsacorp.processmanagement.c8.facade;

import com.behsacorp.processmanagement.pm.controller.facade.ProcessFacade;
import com.behsacorp.processmanagement.pm.controller.model.WorkflowInstanceResponse;
import com.behsacorp.processmanagement.pm.controller.model.WorkflowResponse;
import com.behsacorp.processmanagement.pm.controller.model.WorkflowVariableRequest;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import org.camunda.community.eze.ZeebeEngine;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class ProcessFacadeC8Impl implements ProcessFacade {

    @Autowired
    private ZeebeClient zeebe;

    @Autowired
    private ZeebeEngine zeebeEngine;

    @Override
    public WorkflowInstanceResponse startProcess(String processName, int orderId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("orderId", orderId);

        ProcessInstanceEvent processInstanceEvent = zeebe
                .newCreateInstanceCommand()
                .bpmnProcessId(processName)
                .latestVersion()
                .variables(variables)
                .send()
                .join();

        WorkflowInstanceResponse workflowInstanceResponse = new WorkflowInstanceResponse();
        workflowInstanceResponse.setResultDescription("started.");
        workflowInstanceResponse.setProcessInstanceKey(processInstanceEvent.getProcessInstanceKey());
        workflowInstanceResponse.setResultCode(0);
        return workflowInstanceResponse;
    }

    @Override
    public WorkflowResponse addVariableToProcess(WorkflowVariableRequest workflowVariableRequest) {
        Map<String, Object> vars = new HashMap<>();
        vars.put(workflowVariableRequest.getVariableName(), workflowVariableRequest.getVariableValue());

        zeebe
                .newSetVariablesCommand(workflowVariableRequest.getProcessInstanceKey())
                .variables(vars)
                .send();

        WorkflowResponse workflowResponse = new WorkflowResponse();
        workflowResponse.setResultDescription("variable " + workflowVariableRequest.getVariableName() + " added/edited");
        workflowResponse.setResultCode(0);
        return workflowResponse;
    }

    @Override
    public WorkflowResponse deployProcess(String processName) {
        WorkflowResponse workflowResponse = new WorkflowResponse();

        if (!processName.endsWith(".bpmn")) {
            processName += ".bpmn";
        }

        try {
            DeploymentEvent deploymentEvent = zeebe
                    .newDeployResourceCommand()
                    .addResourceFromClasspath("process/" + processName)
                    .send()
                    .join();

            long deploymentEventKey = deploymentEvent.getKey();
            System.out.println("deployment with key: " + deploymentEventKey);
            workflowResponse.setResultDescription("deployed.");
            workflowResponse.setResultCode(0);

        } catch (Exception e) {
            if (e.getCause() instanceof FileNotFoundException) {
                workflowResponse.setResultDescription("process not found.");
                workflowResponse.setResultCode(101);
            } else {
                e.printStackTrace();
                workflowResponse.setResultDescription("unknown error.");
                workflowResponse.setResultCode(100);
            }
        }

        return workflowResponse;
    }
}
