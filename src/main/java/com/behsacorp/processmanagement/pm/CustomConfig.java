package com.behsacorp.processmanagement.pm;


import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "custom-config")
@EnableAutoConfiguration
@EnableConfigurationProperties
public class CustomConfig {

    private CAMUNDA_VERSION camundaVersion;

    @PostConstruct
    private void postConstruct() {
        if (camundaVersion == null) {
            System.out.println("WARN: cannot find camunda version environment variable setting it to default value: C7");
            camundaVersion = CAMUNDA_VERSION.C7;
        }
    }

    public CAMUNDA_VERSION getCamundaVersion() {
        return camundaVersion;
    }

    public void setCamundaVersion(CAMUNDA_VERSION camundaVersion) {
        this.camundaVersion = camundaVersion;
    }

    public enum CAMUNDA_VERSION {
        C7("C7"),
        C8("C8");

        private final String value;

        CAMUNDA_VERSION(String value) {
            this.value = value;
        }
    }
}
