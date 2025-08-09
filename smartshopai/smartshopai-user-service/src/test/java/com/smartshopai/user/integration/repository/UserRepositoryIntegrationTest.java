package com.smartshopai.user.integration.repository;

import com.smartshopai.user.domain.entity.User;
import com.smartshopai.user.domain.entity.UserBehaviorMetrics;
import com.smartshopai.user.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for UserRepository
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        
        testUser = User.builder()
                .id("test-user-id")
                .email("test@example.com")
                .username("testuser")
                .password("encodedPassword")
                .firstName("Test")
                .lastName("User")
                .phoneNumber("+1234567890")
                .roles(Set.of("USER"))
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .preferredCategories(List.of("electronics", "books"))
                .maxBudget(1000.0)
                .preferredBrands("Apple,Samsung")
                .shoppingPreferences("quality,price")
                .behaviorMetrics(UserBehaviorMetrics.builder()
                        .userId("test-user-id")
                        .aiInsights("User prefers quality over price")
                        .shoppingPersonality("Quality-focused")
                        .totalSearches(50)
                        .totalProductViews(100)
                        .totalPurchases(10)
                        .totalSpent(2500.0)
                        .lastUpdated(LocalDateTime.now())
                        .build())
                .build();
    }

    @Test
    void saveAndFindById_Success() {
        // Given
        User savedUser = userRepository.save(testUser);

        // When
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        // Then
        assertTrue(foundUser.isPresent());
        assertEquals(testUser.getEmail(), foundUser.get().getEmail());
        assertEquals(testUser.getUsername(), foundUser.get().getUsername());
    }

    @Test
    void findByEmail_Success() {
        // Given
        userRepository.save(testUser);

        // When
        Optional<User> foundUser = userRepository.findByEmail(testUser.getEmail());

        // Then
        assertTrue(foundUser.isPresent());
        assertEquals(testUser.getEmail(), foundUser.get().getEmail());
    }

    @Test
    void findByEmail_UserNotFound() {
        // When
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");

        // Then
        assertFalse(foundUser.isPresent());
    }

    @Test
    void findByUsername_Success() {
        // Given
        userRepository.save(testUser);

        // When
        Optional<User> foundUser = userRepository.findByUsername(testUser.getUsername());

        // Then
        assertTrue(foundUser.isPresent());
        assertEquals(testUser.getUsername(), foundUser.get().getUsername());
    }

    @Test
    void findByUsername_UserNotFound() {
        // When
        Optional<User> foundUser = userRepository.findByUsername("nonexistentuser");

        // Then
        assertFalse(foundUser.isPresent());
    }

    @Test
    void existsByEmail_Success() {
        // Given
        userRepository.save(testUser);

        // When
        boolean exists = userRepository.existsByEmail(testUser.getEmail());

        // Then
        assertTrue(exists);
    }

    @Test
    void existsByEmail_UserNotFound() {
        // When
        boolean exists = userRepository.existsByEmail("nonexistent@example.com");

        // Then
        assertFalse(exists);
    }

    @Test
    void existsByUsername_Success() {
        // Given
        userRepository.save(testUser);

        // When
        boolean exists = userRepository.existsByUsername(testUser.getUsername());

        // Then
        assertTrue(exists);
    }

    @Test
    void existsByUsername_UserNotFound() {
        // When
        boolean exists = userRepository.existsByUsername("nonexistentuser");

        // Then
        assertFalse(exists);
    }

    @Test
    void findByRolesContaining_Success() {
        // Given
        userRepository.save(testUser);

        // When
        List<User> users = userRepository.findByRolesContaining("USER");

        // Then
        assertFalse(users.isEmpty());
        assertEquals(testUser.getEmail(), users.get(0).getEmail());
    }

    @Test
    void findByEnabledTrue_Success() {
        // Given
        userRepository.save(testUser);

        // When
        List<User> users = userRepository.findByEnabledTrue();

        // Then
        assertFalse(users.isEmpty());
        assertEquals(testUser.getEmail(), users.get(0).getEmail());
    }

    @Test
    void findByPreferredCategoriesIn_Success() {
        // Given
        userRepository.save(testUser);

        // When
        List<User> users = userRepository.findByPreferredCategoriesIn(List.of("electronics"));

        // Then
        assertFalse(users.isEmpty());
        assertEquals(testUser.getEmail(), users.get(0).getEmail());
    }

    @Test
    void findByMaxBudgetGreaterThanEqual_Success() {
        // Given
        userRepository.save(testUser);

        // When
        List<User> users = userRepository.findByMaxBudgetGreaterThanEqual(500.0);

        // Then
        assertFalse(users.isEmpty());
        assertEquals(testUser.getEmail(), users.get(0).getEmail());
    }

    @Test
    void findByShoppingPreferencesContaining_Success() {
        // Given
        userRepository.save(testUser);

        // When
        List<User> users = userRepository.findByShoppingPreferencesContaining("quality");

        // Then
        assertFalse(users.isEmpty());
        assertEquals(testUser.getEmail(), users.get(0).getEmail());
    }

    @Test
    void countByEnabled_Success() {
        // Given
        userRepository.save(testUser);

        // When
        long count = userRepository.countByEnabled(true);

        // Then
        assertEquals(1, count);
    }

    @Test
    void countByEmailVerified_Success() {
        // Given
        testUser.setEmailVerified(true);
        userRepository.save(testUser);

        // When
        long count = userRepository.countByEmailVerified(true);

        // Then
        assertEquals(1, count);
    }

    @Test
    void updateUser_Success() {
        // Given
        User savedUser = userRepository.save(testUser);
        savedUser.setFirstName("Updated");
        savedUser.setLastName("Name");

        // When
        User updatedUser = userRepository.save(savedUser);

        // Then
        assertEquals("Updated", updatedUser.getFirstName());
        assertEquals("Name", updatedUser.getLastName());
    }

    @Test
    void deleteUser_Success() {
        // Given
        User savedUser = userRepository.save(testUser);

        // When
        userRepository.deleteById(savedUser.getId());

        // Then
        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        assertFalse(foundUser.isPresent());
    }
}
