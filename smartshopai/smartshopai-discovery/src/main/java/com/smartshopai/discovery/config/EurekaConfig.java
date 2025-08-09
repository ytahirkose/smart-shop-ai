package com.smartshopai.discovery.config;

import org.springframework.cloud.netflix.eureka.server.EurekaServerConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EurekaConfig {

    @Bean
    public EurekaServerConfigBean eurekaServerConfigBean() {
        EurekaServerConfigBean config = new EurekaServerConfigBean();
        config.setEnableSelfPreservation(false);
        return config;
    }
}
