package com.smartshopai.user.application.service;

import com.smartshopai.user.domain.entity.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileAppService {

    private final com.smartshopai.user.domain.service.UserProfileService domainService;

    public UserProfile saveUserProfile(UserProfile profile) {
        return domainService.saveUserProfile(profile);
    }

    public Optional<UserProfile> getUserProfile(String userId) {
        return domainService.getUserProfile(userId);
    }

    public List<UserProfile> getPublicProfiles() {
        return domainService.getCompletedProfiles();
    }
}
