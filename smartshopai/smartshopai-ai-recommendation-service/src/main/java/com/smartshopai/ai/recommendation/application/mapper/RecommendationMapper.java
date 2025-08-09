package com.smartshopai.ai.recommendation.application.mapper;

import com.smartshopai.ai.recommendation.application.dto.request.CreateRecommendationRequest;
import com.smartshopai.ai.recommendation.application.dto.response.RecommendationRequestResponse;
import com.smartshopai.ai.recommendation.application.dto.response.RecommendationResponse;
import com.smartshopai.ai.recommendation.application.dto.response.RecommendationResultResponse;
import com.smartshopai.ai.recommendation.domain.entity.Recommendation;
import com.smartshopai.ai.recommendation.domain.entity.RecommendationRequest;
import com.smartshopai.ai.recommendation.domain.entity.RecommendationResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper for Recommendation entities and DTOs
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecommendationMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "requestType", target = "requestType")
    @Mapping(source = "context", target = "context")
    @Mapping(source = "recommendedProducts", target = "recommendedProducts")
    @Mapping(source = "recommendationSummary", target = "recommendationSummary")
    @Mapping(source = "reasoning", target = "reasoning")
    @Mapping(source = "confidenceScore", target = "confidenceScore")
    @Mapping(source = "aiModelUsed", target = "aiModelUsed")
    @Mapping(source = "tokensUsed", target = "tokensUsed")
    @Mapping(source = "processingTimeMs", target = "processingTimeMs")
    @Mapping(source = "modelParameters", target = "modelParameters")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    RecommendationResponse toResponse(Recommendation recommendation);

    List<RecommendationResponse> toResponseList(List<Recommendation> recommendations);

    RecommendationRequestResponse toRequestResponse(RecommendationRequest request);
    List<RecommendationRequestResponse> toRequestResponseList(List<RecommendationRequest> requests);

    RecommendationResultResponse toResultResponse(RecommendationResult result);
    List<RecommendationResultResponse> toResultResponseList(List<RecommendationResult> results);

    // CreateRecommendationRequest -> RecommendationRequest (for request persistence flows)
    RecommendationRequest toEntity(CreateRecommendationRequest request);
}
