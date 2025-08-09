package com.smartshopai.businessintelligence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main application class for Business Intelligence Service
 * Provides analytics, reporting, and business metrics
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class BusinessIntelligenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessIntelligenceApplication.class, args);
    }
}
