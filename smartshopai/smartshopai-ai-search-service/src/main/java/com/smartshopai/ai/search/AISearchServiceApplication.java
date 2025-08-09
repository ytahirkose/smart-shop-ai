package com.smartshopai.ai.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AISearchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AISearchServiceApplication.class, args);
    }
}
