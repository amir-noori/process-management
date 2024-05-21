package com.behsacorp.processmanagement.c8.zeebe;

import io.camunda.zeebe.client.ZeebeClient;
import org.camunda.community.eze.EngineFactory;
import org.camunda.community.eze.ZeebeEngine;
import org.camunda.community.eze.configuration.BrokerCfg;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(BrokerCfg.class)
@ConditionalOnClass(ZeebeEngine.class)
@ConditionalOnProperty(prefix = "eze", value = "enable", havingValue = "true", matchIfMissing = true)
public class ZeebeEmbeddedEngineAutoConfiguration implements InitializingBean, DisposableBean {

    @Value("${eze.enable}")
    private String userBucketPath;
    private final BrokerCfg brokerCfg;
    private ZeebeEngine zeebeEngine;

    public ZeebeEmbeddedEngineAutoConfiguration(BrokerCfg brokerCfg) {
        this.brokerCfg = brokerCfg;
    }

    @Bean
    @ConditionalOnMissingBean
    public ZeebeEngine zeebeEngine() {
        return zeebeEngine;
    }

    @Bean
    @ConditionalOnMissingBean
    public ZeebeClient zeebeClient(ZeebeEngine zeebeEngine) {
        return zeebeEngine.createClient();
    }

    @Override
    public void afterPropertiesSet() {
        zeebeEngine = EngineFactory.create(brokerCfg);
        zeebeEngine.start();
    }

    @Override
    public void destroy() {
        zeebeEngine.stop();
    }
}
