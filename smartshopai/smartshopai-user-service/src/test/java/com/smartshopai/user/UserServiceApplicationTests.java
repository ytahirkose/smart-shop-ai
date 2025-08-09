package com.smartshopai.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * Basic test for User Service application
 * Tests if the application context loads successfully
 */
@SpringBootTest
@ActiveProfiles("test")
class UserServiceApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    // JwtTokenProvider not required in this module

    @Test
    void contextLoads() {
        assertNotNull(applicationContext);
        // No additional bean assertions needed.
    }
}
