package com.smartshopai.product.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Product Brand entity for SmartShopAI application
 * Represents product brands and their information
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product_brands")
public class ProductBrand {
    
    @Id
    private String id;
    
    private String name;
    private String description;
    private String slug;
    private String logoUrl;
    private String websiteUrl;
    private String country;
    private String foundedYear;
    
    // Brand statistics
    private Integer productCount;
    private Double averagePrice;
    private Double averageRating;
    private Integer totalReviews;
    
    // AI-generated insights
    private String aiInsights;
    private String brandPersonality;
    private String qualityScore;
    private String valueForMoney;
    private String customerSatisfaction;
    
    // Brand positioning
    private String targetAudience;
    private String priceRange;
    private String qualityTier;
    private String marketPosition;
    
    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastAnalysisAt;
    
    // Status
    private boolean active;
    private boolean verified;
    private boolean featured;
    
    @Builder.Default
    private boolean aiAnalysisCompleted = false;
    
    // Pre-save method to set timestamps
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        updatedAt = LocalDateTime.now();
    }
    
    // Pre-update method to set updated timestamp
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
