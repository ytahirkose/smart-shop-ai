package com.smartshopai.product;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {
        "spring.cloud.config.enabled=false",
        "spring.cloud.config.import-check.enabled=false",
        "eureka.client.enabled=false",
        "spring.config.import=optional:configserver:",
        "spring.data.mongodb.host=localhost",
        "spring.data.mongodb.port=0",
        "spring.data.mongodb.database=test",
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration,org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration"
})
@ActiveProfiles("test")
class ProductServiceApplicationTests {

    @Test
    void contextLoads() {
        // Test that the application context loads successfully
    }
}
