package com.smartshopai.aisearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AiSearchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiSearchServiceApplication.class, args);
    }
}
