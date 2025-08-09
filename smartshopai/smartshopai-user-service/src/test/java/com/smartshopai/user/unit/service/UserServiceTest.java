package com.smartshopai.user.unit.service;

import com.smartshopai.common.exception.BusinessException;
import com.smartshopai.common.exception.NotFoundException;
import com.smartshopai.user.application.dto.response.LoginResponse;
import com.smartshopai.user.domain.entity.User;
import com.smartshopai.user.domain.entity.UserBehaviorMetrics;
import com.smartshopai.user.domain.repository.UserRepository;
import com.smartshopai.user.domain.service.UserBehaviorService;
import com.smartshopai.user.domain.service.UserService;
import com.smartshopai.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserService
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserBehaviorService userBehaviorService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
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
                .behaviorMetrics(UserBehaviorMetrics.builder()
                        .userId("test-user-id")
                        .lastUpdated(LocalDateTime.now())
                        .build())
                .build();
    }

    @Test
    void createUser_Success() {
        // Given
        when(userRepository.existsByEmail(testUser.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        User result = userService.createUser(testUser);

        // Then
        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_UserAlreadyExists_ThrowsException() {
        // Given
        when(userRepository.existsByEmail(testUser.getEmail())).thenReturn(true);

        // When & Then
        assertThrows(BusinessException.class, () -> userService.createUser(testUser));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void findById_Success() {
        // Given
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));

        // When
        User result = userService.findById(testUser.getId());

        // Then
        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
    }

    @Test
    void findById_UserNotFound_ThrowsException() {
        // Given
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> userService.findById(testUser.getId()));
    }

    @Test
    void findByEmail_Success() {
        // Given
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));

        // When
        User result = userService.findByEmail(testUser.getEmail());

        // Then
        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
    }

    @Test
    void findByEmail_UserNotFound_ThrowsException() {
        // Given
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> userService.findByEmail(testUser.getEmail()));
    }

    @Test
    void loginUser_Success() {
        // Given
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtTokenProvider.generateTokenFromUsername(anyString(), anyMap())).thenReturn("jwt-token");
        when(jwtTokenProvider.generateRefreshToken(anyString())).thenReturn("refresh-token");

        // When
        LoginResponse result = userService.loginUser(testUser.getEmail(), "password");

        // Then
        assertNotNull(result);
        assertEquals("jwt-token", result.getToken());
        assertEquals("refresh-token", result.getRefreshToken());
    }

    @Test
    void loginUser_InvalidCredentials_ThrowsException() {
        // Given
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // When & Then
        assertThrows(BusinessException.class, () -> userService.loginUser(testUser.getEmail(), "wrong-password"));
    }

    @Test
    void loginUser_UserDisabled_ThrowsException() {
        // Given
        testUser.setEnabled(false);
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));

        // When & Then
        assertThrows(BusinessException.class, () -> userService.loginUser(testUser.getEmail(), "password"));
    }

    @Test
    void updateUser_Success() {
        // Given
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        User result = userService.updateUser(testUser);

        // Then
        assertNotNull(result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void setUserEnabled_Success() {
        // Given
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        userService.setUserEnabled(testUser.getId(), false);

        // Then
        verify(userRepository).save(any(User.class));
    }

    @Test
    void verifyEmail_Success() {
        // Given
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        userService.verifyEmail(testUser.getId());

        // Then
        verify(userRepository).save(any(User.class));
    }

    @Test
    void verifyPhone_Success() {
        // Given
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        userService.verifyPhone(testUser.getId());

        // Then
        verify(userRepository).save(any(User.class));
    }
}
