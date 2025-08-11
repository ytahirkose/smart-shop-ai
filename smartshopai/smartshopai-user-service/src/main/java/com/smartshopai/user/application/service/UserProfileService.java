package com.smartshopai.user.application.service;

import com.smartshopai.user.domain.entity.UserProfile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final com.smartshopai.user.domain.service.UserProfileService userProfileDomainService;

    public UserProfile saveUserProfile(UserProfile profile) {
        return userProfileDomainService.saveUserProfile(profile);
    }

    public Optional<UserProfile> getUserProfile(String userId) {
        return userProfileDomainService.getUserProfile(userId);
    }

    public List<UserProfile> getPublicProfiles() {
        return userProfileDomainService.getCompletedProfiles();
    }
}
