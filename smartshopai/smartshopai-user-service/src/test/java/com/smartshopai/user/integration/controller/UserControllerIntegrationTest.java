package com.smartshopai.user.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartshopai.user.application.dto.request.CreateUserRequest;
import com.smartshopai.user.application.dto.request.LoginRequest;
import com.smartshopai.user.domain.entity.User;
import com.smartshopai.user.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for UserController
 */
@SpringBootTest
@ActiveProfiles("test")
class UserControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        userRepository.deleteAll();
    }

    @Test
    void createUser_Success() throws Exception {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
                .email("test@example.com")
                .username("testuser")
                .password("password123")
                .firstName("Test")
                .lastName("User")
                .phoneNumber("+1234567890")
                .preferredCategories(List.of("electronics", "books"))
                .maxBudget(1000.0)
                .preferredBrands("Apple,Samsung")
                .shoppingPreferences("quality,price")
                .build();

        // When & Then
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.email").value("test@example.com"))
                .andExpect(jsonPath("$.data.username").value("testuser"));
    }

    @Test
    void createUser_InvalidRequest_ReturnsBadRequest() throws Exception {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
                .email("invalid-email")
                .username("")
                .password("")
                .build();

        // When & Then
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void loginUser_Success() throws Exception {
        // Given
        User user = createTestUser();
        userRepository.save(user);

        LoginRequest request = LoginRequest.builder()
                .emailOrUsername("test@example.com")
                .password("password123")
                .build();

        // When & Then
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.token").exists())
                .andExpect(jsonPath("$.data.refreshToken").exists());
    }

    @Test
    void loginUser_InvalidCredentials_ReturnsUnauthorized() throws Exception {
        // Given
        LoginRequest request = LoginRequest.builder()
                .emailOrUsername("test@example.com")
                .password("wrongpassword")
                .build();

        // When & Then
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getUserById_Success() throws Exception {
        // Given
        User user = createTestUser();
        userRepository.save(user);

        // When & Then
        mockMvc.perform(get("/api/v1/users/{id}", user.getId())
                        .header("Authorization", "Bearer " + generateTestToken(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(user.getId()))
                .andExpect(jsonPath("$.data.email").value(user.getEmail()));
    }

    @Test
    void getUserById_UserNotFound_ReturnsNotFound() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/users/nonexistent-id")
                        .header("Authorization", "Bearer " + generateTestToken(createTestUser())))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser_Success() throws Exception {
        // Given
        User user = createTestUser();
        userRepository.save(user);

        CreateUserRequest request = CreateUserRequest.builder()
                .firstName("Updated")
                .lastName("Name")
                .phoneNumber("+9876543210")
                .preferredCategories(List.of("clothing"))
                .maxBudget(2000.0)
                .preferredBrands("Nike,Adidas")
                .shoppingPreferences("style,comfort")
                .build();

        // When & Then
        mockMvc.perform(put("/api/v1/users/{id}", user.getId())
                        .header("Authorization", "Bearer " + generateTestToken(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.firstName").value("Updated"));
    }

    @Test
    void getUserInsights_Success() throws Exception {
        // Given
        User user = createTestUser();
        userRepository.save(user);

        // When & Then
        mockMvc.perform(get("/api/v1/users/{id}/insights", user.getId())
                        .header("Authorization", "Bearer " + generateTestToken(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void updateUserBehavior_Success() throws Exception {
        // Given
        User user = createTestUser();
        userRepository.save(user);

        // When & Then
        mockMvc.perform(post("/api/v1/users/{id}/behavior", user.getId())
                        .header("Authorization", "Bearer " + generateTestToken(user))
                        .param("action", "product_view")
                        .param("data", "product_id:123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    private User createTestUser() {
        return User.builder()
                .id("test-user-id")
                .email("test@example.com")
                .username("testuser")
                .password(passwordEncoder.encode("password123"))
                .firstName("Test")
                .lastName("User")
                .phoneNumber("+1234567890")
                .roles(Set.of("USER"))
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .preferredCategories(List.of("electronics", "books"))
                .maxBudget(1000.0)
                .preferredBrands("Apple,Samsung")
                .shoppingPreferences("quality,price")
                .build();
    }

    private String generateTestToken(User user) {
        // This is a simplified token generation for testing
        // In a real scenario, you would use the JwtTokenProvider
        return "test-jwt-token";
    }
}
