package com.smartshopai.user.domain.service;

import com.smartshopai.user.domain.entity.UserProfile;
import com.smartshopai.user.domain.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Domain service for UserProfile operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserProfileService {
    
    private final UserProfileRepository userProfileRepository;
    
    /**
     * Create or update user profile
     */
    public UserProfile saveUserProfile(UserProfile userProfile) {
        log.info("Saving user profile for userId: {}", userProfile.getUserId());
        
        if (userProfile.getId() == null) {
            userProfile.setCreatedAt(LocalDateTime.now());
        }
        userProfile.setUpdatedAt(LocalDateTime.now());
        userProfile.setLastProfileUpdate(LocalDateTime.now());
        
        UserProfile savedProfile = userProfileRepository.save(userProfile);
        log.info("User profile saved successfully with ID: {}", savedProfile.getId());
        
        return savedProfile;
    }
    
    /**
     * Get user profile by user ID
     */
    @Transactional(readOnly = true)
    public Optional<UserProfile> getUserProfile(String userId) {
        log.debug("Getting user profile for userId: {}", userId);
        return userProfileRepository.findByUserId(userId);
    }
    
    /**
     * Check if profile exists by user ID
     */
    @Transactional(readOnly = true)
    public boolean existsByUserId(String userId) {
        return userProfileRepository.existsByUserId(userId);
    }
    
    /**
     * Get profiles by preferred categories
     */
    @Transactional(readOnly = true)
    public List<UserProfile> getProfilesByPreferredCategories(List<String> categories) {
        log.debug("Getting profiles by preferred categories: {}", categories);
        return userProfileRepository.findByPreferredCategoriesIn(categories);
    }
    
    /**
     * Get profiles by max budget
     */
    @Transactional(readOnly = true)
    public List<UserProfile> getProfilesByMaxBudget(Double maxBudget) {
        log.debug("Getting profiles by max budget: {}", maxBudget);
        return userProfileRepository.findByMaxBudgetLessThanEqual(maxBudget);
    }
    
    /**
     * Get profiles by preferred categories and max budget
     */
    @Transactional(readOnly = true)
    public List<UserProfile> getProfilesByPreferences(List<String> categories, Double maxBudget) {
        log.debug("Getting profiles by preferences - categories: {}, maxBudget: {}", categories, maxBudget);
        return userProfileRepository.findByPreferredCategoriesInAndMaxBudgetLessThanEqual(categories, maxBudget);
    }
    
    /**
     * Get completed profiles
     */
    @Transactional(readOnly = true)
    public List<UserProfile> getCompletedProfiles() {
        log.debug("Getting completed profiles");
        return userProfileRepository.findByProfileCompletedTrue();
    }
    
    /**
     * Get profiles with notification preferences
     */
    @Transactional(readOnly = true)
    public List<UserProfile> getProfilesWithNotifications() {
        log.debug("Getting profiles with notifications enabled");
        return userProfileRepository.findByReceiveNotificationsTrue();
    }
    
    /**
     * Get profiles with email alerts
     */
    @Transactional(readOnly = true)
    public List<UserProfile> getProfilesWithEmailAlerts() {
        log.debug("Getting profiles with email alerts enabled");
        return userProfileRepository.findByReceiveEmailAlertsTrue();
    }
    
    /**
     * Get profiles with SMS alerts
     */
    @Transactional(readOnly = true)
    public List<UserProfile> getProfilesWithSmsAlerts() {
        log.debug("Getting profiles with SMS alerts enabled");
        return userProfileRepository.findByReceiveSmsAlertsTrue();
    }
    
    /**
     * Get profiles with push notifications
     */
    @Transactional(readOnly = true)
    public List<UserProfile> getProfilesWithPushNotifications() {
        log.debug("Getting profiles with push notifications enabled");
        return userProfileRepository.findByReceivePushNotificationsTrue();
    }
    
    /**
     * Update profile completion percentage
     */
    public void updateProfileCompletion(String userId, Integer completionPercentage) {
        log.debug("Updating profile completion for userId: {} to {}%", userId, completionPercentage);
        
        Optional<UserProfile> profileOpt = userProfileRepository.findByUserId(userId);
        if (profileOpt.isPresent()) {
            UserProfile profile = profileOpt.get();
            profile.setProfileCompletionPercentage(completionPercentage);
            profile.setProfileCompleted(completionPercentage >= 100);
            profile.setUpdatedAt(LocalDateTime.now());
            userProfileRepository.save(profile);
        }
    }
    
    /**
     * Update AI personality
     */
    public void updateAiPersonality(String userId, String aiPersonality) {
        log.debug("Updating AI personality for userId: {}", userId);
        
        Optional<UserProfile> profileOpt = userProfileRepository.findByUserId(userId);
        if (profileOpt.isPresent()) {
            UserProfile profile = profileOpt.get();
            profile.setAiPersonality(aiPersonality);
            profile.setUpdatedAt(LocalDateTime.now());
            userProfileRepository.save(profile);
        }
    }
    
    /**
     * Update AI insights
     */
    public void updateAiInsights(String userId, String aiInsights) {
        log.debug("Updating AI insights for userId: {}", userId);
        
        Optional<UserProfile> profileOpt = userProfileRepository.findByUserId(userId);
        if (profileOpt.isPresent()) {
            UserProfile profile = profileOpt.get();
            profile.setAiInsights(aiInsights);
            profile.setUpdatedAt(LocalDateTime.now());
            userProfileRepository.save(profile);
        }
    }
    
    /**
     * Update AI recommendations
     */
    public void updateAiRecommendations(String userId, List<String> aiRecommendations) {
        log.debug("Updating AI recommendations for userId: {}", userId);
        
        Optional<UserProfile> profileOpt = userProfileRepository.findByUserId(userId);
        if (profileOpt.isPresent()) {
            UserProfile profile = profileOpt.get();
            profile.setAiRecommendations(aiRecommendations);
            profile.setUpdatedAt(LocalDateTime.now());
            userProfileRepository.save(profile);
        }
    }
}
