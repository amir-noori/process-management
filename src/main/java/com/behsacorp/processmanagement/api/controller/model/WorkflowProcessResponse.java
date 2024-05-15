package com.behsacorp.processmanagement.api.controller.model;


import com.behsacorp.processmanagement.dto.ProcessDto;

public class WorkflowProcessResponse extends BaseResponse {

    private ProcessDto processDto;

    public ProcessDto getProcessDto() {
        return processDto;
    }

    public void setProcessDto(ProcessDto processDto) {
        this.processDto = processDto;
    }
}
