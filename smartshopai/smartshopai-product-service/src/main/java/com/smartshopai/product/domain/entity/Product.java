package com.smartshopai.product.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {
    
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String productId;
    
    @TextIndexed
    private String name;
    
    @TextIndexed
    private String description;
    
    private String brand;
    private String category;
    private String subcategory;
    
    private BigDecimal price;
    private String currency;
    private BigDecimal originalPrice;
    private BigDecimal discountPrice;
    private Integer discountPercentage;
    
    private boolean inStock;
    private Integer stockQuantity;
    private String stockStatus; // IN_STOCK, LOW_STOCK, OUT_OF_STOCK
    
    private List<String> images;
    private String mainImage;
    private List<String> tags;
    
    private Map<String, Object> specifications;
    private Map<String, Object> features;
    private Map<String, Object> technicalDetails;
    
    private String url;
    private String source; // AMAZON, EBAY, etc.
    private String affiliateLink;
    private String warranty;
    
    private Double rating;
    private Integer reviewCount;
    private List<Review> reviews;
    
    private ProductAnalytics analytics;
    private ProductComparison comparison;
    private ProductAnalysis analysis;
    
    // Additional fields for product management
    private List<PriceHistory> priceHistories;
    private Long viewCount;
    private LocalDateTime lastViewedAt;
    private boolean featured;
    private boolean trending;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastPriceUpdate;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Review {
        private String id;
        private String userId;
        private String username;
        private Integer rating;
        private String comment;
        private LocalDateTime createdAt;
        private boolean verified;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductAnalytics {
        private Integer viewCount;
        private Integer clickCount;
        private Integer conversionCount;
        private Double conversionRate;
        private LocalDateTime lastViewed;
        private List<String> viewedByUsers;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductComparison {
        private String comparisonId;
        private List<String> comparedWithProducts;
        private Map<String, Object> comparisonResults;
        private String bestChoice;
        private String bestValue;
        private LocalDateTime lastCompared;
    }
}
