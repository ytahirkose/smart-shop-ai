package com.smartshopai.user.application.service;

import com.smartshopai.user.domain.entity.User;
import com.smartshopai.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public User createUser(User user) {
        log.info("Creating new user with username: {}", user.getUsername());
        
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists: " + user.getUsername());
        }
        
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists: " + user.getEmail());
        }
        
        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Set default values
        user.setEnabled(true);
        user.setEmailVerified(false);
        user.setPhoneVerified(false);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        
        User savedUser = userRepository.save(user);
        log.info("User created successfully with ID: {}", savedUser.getId());
        
        return savedUser;
    }
    
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }
    
    public List<User> getUsersByPreferredCategory(String category) {
        return userRepository.findByPreferredCategory(category);
    }
    
    public List<User> getUsersByBudgetLimit(Double budget) {
        return userRepository.findByBudgetLimitGreaterThan(budget);
    }
    
    public List<User> getUsersByQualityPreference(String qualityPreference) {
        return userRepository.findByQualityPreference(qualityPreference);
    }
    
    public User updateUser(String id, User userDetails) {
        log.info("Updating user with ID: {}", id);
        
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        
        // Update fields
        existingUser.setFirstName(userDetails.getFirstName());
        existingUser.setLastName(userDetails.getLastName());
        existingUser.setPhoneNumber(userDetails.getPhoneNumber());
        existingUser.setAvatarUrl(userDetails.getAvatarUrl());
        existingUser.setUserProfile(userDetails.getUserProfile());
        existingUser.setUserPreferences(userDetails.getUserPreferences());
        existingUser.setUpdatedAt(LocalDateTime.now());
        
        User updatedUser = userRepository.save(existingUser);
        log.info("User updated successfully with ID: {}", updatedUser.getId());
        
        return updatedUser;
    }
    
    public void deleteUser(String id) {
        log.info("Deleting user with ID: {}", id);
        
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        
        userRepository.deleteById(id);
        log.info("User deleted successfully with ID: {}", id);
    }
    
    public void updateLastLogin(String id, String ipAddress) {
        log.info("Updating last login for user with ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        
        user.setLastLoginAt(LocalDateTime.now());
        user.setLastLoginIp(ipAddress);
        user.setUpdatedAt(LocalDateTime.now());
        
        userRepository.save(user);
        log.info("Last login updated for user with ID: {}", id);
    }
    
    public boolean verifyEmail(String id) {
        log.info("Verifying email for user with ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        
        user.setEmailVerified(true);
        user.setUpdatedAt(LocalDateTime.now());
        
        userRepository.save(user);
        log.info("Email verified for user with ID: {}", id);
        
        return true;
    }
    
    public boolean verifyPhone(String id) {
        log.info("Verifying phone for user with ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        
        user.setPhoneVerified(true);
        user.setUpdatedAt(LocalDateTime.now());
        
        userRepository.save(user);
        log.info("Phone verified for user with ID: {}", id);
        
        return true;
    }
    
    public void changePassword(String id, String newPassword) {
        log.info("Changing password for user with ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(LocalDateTime.now());
        
        userRepository.save(user);
        log.info("Password changed successfully for user with ID: {}", id);
    }
}
