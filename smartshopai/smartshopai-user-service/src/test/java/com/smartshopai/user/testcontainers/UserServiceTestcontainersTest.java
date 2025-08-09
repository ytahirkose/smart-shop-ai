package com.smartshopai.user.testcontainers;

import com.smartshopai.user.application.dto.request.CreateUserRequest;
import com.smartshopai.user.application.dto.response.UserResponse;
import com.smartshopai.user.application.service.UserApplicationService;
import com.smartshopai.user.domain.entity.User;
import com.smartshopai.user.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests using Testcontainers
 */
@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class UserServiceTestcontainersTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0")
            .withExposedPorts(27017);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserApplicationService userApplicationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void createUser_WithTestcontainers_Success() {
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

        // When
        UserResponse response = userApplicationService.createUser(request);

        // Then
        assertNotNull(response);
        assertEquals(request.getEmail(), response.getEmail());
        assertEquals(request.getUsername(), response.getUsername());
        assertEquals(request.getFirstName(), response.getFirstName());
        assertEquals(request.getLastName(), response.getLastName());
        assertEquals(request.getPhoneNumber(), response.getPhoneNumber());
        assertEquals(request.getPreferredCategories(), response.getPreferredCategories());
        assertEquals(request.getMaxBudget(), response.getMaxBudget());
        assertEquals(request.getPreferredBrands(), response.getPreferredBrands());
        assertEquals(request.getShoppingPreferences(), response.getShoppingPreferences());
        assertTrue(response.isEnabled());
        assertFalse(response.isEmailVerified());
        assertFalse(response.isPhoneVerified());
    }

    @Test
    void getUserById_WithTestcontainers_Success() {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
                .email("test@example.com")
                .username("testuser")
                .password("password123")
                .firstName("Test")
                .lastName("User")
                .phoneNumber("+1234567890")
                .preferredCategories(List.of("electronics"))
                .maxBudget(1000.0)
                .preferredBrands("Apple")
                .shoppingPreferences("quality")
                .build();

        UserResponse createdUser = userApplicationService.createUser(request);

        // When
        UserResponse foundUser = userApplicationService.getUserById(createdUser.getId());

        // Then
        assertNotNull(foundUser);
        assertEquals(createdUser.getId(), foundUser.getId());
        assertEquals(createdUser.getEmail(), foundUser.getEmail());
        assertEquals(createdUser.getUsername(), foundUser.getUsername());
    }

    @Test
    void updateUser_WithTestcontainers_Success() {
        // Given
        CreateUserRequest createRequest = CreateUserRequest.builder()
                .email("test@example.com")
                .username("testuser")
                .password("password123")
                .firstName("Test")
                .lastName("User")
                .phoneNumber("+1234567890")
                .preferredCategories(List.of("electronics"))
                .maxBudget(1000.0)
                .preferredBrands("Apple")
                .shoppingPreferences("quality")
                .build();

        UserResponse createdUser = userApplicationService.createUser(createRequest);

        CreateUserRequest updateRequest = CreateUserRequest.builder()
                .firstName("Updated")
                .lastName("Name")
                .phoneNumber("+9876543210")
                .preferredCategories(List.of("clothing"))
                .maxBudget(2000.0)
                .preferredBrands("Nike,Adidas")
                .shoppingPreferences("style,comfort")
                .build();

        // When
        UserResponse updatedUser = userApplicationService.updateUser(createdUser.getId(), updateRequest);

        // Then
        assertNotNull(updatedUser);
        assertEquals(createdUser.getId(), updatedUser.getId());
        assertEquals("Updated", updatedUser.getFirstName());
        assertEquals("Name", updatedUser.getLastName());
        assertEquals("+9876543210", updatedUser.getPhoneNumber());
        assertEquals(List.of("clothing"), updatedUser.getPreferredCategories());
        assertEquals(2000.0, updatedUser.getMaxBudget());
        assertEquals("Nike,Adidas", updatedUser.getPreferredBrands());
        assertEquals("style,comfort", updatedUser.getShoppingPreferences());
    }

    @Test
    void getUserInsights_WithTestcontainers_Success() {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
                .email("test@example.com")
                .username("testuser")
                .password("password123")
                .firstName("Test")
                .lastName("User")
                .phoneNumber("+1234567890")
                .preferredCategories(List.of("electronics"))
                .maxBudget(1000.0)
                .preferredBrands("Apple")
                .shoppingPreferences("quality")
                .build();

        UserResponse createdUser = userApplicationService.createUser(request);

        // When
        String insights = userApplicationService.getUserInsights(createdUser.getId());

        // Then
        assertNotNull(insights);
        assertFalse(insights.isEmpty());
    }

    @Test
    void updateUserBehavior_WithTestcontainers_Success() {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
                .email("test@example.com")
                .username("testuser")
                .password("password123")
                .firstName("Test")
                .lastName("User")
                .phoneNumber("+1234567890")
                .preferredCategories(List.of("electronics"))
                .maxBudget(1000.0)
                .preferredBrands("Apple")
                .shoppingPreferences("quality")
                .build();

        UserResponse createdUser = userApplicationService.createUser(request);

        // When
        userApplicationService.updateUserBehavior(createdUser.getId(), "product_view", "product_id:123");

        // Then
        // Verify that the behavior was updated by checking if we can get insights
        String insights = userApplicationService.getUserInsights(createdUser.getId());
        assertNotNull(insights);
    }

    @Test
    void getUsersByPreferences_WithTestcontainers_Success() {
        // Given
        CreateUserRequest request1 = CreateUserRequest.builder()
                .email("test1@example.com")
                .username("testuser1")
                .password("password123")
                .firstName("Test")
                .lastName("User1")
                .phoneNumber("+1234567890")
                .preferredCategories(List.of("electronics"))
                .maxBudget(1000.0)
                .preferredBrands("Apple")
                .shoppingPreferences("quality")
                .build();

        CreateUserRequest request2 = CreateUserRequest.builder()
                .email("test2@example.com")
                .username("testuser2")
                .password("password123")
                .firstName("Test")
                .lastName("User2")
                .phoneNumber("+1234567891")
                .preferredCategories(List.of("books"))
                .maxBudget(500.0)
                .preferredBrands("Amazon")
                .shoppingPreferences("price")
                .build();

        userApplicationService.createUser(request1);
        userApplicationService.createUser(request2);

        // When
        List<UserResponse> users = userApplicationService.getUsersByPreferences(List.of("electronics"), 1500.0);

        // Then
        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals("test1@example.com", users.get(0).getEmail());
    }

    @Test
    void setUserEnabled_WithTestcontainers_Success() {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
                .email("test@example.com")
                .username("testuser")
                .password("password123")
                .firstName("Test")
                .lastName("User")
                .phoneNumber("+1234567890")
                .preferredCategories(List.of("electronics"))
                .maxBudget(1000.0)
                .preferredBrands("Apple")
                .shoppingPreferences("quality")
                .build();

        UserResponse createdUser = userApplicationService.createUser(request);

        // When
        userApplicationService.setUserEnabled(createdUser.getId(), false);

        // Then
        UserResponse updatedUser = userApplicationService.getUserById(createdUser.getId());
        assertFalse(updatedUser.isEnabled());
    }

    @Test
    void verifyEmail_WithTestcontainers_Success() {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
                .email("test@example.com")
                .username("testuser")
                .password("password123")
                .firstName("Test")
                .lastName("User")
                .phoneNumber("+1234567890")
                .preferredCategories(List.of("electronics"))
                .maxBudget(1000.0)
                .preferredBrands("Apple")
                .shoppingPreferences("quality")
                .build();

        UserResponse createdUser = userApplicationService.createUser(request);

        // When
        userApplicationService.verifyEmail(createdUser.getId());

        // Then
        UserResponse updatedUser = userApplicationService.getUserById(createdUser.getId());
        assertTrue(updatedUser.isEmailVerified());
    }

    @Test
    void verifyPhone_WithTestcontainers_Success() {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
                .email("test@example.com")
                .username("testuser")
                .password("password123")
                .firstName("Test")
                .lastName("User")
                .phoneNumber("+1234567890")
                .preferredCategories(List.of("electronics"))
                .maxBudget(1000.0)
                .preferredBrands("Apple")
                .shoppingPreferences("quality")
                .build();

        UserResponse createdUser = userApplicationService.createUser(request);

        // When
        userApplicationService.verifyPhone(createdUser.getId());

        // Then
        UserResponse updatedUser = userApplicationService.getUserById(createdUser.getId());
        assertTrue(updatedUser.isPhoneVerified());
    }
}
