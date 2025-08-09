package com.smartshopai.user.application.service;

import com.smartshopai.user.application.dto.request.CreateUserRequest;
import com.smartshopai.user.application.dto.request.LoginRequest;
import com.smartshopai.user.application.dto.request.UpdateProfileRequest;
import com.smartshopai.user.application.dto.response.LoginResponse;
import com.smartshopai.user.application.dto.response.UserProfileResponse;
import com.smartshopai.user.application.dto.response.UserResponse;
import com.smartshopai.user.application.mapper.UserMapper;
import com.smartshopai.user.domain.entity.User;
import com.smartshopai.user.domain.entity.UserProfile;
import com.smartshopai.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Application service for user operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserApplicationService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final ChatClient chatClient;

    public UserResponse createUser(CreateUserRequest request) {
        log.info("Creating new user with username: {}", request.getUsername());

        User user = userMapper.toEntity(request);
        UserProfile userProfile = userMapper.toProfileEntity(request);
        
        User savedUser = userService.createUser(user, userProfile);
        
        return userMapper.toResponse(savedUser);
    }

    public void trackProductView(String userId, String productId) {
        userService.trackProductView(userId, productId);
    }

    public void trackSearch(String userId, String searchQuery) {
        userService.trackSearch(userId, searchQuery);
    }

    public Optional<UserResponse> getUserById(String userId) {
        log.debug("Getting user by ID: {}", userId);
        Optional<User> user = userService.getUserById(userId);
        return user.map(userMapper::toResponse);
    }

    public Optional<UserResponse> getUserByEmail(String email) {
        log.debug("Getting user by email: {}", email);
        
        Optional<User> user = userService.getUserByEmail(email);
        return user.map(userMapper::toResponse);
    }

    public List<UserResponse> getAllUsers() {
        log.debug("Getting all users");
        
        List<User> users = userService.getAllUsers();
        return userMapper.toResponseList(users);
    }

    public UserResponse updateUser(String userId, CreateUserRequest request) {
        log.info("Updating user: {}", userId);
        
        User user = userMapper.toEntity(request);
        user.setId(userId);
        User updatedUser = userService.updateUser(user);
        
        return userMapper.toResponse(updatedUser);
    }

    public void deleteUser(String userId) {
        log.info("Deleting user: {}", userId);
        userService.deleteUser(userId);
    }

    public LoginResponse login(LoginRequest request) {
        log.info("User login attempt for: {}", request.getEmailOrUsername());
        String token = userService.authenticateUser(request.getEmailOrUsername(), request.getPassword());
        // Kullanıcıyı bul ve response'a ekle
        Optional<User> userOpt = userService.getUserByIdOrEmail(request.getEmailOrUsername());
        UserResponse userResponse = userOpt.map(userMapper::toResponse).orElse(null);
        return LoginResponse.of(token, null, userResponse);
    }

    public UserProfileResponse updateProfile(String userId, UpdateProfileRequest request) {
        log.info("Updating profile for user: {}", userId);
        
        UserProfile profile = userMapper.toProfileEntity(request);
        UserProfile updatedProfile = userService.updateUserProfile(userId, profile);
        
        return userMapper.toProfileResponse(updatedProfile);
    }

    public UserProfileResponse getUserProfile(String userId) {
        log.debug("Getting user profile for user: {}", userId);
        
        UserProfile profile = userService.getUserProfile(userId);
        return userMapper.toProfileResponse(profile);
    }

    /**
     * Kullanıcıya özel AI insight raporu üretir (prompt, Map, Objects, Optional kullanımı)
     */
    public String getUserInsights(String userId) {
        log.info("Generating AI insights for user: {}", userId);
        Optional<User> userOpt = userService.getUserById(userId);
        User user = userOpt.orElseThrow(() -> new RuntimeException("User not found"));
        String promptString = """
            You are a sharp-witted customer relationship management (CRM) analyst.
            Based on the data provided for a user, generate a brief, actionable insights report.
            The report should be in 2-3 bullet points, focusing on their potential value and marketing opportunities.

            User Data:
            - Username: {username}
            - Email: {email}
            - Member Since: {createdAt}
            - Role: {roles}
            - Profile: {profile}
            - Preferences: {preferences}

            Generate the insights now.
            """;
        PromptTemplate promptTemplate = new PromptTemplate(promptString);
        Prompt prompt = promptTemplate.create(Map.of(
                "username", Objects.toString(user.getUsername(), "N/A"),
                "email", Objects.toString(user.getEmail(), "N/A"),
                "createdAt", Objects.toString(user.getCreatedAt(), "N/A"),
                "roles", Objects.toString(user.getRoles(), "N/A"),
                "profile", Objects.toString(user.getUserProfile(), "No profile details"),
                "preferences", Objects.toString(user.getUserPreferences(), "No preferences set")
        ));
        // AI insight fonksiyonu örneği (iş planına uygun)
        return chatClient.prompt(prompt).call().content();
    }
}
