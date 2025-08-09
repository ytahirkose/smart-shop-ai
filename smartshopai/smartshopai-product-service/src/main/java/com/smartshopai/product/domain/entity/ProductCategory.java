package com.smartshopai.product.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Product Category entity for SmartShopAI application
 * Represents product categories and their hierarchy
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product_categories")
public class ProductCategory {
    
    @Id
    private String id;
    
    private String name;
    private String description;
    private String slug;
    private String parentCategoryId;
    private Integer level;
    private String imageUrl;
    private String iconUrl;
    
    // SEO and metadata
    private String metaTitle;
    private String metaDescription;
    private String keywords;
    
    // Category statistics
    private Integer productCount;
    private Double averagePrice;
    private String popularBrands;
    
    // AI-generated insights
    private String aiInsights;
    private String trendingProducts;
    private String categoryTrends;
    
    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastAnalysisAt;
    
    // Status
    private boolean active;
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
