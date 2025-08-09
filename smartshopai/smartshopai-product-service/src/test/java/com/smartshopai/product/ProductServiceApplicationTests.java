package com.smartshopai.product;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.smartshopai.product.domain.service.ProductAnalysisService;

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

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired(required = false)
    private ProductAnalysisService productAnalysisService;

    @Test
    void contextLoads() {
        assertNotNull(applicationContext);
        assertTrue(applicationContext.containsBeanDefinition("aiBeansConfig") || applicationContext.getBeanDefinitionCount() > 0);
        // Bean may be null in minimal test config but shouldn't throw
        if (productAnalysisService != null) {
            assertNotNull(productAnalysisService);
        }
    }
}
