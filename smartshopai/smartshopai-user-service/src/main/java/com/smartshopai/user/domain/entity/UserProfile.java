package com.smartshopai.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_profiles")
public class UserProfile {

    @Id
    private String id;

    // Personal Information
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String profilePictureUrl;

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // --- ADDED FIELDS FOR REPOSITORY COMPATIBILITY ---
    /**
     * Kullanıcı ID'si (referans için)
     */
    private String userId;

    /**
     * Maksimum alışveriş bütçesi (profilde gösterim ve öneri için)
     */
    private Double maxBudget;

    /**
     * Tercih edilen kategoriler (profilde gösterim ve öneri için)
     */
    private java.util.List<String> preferredCategories;

    /**
     * Profil tamamlandı mı?
     */
    @Builder.Default
    private boolean profileCompleted = false;

    /**
     * Bildirim almak istiyor mu?
     */
    @Builder.Default
    private boolean receiveNotifications = false;

    /**
     * E-posta ile bildirim almak istiyor mu?
     */
    @Builder.Default
    private boolean receiveEmailAlerts = false;

    /**
     * SMS ile bildirim almak istiyor mu?
     */
    @Builder.Default
    private boolean receiveSmsAlerts = false;

    /**
     * Push notification almak istiyor mu?
     */
    @Builder.Default
    private boolean receivePushNotifications = false;
}
