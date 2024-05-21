package com.behsacorp.processmanagement.c8.controller.bean;

import com.behsacorp.processmanagement.pm.CustomConfig;
import com.behsacorp.processmanagement.c8.facade.ProcessFacadeC8Impl;
import com.behsacorp.processmanagement.pm.controller.facade.ProcessFacade;
import com.behsacorp.processmanagement.pm.exception.PMConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoConfigurations {

    @Autowired
    CustomConfig config;

    @Bean
    public ProcessFacade processFacade() throws PMConfigurationException {
        CustomConfig.CAMUNDA_VERSION camundaVersion = config.getCamundaVersion();
        switch (camundaVersion) {
            case C8 -> {
                return new ProcessFacadeC8Impl();
            }
            default -> throw new PMConfigurationException("Camunda version config is not valid: " + camundaVersion);
        }
    }
}
