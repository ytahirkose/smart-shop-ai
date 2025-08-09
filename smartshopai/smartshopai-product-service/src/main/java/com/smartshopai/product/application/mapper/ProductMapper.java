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
    @Mapping(target = "analysis", ignore = true)
    @Mapping(target = "alternativeProductIds", ignore = true)
    @Mapping(target = "similarProductIds", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "averageRating", ignore = true)
    @Mapping(target = "totalReviews", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lastAnalyzedAt", ignore = true)
    @Mapping(target = "aiAnalysisCompleted", ignore = true)
    Product toEntity(CreateProductRequest request);

    @Mapping(target = "analysis", source = "analysis")
    @Mapping(target = "specifications", source = "specifications.specifications")
    ProductResponse toResponse(Product product);

    List<ProductResponse> toResponseList(List<Product> products);

    ProductAnalysisResponse toAnalysisResponse(ProductAnalysis analysis);

    ProductComparisonResponse toComparisonResponse(ProductComparison comparison);

    ReviewResponse toReviewResponse(Review review);

    List<ReviewResponse> toReviewResponseList(List<Review> reviews);
}
