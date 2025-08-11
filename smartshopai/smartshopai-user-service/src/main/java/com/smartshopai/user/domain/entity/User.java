package com.smartshopai.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String username;
    
    @Indexed(unique = true)
    private String email;
    
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String avatarUrl;
    private boolean enabled;
    private boolean emailVerified;
    private boolean phoneVerified;
    
    private Set<String> roles;
    private Set<String> permissions;
    
    private UserProfile userProfile;
    private UserPreferences userPreferences;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;
    private String lastLoginIp;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserProfile {
        private String bio;
        private String location;
        private String website;
        private String company;
        private String jobTitle;
        private LocalDateTime birthDate;
        private String gender;
        private String language;
        private String timezone;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserPreferences {
        private String currency;
        private String language;
        private boolean emailNotifications;
        private boolean pushNotifications;
        private boolean smsNotifications;
        private String theme;
        private boolean twoFactorEnabled;
        private String defaultCategory;
        private Double budgetLimit;
        private String qualityPreference; // LOW, MEDIUM, HIGH
    }
}
