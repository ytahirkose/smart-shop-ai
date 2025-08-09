package com.smartshopai.ai.search.application.service;

import com.smartshopai.ai.search.application.dto.request.CreateSearchRequest;
import com.smartshopai.ai.search.application.dto.request.SearchProductsRequest;
import com.smartshopai.ai.search.application.dto.request.SemanticSearchRequest;
import com.smartshopai.ai.search.application.dto.response.SearchRequestResponse;
import com.smartshopai.ai.search.application.dto.response.SearchResultResponse;
import com.smartshopai.ai.search.application.mapper.SearchMapper;
import com.smartshopai.ai.search.domain.entity.SearchResult;
import com.smartshopai.ai.search.domain.service.AISearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service for AI search operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SearchApplicationService {

    private final AISearchService aiSearchService;
    private final SearchMapper searchMapper;

    public SearchRequestResponse createSearchRequest(CreateSearchRequest request) {
        log.info("Creating search request for user: {}", request.getUserId());
        
        var entity = searchMapper.toEntity(request);
        var saved = aiSearchService.createSearchRequest(entity);
        return searchMapper.toRequestResponse(saved);
    }

    public List<SearchResultResponse> searchProducts(SearchProductsRequest request) {
        log.info("Searching products with AI for user: {}", request.getUserId());
        
        // Map to domain request and delegate to unified performSearch
        var domainRequest = searchMapper.toEntity(CreateSearchRequest.builder()
                .userId(request.getUserId())
                .query(request.getQuery())
                .searchType(request.getSearchType())
                .context(request.getContext())
                .filters(request.getFilters())
                .build());
        var result = aiSearchService.performSearch(domainRequest);
        return searchMapper.toResultResponseList(List.of(result));
    }

    public List<SearchResultResponse> semanticSearch(SemanticSearchRequest request) {
        log.info("Performing semantic search for user: {}", request.getUserId());
        
        var domainRequest = searchMapper.toEntity(CreateSearchRequest.builder()
                .userId(request.getUserId())
                .query(request.getQuery())
                .searchType("SEMANTIC")
                .context(request.getContext())
                .filters(request.getFilters())
                .build());
        var result = aiSearchService.performSearch(domainRequest);
        return searchMapper.toResultResponseList(List.of(result));
    }

    public List<SearchResultResponse> hybridSearch(String query, String userId) {
        log.debug("Performing hybrid search for user: {}", userId);
        
        var domainRequest = searchMapper.toEntity(CreateSearchRequest.builder()
                .userId(userId)
                .query(query)
                .searchType("HYBRID")
                .context("DISCOVERY")
                .build());
        var result = aiSearchService.performSearch(domainRequest);
        return searchMapper.toResultResponseList(List.of(result));
    }

    public List<SearchResultResponse> naturalLanguageSearch(String query, String userId) {
        log.debug("Performing natural language search for user: {}", userId);
        
        var domainRequest = searchMapper.toEntity(CreateSearchRequest.builder()
                .userId(userId)
                .query(query)
                .searchType("SEMANTIC")
                .context("NATURAL_LANGUAGE")
                .build());
        var result = aiSearchService.performSearch(domainRequest);
        return searchMapper.toResultResponseList(List.of(result));
    }

    public List<SearchResultResponse> getSearchHistory(String userId) {
        log.debug("Getting search history for user: {}", userId);
        
        // Simplified: fetch results by user
        var results = aiSearchService.findResultsByUserId(userId);
        return searchMapper.toResultResponseList(results);
    }

    public void trackSearchInteraction(String userId, String query, String resultId, String interactionType) {
        log.debug("Tracking search interaction - userId: {}, query: {}, resultId: {}, type: {}", 
                 userId, query, resultId, interactionType);
        
        // Placeholder: no-op tracking
    }

    public void updateSearchIndex() {
        log.info("Updating search index");
        // Placeholder: no-op index update
    }

    public String getSearchSuggestions(String query, String userId) {
        log.debug("Getting search suggestions for query: {}", query);
        
        // Placeholder suggestion
        return "No suggestions available";
    }
}
