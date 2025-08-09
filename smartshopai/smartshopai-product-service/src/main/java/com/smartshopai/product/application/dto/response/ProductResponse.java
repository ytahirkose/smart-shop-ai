package com.smartshopai.product.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Response DTO for product data
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private String id;
    private String productId;
    private String name;
    private String description;
    private String brand;
    private String category;
    private String subcategory;

    private BigDecimal price;
    private BigDecimal originalPrice;
    private String currency;

    private String imageUrl;
    private List<String> imageUrls;
    private String videoUrl;

    // Technical specifications
    private Map<String, Object> specifications;
    private List<String> features;
    private List<String> tags;

    // AI Analysis Results
    private ProductAnalysisResponse analysis;

    // Alternative products
    private List<String> alternativeProductIds;
    private List<String> similarProductIds;

    // Ratings and reviews
    private Double averageRating;
    private Integer totalReviews;
    private List<ReviewResponse> reviews;

    // Availability and stock
    private boolean inStock;
    private Integer stockQuantity;
    private String availabilityStatus;

    // Shipping and warranty
    private BigDecimal shippingCost;
    private String warranty;
    private String returnPolicy;

    // SEO and metadata
    private String metaTitle;
    private String metaDescription;
    private List<String> keywords;

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastAnalyzedAt;

    // Status
    private String status;
    private boolean featured;
    private boolean trending;
    private boolean aiAnalysisCompleted;
}
