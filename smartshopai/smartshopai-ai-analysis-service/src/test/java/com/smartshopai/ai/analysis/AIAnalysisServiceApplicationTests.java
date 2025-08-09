package com.smartshopai.ai.analysis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import com.smartshopai.security.jwt.JwtTokenProvider;

@SpringBootTest(properties = {
        "spring.cloud.config.enabled=false",
        "spring.ai.openai.api-key=test-key",
        "spring.autoconfigure.exclude=org.springframework.ai.autoconfigure.ollama.OllamaAutoConfiguration",
        "spring.cloud.compatibility-verifier.enabled=false"
})
@ActiveProfiles("test")
class AIAnalysisServiceApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void contextLoads() {
        assertNotNull(applicationContext, "Application context should load");
        assertTrue(applicationContext.containsBean("jwtTokenProvider"));
        // basic token generation check
        String token = jwtTokenProvider.generateTokenFromUsername("test-user", java.util.Collections.emptyMap());
        assertNotNull(token);
    }
}
