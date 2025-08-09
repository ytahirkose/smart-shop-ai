package com.smartshopai.ai.analysis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {
        "spring.cloud.config.enabled=false",
        "spring.ai.openai.api-key=test-key",
        "spring.autoconfigure.exclude=org.springframework.ai.autoconfigure.ollama.OllamaAutoConfiguration",
        "spring.cloud.compatibility-verifier.enabled=false"
})
@ActiveProfiles("test")
class AIAnalysisServiceApplicationTests {

    @Test
    void contextLoads() {
        // Test that the application context loads successfully
    }
}
