package com.smartshopai.ai.search.application.mapper;

import com.smartshopai.ai.search.application.dto.request.CreateSearchRequest;
import com.smartshopai.ai.search.application.dto.response.SearchRequestResponse;
import com.smartshopai.ai.search.application.dto.response.SearchResultResponse;
import com.smartshopai.ai.search.domain.entity.SearchRequest;
import com.smartshopai.ai.search.domain.entity.SearchResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SearchMapper {

    SearchRequestResponse toRequestResponse(SearchRequest entity);

    List<SearchRequestResponse> toRequestResponseList(List<SearchRequest> entities);

    SearchResultResponse toResultResponse(SearchResult entity);

    List<SearchResultResponse> toResultResponseList(List<SearchResult> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "errorMessage", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "processedAt", ignore = true)
    @Mapping(target = "processingTimeMs", ignore = true)
    @Mapping(target = "resultProductIds", ignore = true)
    @Mapping(target = "searchMetadata", ignore = true)
    @Mapping(target = "relevanceScore", ignore = true)
    @Mapping(target = "aiAnalysisCompleted", ignore = true)
    SearchRequest toEntity(CreateSearchRequest request);
}
