package com.behsacorp.processmanagement.pm.controller.rest;


import com.behsacorp.processmanagement.common.Results;
import com.behsacorp.processmanagement.pm.controller.facade.ProcessFacade;
import com.behsacorp.processmanagement.pm.controller.model.process.*;
import com.behsacorp.processmanagement.pm.controller.model.task.CompleteUserTaskRequest;
import com.behsacorp.processmanagement.pm.exception.PMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "process", produces = "application/json")
@CrossOrigin(origins = "*")
public class ProcessController {

    @Autowired
    ProcessFacade processFacade;

    @GetMapping("/test")
    public ProcessResponse test() {
        ProcessResponse workflowResponse = new ProcessResponse();
        workflowResponse.setResultDescription("OK.");
        workflowResponse.setResultCode(Results.SUCCESS.code);
        return workflowResponse;
    }

    @GetMapping("/start")
    public ProcessInstanceResponse startProcess(@RequestParam String processName) {
        ProcessInstanceResponse workflowInstanceResponse = processFacade.startProcess(processName);
        workflowInstanceResponse.setResultDescription("started.");
        workflowInstanceResponse.setResultCode(Results.SUCCESS.code);
        return workflowInstanceResponse;
    }

    @PostMapping("/addVariableToProcess")
    public ProcessResponse addVariableToProcess(@RequestBody ProcessVariableRequest workflowVariableRequest) {
        ProcessResponse workflowResponse = new ProcessResponse();
        processFacade.addVariableToProcess(workflowVariableRequest);
        workflowResponse.setResultDescription("variable added.");
        workflowResponse.setResultCode(Results.SUCCESS.code);
        return workflowResponse;
    }

    @GetMapping("/deploy")
    public ProcessResponse deployProcess(@RequestParam String processName) {
        ProcessResponse workflowResponse = new ProcessResponse();
        processFacade.deployProcess(processName);
        workflowResponse.setResultDescription("deployed.");
        workflowResponse.setResultCode(Results.SUCCESS.code);
        return workflowResponse;
    }

    @GetMapping("/delete")
    public ProcessResponse deleteProcess(@RequestParam String processName) {
        // TODO: first check the running instances
        return null;
    }
    @GetMapping("/claimUserTask")
    public ProcessResponse claimUserTask(@RequestParam String taskId, @RequestParam String userId) {
        ProcessResponse processResponse = new ProcessResponse();
        processFacade.claimUserTask(taskId, userId);
        processResponse.setResultDescription("task claimed successfully.");
        processResponse.setResultCode(Results.SUCCESS.code);
        return processResponse;
    }

    @PostMapping("/completeUserTask")
    public ProcessResponse completeUserTask(@RequestBody CompleteUserTaskRequest completeUserTaskRequest) {
        ProcessResponse processResponse = new ProcessResponse();
        processFacade.completeUserTask(completeUserTaskRequest.getTaskId(), completeUserTaskRequest.getVariables());
        processResponse.setResultDescription("task completed successfully.");
        processResponse.setResultCode(Results.SUCCESS.code);
        return processResponse;
    }

    @GetMapping("/getProcessInstanceData")
    public ProcessInstanceResponse getProcessInstanceData(@RequestParam String processId) throws PMException {
        return processFacade.getProcessInstanceData(processId);
    }

    @PostMapping("/resolveIncident")
    public ProcessResolveResponse resolveIncident(@RequestBody ProcessInstanceRequest processInstanceRequest) throws PMException {
        ProcessResolveResponse processResolveResponse = new ProcessResolveResponse();
        processFacade.resolveIncident(processInstanceRequest.getProcessInstanceId());
        return processResolveResponse;
    }

    @PostMapping("/retryFailedInstance")
    public ProcessResponse retryFailedInstance(@RequestBody ProcessInstanceRequest processInstanceRequest) throws PMException {
        ProcessResponse processResponse = new ProcessResponse();
        processFacade.retryFailedInstance(processInstanceRequest.getProcessInstanceId());
        return processResponse;
    }

}
