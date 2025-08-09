package com.smartshopai.ai.recommendation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AIRecommendationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AIRecommendationServiceApplication.class, args);
    }
}
