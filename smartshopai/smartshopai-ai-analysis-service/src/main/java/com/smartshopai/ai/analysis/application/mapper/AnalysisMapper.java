package com.smartshopai.ai.analysis.application.mapper;

import com.smartshopai.ai.analysis.application.dto.request.CreateAnalysisRequest;
import com.smartshopai.ai.analysis.application.dto.response.AnalysisResponse;
import com.smartshopai.ai.analysis.domain.entity.ProductAnalysis;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper for AI Analysis Service
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnalysisMapper {
    
    AnalysisMapper INSTANCE = Mappers.getMapper(AnalysisMapper.class);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "confidenceScore", ignore = true)
    @Mapping(target = "keyFeatures", ignore = true)
    @Mapping(target = "pros", ignore = true)
    @Mapping(target = "cons", ignore = true)
    @Mapping(target = "simplifiedSpecs", ignore = true)
    @Mapping(target = "technicalTerms", ignore = true)
    @Mapping(target = "aiModelVersion", ignore = true)
    @Mapping(target = "analysisPrompt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "wasHelpful", ignore = true)
    @Mapping(target = "userFeedback", ignore = true)
    @Mapping(target = "isPublic", ignore = true)
    ProductAnalysis toEntity(CreateAnalysisRequest request);
    
    @Mapping(target = "analysisId", source = "id")
    AnalysisResponse toResponse(ProductAnalysis entity);
    
    List<AnalysisResponse> toResponseList(List<ProductAnalysis> entities);
}
