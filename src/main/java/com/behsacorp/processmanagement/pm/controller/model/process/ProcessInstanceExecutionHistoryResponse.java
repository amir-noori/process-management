package com.behsacorp.processmanagement.pm.controller.model.process;


import com.behsacorp.processmanagement.pm.dto.ActivityInstanceDto;

import java.util.List;

public class ProcessInstanceExecutionHistoryResponse extends ProcessInstanceResponse {

    private List<ActivityInstanceDto> activityInstanceDtoList;

    public List<ActivityInstanceDto> getActivityInstanceDtoList() {
        return activityInstanceDtoList;
    }

    public void setActivityInstanceDtoList(List<ActivityInstanceDto> activityInstanceDtoList) {
        this.activityInstanceDtoList = activityInstanceDtoList;
    }
}
