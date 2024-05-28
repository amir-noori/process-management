package com.behsacorp.processmanagement.c7.util;

import com.behsacorp.processmanagement.pm.dto.ActivityInstanceDto;
import org.camunda.bpm.engine.history.HistoricActivityInstance;

import java.util.ArrayList;
import java.util.List;

public class DtoUtil {

    public static List<ActivityInstanceDto> copyFromHistoricActivityInstances(List<HistoricActivityInstance> historicActivityInstances) {
        List<ActivityInstanceDto> activityInstanceDtoList = new ArrayList<>();
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            ActivityInstanceDto activityInstanceDto = new ActivityInstanceDto();
            activityInstanceDto.setActivityId(historicActivityInstance.getActivityId());
            activityInstanceDto.setActivityName(historicActivityInstance.getActivityName());
            activityInstanceDto.setActivityType(historicActivityInstance.getActivityType());
            activityInstanceDto.setId(historicActivityInstance.getId());
            activityInstanceDto.setStartTime(historicActivityInstance.getStartTime());
            activityInstanceDto.setEndTime(historicActivityInstance.getEndTime());
            activityInstanceDto.setTenantId(historicActivityInstance.getTenantId());
            activityInstanceDto.setTaskId(historicActivityInstance.getTaskId());
            activityInstanceDto.setDurationInMillis(historicActivityInstance.getDurationInMillis());

            activityInstanceDtoList.add(activityInstanceDto);
        }
        return activityInstanceDtoList;
    }
}
