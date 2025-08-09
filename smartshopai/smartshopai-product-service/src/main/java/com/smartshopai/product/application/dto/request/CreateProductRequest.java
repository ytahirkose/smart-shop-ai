package com.smartshopai.product.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Request DTO for creating a new product
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {

    @NotBlank(message = "Product ID is required")
    private String productId;

    @NotBlank(message = "Product name is required")
    private String name;

    private String description;
    private String brand;
    private String category;
    private String subcategory;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
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

    // Availability and stock
    private boolean inStock;
    @Positive(message = "Stock quantity must be positive")
    private Integer stockQuantity;
    private String availabilityStatus;

    // Shipping and warranty
    @Positive(message = "Shipping cost must be positive")
    private BigDecimal shippingCost;
    private String warranty;
    private String returnPolicy;

    // SEO and metadata
    private String metaTitle;
    private String metaDescription;
    private List<String> keywords;

    // Status
    private String status;
    private boolean featured;
    private boolean trending;
}
