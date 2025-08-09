package com.smartshopai.user.domain.service;

import com.smartshopai.user.domain.entity.User;
import com.smartshopai.user.domain.entity.UserPreferences;
import com.smartshopai.user.domain.repository.UserPreferencesRepository;
import com.smartshopai.user.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserPreferencesService {

    private final UserRepository userRepository;
    private final UserPreferencesRepository preferencesRepository;

    @Transactional
    public UserPreferences updatePreferences(String userId, UserPreferences preferencesUpdate) {
        log.info("Updating preferences for user ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        UserPreferences currentPreferences = user.getUserPreferences();
        if (currentPreferences == null) {
            // This case should ideally not happen if preferences are created with the user.
            // But as a safeguard:
            currentPreferences = new UserPreferences();
            currentPreferences.setCreatedAt(LocalDateTime.now());
            user.setUserPreferences(currentPreferences);
        }

        // Update fields if they are provided in the request
        if (preferencesUpdate.getMinBudget() != null) {
            currentPreferences.setMinBudget(preferencesUpdate.getMinBudget());
        }
        if (preferencesUpdate.getMaxBudget() != null) {
            currentPreferences.setMaxBudget(preferencesUpdate.getMaxBudget());
        }
        if (preferencesUpdate.getPreferredCategories() != null) {
            currentPreferences.setPreferredCategories(preferencesUpdate.getPreferredCategories());
        }
        if (preferencesUpdate.getExcludedCategories() != null) {
            currentPreferences.setExcludedCategories(preferencesUpdate.getExcludedCategories());
        }
        if (preferencesUpdate.getPreferredBrands() != null) {
            currentPreferences.setPreferredBrands(preferencesUpdate.getPreferredBrands());
        }
        if (preferencesUpdate.getExcludedBrands() != null) {
            currentPreferences.setExcludedBrands(preferencesUpdate.getExcludedBrands());
        }

        currentPreferences.setUpdatedAt(LocalDateTime.now());
        
        return preferencesRepository.save(currentPreferences);
    }

    public UserPreferences getPreferencesByUserId(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        
        return user.getUserPreferences();
    }
}
