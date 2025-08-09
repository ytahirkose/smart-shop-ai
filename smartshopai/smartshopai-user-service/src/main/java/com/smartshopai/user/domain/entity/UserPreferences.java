package com.smartshopai.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_preferences")
public class UserPreferences {
    
    @Id
    private String id;
    
    // Budget Preferences
    private Double minBudget;
    private Double maxBudget;
    
    // Category Preferences
    private List<String> preferredCategories;
    private List<String> excludedCategories;
    
    // Brand Preferences
    private List<String> preferredBrands;
    private List<String> excludedBrands;

    // Notification Preferences
    private boolean emailNotifications;
    private boolean smsNotifications;
    private boolean pushNotifications;
    
    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // --- ADDED FIELDS FOR REPOSITORY COMPATIBILITY ---
    /**
     * Kullanıcı ID'si (referans için)
     */
    private String userId;

    /**
     * AI tabanlı öneriler aktif mi?
     */
    @Builder.Default
    private boolean aiRecommendationsEnabled = true;

    /**
     * AI davranış analizi aktif mi?
     */
    @Builder.Default
    private boolean aiBehaviorAnalysisEnabled = false;

    /**
     * AI kişiselleştirme aktif mi?
     */
    @Builder.Default
    private boolean aiPersonalizationEnabled = false;

    /**
     * Veri paylaşımı aktif mi?
     */
    @Builder.Default
    private boolean dataSharingEnabled = false;

    /**
     * Kişiselleştirilmiş reklamlar aktif mi?
     */
    @Builder.Default
    private boolean personalizedAdsEnabled = false;

    /**
     * Kullanıcı tercihleri tamamlandı mı?
     */
    @Builder.Default
    private boolean preferencesCompleted = false;
}
