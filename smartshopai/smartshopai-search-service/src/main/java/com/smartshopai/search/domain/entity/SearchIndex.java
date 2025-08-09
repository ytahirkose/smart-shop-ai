package com.smartshopai.search.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Search Index entity for SmartShopAI application
 * Represents indexed product data for search
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "products")
public class SearchIndex {

    @Id
    private String id;
    
    @Field(type = FieldType.Keyword)
    private String productId;
    
    @Field(type = FieldType.Text, analyzer = "standard")
    private String name;
    
    @Field(type = FieldType.Text, analyzer = "standard")
    private String description;
    
    @Field(type = FieldType.Keyword)
    private String brand;
    
    @Field(type = FieldType.Keyword)
    private String category;
    
    @Field(type = FieldType.Keyword)
    private String subcategory;
    
    @Field(type = FieldType.Double)
    private BigDecimal price;
    
    @Field(type = FieldType.Double)
    private BigDecimal originalPrice;
    
    @Field(type = FieldType.Keyword)
    private String currency;
    
    @Field(type = FieldType.Keyword)
    private String imageUrl;
    
    @Field(type = FieldType.Keyword)
    private List<String> imageUrls;
    
    @Field(type = FieldType.Keyword)
    private String videoUrl;
    
    // Technical specifications
    @Field(type = FieldType.Object)
    private Map<String, Object> specifications;
    
    @Field(type = FieldType.Keyword)
    private List<String> features;
    
    @Field(type = FieldType.Keyword)
    private List<String> tags;
    
    // AI Analysis Results
    @Field(type = FieldType.Double)
    private Double qualityScore;
    
    @Field(type = FieldType.Double)
    private Double valueForMoneyScore;
    
    @Field(type = FieldType.Text, analyzer = "standard")
    private String aiInsights;
    
    // Alternative products
    @Field(type = FieldType.Keyword)
    private List<String> alternativeProductIds;
    
    @Field(type = FieldType.Keyword)
    private List<String> similarProductIds;
    
    // Ratings and reviews
    @Field(type = FieldType.Double)
    private Double averageRating;
    
    @Field(type = FieldType.Integer)
    private Integer totalReviews;
    
    // Availability and stock
    @Field(type = FieldType.Boolean)
    private boolean inStock;
    
    @Field(type = FieldType.Integer)
    private Integer stockQuantity;
    
    @Field(type = FieldType.Keyword)
    private String availabilityStatus;
    
    // Shipping and warranty
    @Field(type = FieldType.Double)
    private BigDecimal shippingCost;
    
    @Field(type = FieldType.Keyword)
    private String warranty;
    
    @Field(type = FieldType.Keyword)
    private String returnPolicy;
    
    // SEO and metadata
    @Field(type = FieldType.Text, analyzer = "standard")
    private String metaTitle;
    
    @Field(type = FieldType.Text, analyzer = "standard")
    private String metaDescription;
    
    @Field(type = FieldType.Keyword)
    private List<String> keywords;
    
    // Timestamps
    @Field(type = FieldType.Date)
    private LocalDateTime createdAt;
    
    @Field(type = FieldType.Date)
    private LocalDateTime updatedAt;
    
    @Field(type = FieldType.Date)
    private LocalDateTime lastAnalyzedAt;
    
    // Status
    @Field(type = FieldType.Keyword)
    private String status;
    
    @Field(type = FieldType.Boolean)
    private boolean featured;
    
    @Field(type = FieldType.Boolean)
    private boolean trending;
    
    @Field(type = FieldType.Boolean)
    private boolean aiAnalysisCompleted;
}
