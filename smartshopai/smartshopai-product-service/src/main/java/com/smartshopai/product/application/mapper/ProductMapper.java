package com.smartshopai.product.application.mapper;

import com.smartshopai.product.application.dto.request.CreateProductRequest;
import com.smartshopai.product.application.dto.response.ProductAnalysisResponse;
import com.smartshopai.product.application.dto.response.ProductComparisonResponse;
import com.smartshopai.product.application.dto.response.ProductResponse;
import com.smartshopai.product.application.dto.response.ReviewResponse;
import com.smartshopai.product.domain.entity.Product;
import com.smartshopai.product.domain.entity.ProductAnalysis;
import com.smartshopai.product.domain.entity.ProductComparison;
import com.smartshopai.product.domain.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for Product-related objects
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "analytics", ignore = true)
    @Mapping(target = "comparison", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "reviewCount", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lastPriceUpdate", ignore = true)
    @Mapping(target = "features", expression = "java(convertFeaturesToMap(request.getFeatures()))")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "images", source = "imageUrls")
    @Mapping(target = "mainImage", source = "imageUrl")
    @Mapping(target = "url", source = "imageUrl")
    @Mapping(target = "source", constant = "MANUAL")
    @Mapping(target = "affiliateLink", ignore = true)
    @Mapping(target = "discountPrice", ignore = true)
    @Mapping(target = "discountPercentage", ignore = true)
    @Mapping(target = "stockStatus", expression = "java(request.isInStock() ? \"IN_STOCK\" : \"OUT_OF_STOCK\")")
    @Mapping(target = "technicalDetails", ignore = true)
    Product toEntity(CreateProductRequest request);

    @Mapping(target = "analysis", source = "analytics")
    @Mapping(target = "imageUrl", source = "mainImage")
    @Mapping(target = "imageUrls", source = "images")
    @Mapping(target = "averageRating", source = "rating")
    @Mapping(target = "totalReviews", source = "reviewCount")
    @Mapping(target = "availabilityStatus", source = "stockStatus")
    @Mapping(target = "status", expression = "java(product.isInStock() ? \"ACTIVE\" : \"INACTIVE\")")
    @Mapping(target = "featured", constant = "false")
    @Mapping(target = "trending", constant = "false")
    @Mapping(target = "aiAnalysisCompleted", constant = "false")
    @Mapping(target = "features", expression = "java(convertMapToList(product.getFeatures()))")
    @Mapping(target = "videoUrl", ignore = true)
    @Mapping(target = "alternativeProductIds", ignore = true)
    @Mapping(target = "similarProductIds", ignore = true)
    @Mapping(target = "shippingCost", ignore = true)
    @Mapping(target = "warranty", ignore = true)
    @Mapping(target = "returnPolicy", ignore = true)
    @Mapping(target = "metaTitle", ignore = true)
    @Mapping(target = "metaDescription", ignore = true)
    @Mapping(target = "keywords", ignore = true)
    @Mapping(target = "lastAnalyzedAt", ignore = true)
    ProductResponse toResponse(Product product);

    List<ProductResponse> toResponseList(List<Product> products);

    ProductAnalysisResponse toAnalysisResponse(ProductAnalysis analysis);

    @Mapping(target = "isBetterAlternative", constant = "false")
    ProductComparisonResponse toComparisonResponse(ProductComparison comparison);

    ReviewResponse toReviewResponse(Review review);

    List<ReviewResponse> toReviewResponseList(List<Review> reviews);

    // Helper methods for type conversion
    default java.util.Map<String, Object> convertFeaturesToMap(List<String> features) {
        if (features == null) return null;
        java.util.Map<String, Object> map = new java.util.HashMap<>();
        for (int i = 0; i < features.size(); i++) {
            map.put("feature_" + i, features.get(i));
        }
        return map;
    }

    default List<String> convertMapToList(java.util.Map<String, Object> map) {
        if (map == null) return null;
        return map.values().stream()
                .filter(value -> value instanceof String)
                .map(value -> (String) value)
                .toList();
    }
}
