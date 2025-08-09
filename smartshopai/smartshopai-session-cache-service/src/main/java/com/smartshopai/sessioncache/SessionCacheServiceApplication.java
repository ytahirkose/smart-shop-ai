package com.smartshopai.sessioncache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main application class for Session Cache Service
 * Provides session management and real-time session tracking
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
@EnableScheduling
public class SessionCacheServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SessionCacheServiceApplication.class, args);
    }
}
