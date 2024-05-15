package com.behsacorp.processmanagement.api.controller.rest;


import com.behsacorp.processmanagement.api.controller.model.WorkflowInstanceResponse;
import com.behsacorp.processmanagement.api.controller.model.WorkflowResponse;
import com.behsacorp.processmanagement.api.controller.model.WorkflowVariableRequest;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import org.camunda.community.eze.ZeebeEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "process", produces = "application/json")
@CrossOrigin(origins = "*")
public class ProcessController {

    @Autowired
    private ZeebeClient zeebe;

    @Autowired
    private ZeebeEngine zeebeEngine;


    @GetMapping("/test")
    public WorkflowResponse test() {
        WorkflowResponse workflowResponse = new WorkflowResponse();
        workflowResponse.setResultDescription("OK.");
        workflowResponse.setResultCode(0);
        return workflowResponse;
    }

    @GetMapping("/start")
    public WorkflowInstanceResponse startWorkflow(@RequestParam String processName, @RequestParam int orderId) {
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

    @PostMapping("/addVariableToProcess")
    public WorkflowResponse addVariableToProcess(@RequestBody WorkflowVariableRequest workflowVariableRequest) {
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

    @GetMapping("/deploy")
    public WorkflowResponse deployWorkflow(@RequestParam String processName) {
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
