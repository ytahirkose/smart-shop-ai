package com.smartshopai.product.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    @TextIndexed
    private String name;

    @TextIndexed
    private String description;
    
    @Indexed
    private String ean; // European Article Number or other unique identifier

    @DBRef
    private ProductCategory category;

    @DBRef
    private ProductBrand brand;

    private BigDecimal currentPrice;
    private String currency;
    private String productUrl;

    private String mainImageUrl;
    private List<String> additionalImageUrls;
    
    private Double averageRating;
    private Integer reviewCount;

    @DBRef(lazy = true)
    private List<Review> reviews;

    @DBRef(lazy = true)
    private ProductSpecifications specifications;

    @DBRef(lazy = true)
    private List<PriceHistory> priceHistories;
    
    @DBRef(lazy = true)
    private ProductAnalysis analysis;

    private boolean featured;
    private long viewCount;
    private LocalDateTime lastViewedAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private BigDecimal price; // Alias for currentPrice for backward compatibility
    private BigDecimal originalPrice;
    private java.util.List<String> features;
    private Boolean trending;
    private String warranty;

    public BigDecimal getPrice() {
        return price != null ? price : currentPrice;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }
}
