package com.smartshopai.user.application.service;

import com.smartshopai.user.application.dto.response.UserResponse;
import com.smartshopai.user.application.mapper.UserMapper;
import com.smartshopai.user.domain.entity.User;
import com.smartshopai.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Query service for User operations (CQRS pattern)
 * Handles read-only operations for user data
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService {
    
    private final UserService userService;
    private final UserMapper userMapper;
    
    /**
     * Get user by ID
     */
    @Cacheable(value = "userQueries", key = "#id")
    public UserResponse getUserById(String id) {
        log.debug("Getting user by ID: {}", id);
        User user = userService.findById(id);
        return userMapper.toResponse(user);
    }
    
    /**
     * Get user by email
     */
    @Cacheable(value = "userQueries", key = "#email")
    public UserResponse getUserByEmail(String email) {
        log.debug("Getting user by email: {}", email);
        User user = userService.findByEmail(email);
        return userMapper.toResponse(user);
    }
    
    /**
     * Get user by username
     */
    @Cacheable(value = "userQueries", key = "#username")
    public UserResponse getUserByUsername(String username) {
        log.debug("Getting user by username: {}", username);
        User user = userService.findByUsername(username);
        return userMapper.toResponse(user);
    }
    
    /**
     * Get users by preferences for AI recommendations
     */
    @Cacheable(value = "userQueries", key = "'preferences:' + #categories.hashCode() + ':' + #maxBudget")
    public List<UserResponse> getUsersByPreferences(List<String> categories, Double maxBudget) {
        log.debug("Getting users by preferences - categories: {}, maxBudget: {}", categories, maxBudget);
        List<User> users = userService.findUsersByPreferences(categories, maxBudget);
        return userMapper.toResponseList(users);
    }
    
    /**
     * Get users by role
     */
    @Cacheable(value = "userQueries", key = "'role:' + #role")
    public List<UserResponse> getUsersByRole(String role) {
        log.debug("Getting users by role: {}", role);
        List<User> users = userService.findUsersByRole(role);
        return userMapper.toResponseList(users);
    }
    
    /**
     * Get enabled users
     */
    @Cacheable(value = "userQueries", key = "'enabled'")
    public List<UserResponse> getEnabledUsers() {
        log.debug("Getting enabled users");
        List<User> users = userService.findEnabledUsers();
        return userMapper.toResponseList(users);
    }
    
    /**
     * Get users by enabled status
     */
    @Cacheable(value = "userQueries", key = "'enabled:' + #enabled")
    public List<UserResponse> getUsersByEnabledStatus(boolean enabled) {
        log.debug("Getting users by enabled status: {}", enabled);
        List<User> users = userService.findUsersByEnabledStatus(enabled);
        return userMapper.toResponseList(users);
    }
    
    /**
     * Get users by email verification status
     */
    @Cacheable(value = "userQueries", key = "'emailVerified:' + #emailVerified")
    public List<UserResponse> getUsersByEmailVerificationStatus(boolean emailVerified) {
        log.debug("Getting users by email verification status: {}", emailVerified);
        List<User> users = userService.findUsersByEmailVerificationStatus(emailVerified);
        return userMapper.toResponseList(users);
    }
    
    /**
     * Get users by phone verification status
     */
    @Cacheable(value = "userQueries", key = "'phoneVerified:' + #phoneVerified")
    public List<UserResponse> getUsersByPhoneVerificationStatus(boolean phoneVerified) {
        log.debug("Getting users by phone verification status: {}", phoneVerified);
        List<User> users = userService.findUsersByPhoneVerificationStatus(phoneVerified);
        return userMapper.toResponseList(users);
    }
    
    /**
     * Count users by enabled status
     */
    @Cacheable(value = "userQueries", key = "'countEnabled:' + #enabled")
    public long countUsersByEnabledStatus(boolean enabled) {
        log.debug("Counting users by enabled status: {}", enabled);
        return userService.countUsersByEnabledStatus(enabled);
    }
    
    /**
     * Count users by email verification status
     */
    @Cacheable(value = "userQueries", key = "'countEmailVerified:' + #emailVerified")
    public long countUsersByEmailVerificationStatus(boolean emailVerified) {
        log.debug("Counting users by email verification status: {}", emailVerified);
        return userService.countUsersByEmailVerificationStatus(emailVerified);
    }
}
