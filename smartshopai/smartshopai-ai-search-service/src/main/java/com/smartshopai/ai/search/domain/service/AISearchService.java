package com.smartshopai.ai.search.domain.service;

import com.smartshopai.ai.search.domain.entity.SearchRequest;
import com.smartshopai.ai.search.domain.entity.SearchResult;
import com.smartshopai.ai.search.domain.repository.SearchRequestRepository;
import com.smartshopai.ai.search.domain.repository.SearchResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AISearchService {

    private final SearchRequestRepository searchRequestRepository;
    private final SearchResultRepository searchResultRepository;
    private final AISearchEngineService aiSearchEngineService;

    public SearchRequest createSearchRequest(SearchRequest request) {
        log.info("Creating search request for userId: {}", request.getUserId());
        
        // Validate request data
        validateRequestData(request);
        
        // Set default values
        request.setStatus("PENDING");
        request.setAiAnalysisCompleted(false);
        request.prePersist();
        
        SearchRequest savedRequest = searchRequestRepository.save(request);
        log.info("Search request created successfully with ID: {}", savedRequest.getId());
        
        return savedRequest;
    }

    @Cacheable(value = "searchRequests", key = "#id")
    public SearchRequest findRequestById(String id) {
        log.debug("Finding search request by ID: {}", id);
        return searchRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Search request not found"));
    }

    public List<SearchRequest> findRequestsByUserId(String userId) {
        log.debug("Finding search requests for userId: {}", userId);
        return searchRequestRepository.findByUserId(userId);
    }

    public List<SearchRequest> findRequestsByStatus(String status) {
        log.debug("Finding search requests by status: {}", status);
        return searchRequestRepository.findByStatus(status);
    }

    public List<SearchRequest> findPendingRequests() {
        log.debug("Finding pending search requests");
        return searchRequestRepository.findByStatus("PENDING");
    }

    public SearchResult createSearchResult(SearchResult result) {
        log.info("Creating search result for requestId: {}", result.getSearchRequestId());
        
        result.setStatus("ACTIVE");
        result.setAiAnalysisCompleted(true);
        result.prePersist();
        
        SearchResult savedResult = searchResultRepository.save(result);
        log.info("Search result created successfully with ID: {}", savedResult.getId());
        
        return savedResult;
    }

    public SearchResult performSearch(SearchRequest request) {
        log.info("Performing AI search for query: {}", request.getQuery());
        
        try {
            SearchResult result;
            
            switch (request.getSearchType().toUpperCase()) {
                case "SEMANTIC":
                    result = aiSearchEngineService.performSemanticSearch(request);
                    break;
                case "HYBRID":
                    result = aiSearchEngineService.performHybridSearch(request);
                    break;
                case "FILTERED":
                    result = aiSearchEngineService.performFilteredSearch(request);
                    break;
                default:
                    result = aiSearchEngineService.performSemanticSearch(request);
                    break;
            }
            
            return searchResultRepository.save(result);
            
        } catch (Exception e) {
            log.error("Error performing search for query: {}", request.getQuery(), e);
            throw new RuntimeException("Search failed: " + e.getMessage());
        }
    }

    @Cacheable(value = "searchResults", key = "#id")
    public SearchResult findResultById(String id) {
        log.debug("Finding search result by ID: {}", id);
        return searchResultRepository.findById(id)
                .orElseThrow(() -> new com.smartshopai.common.exception.NotFoundException("Search result not found"));
    }

    public Optional<SearchResult> findResultByRequestId(String requestId) {
        log.debug("Finding search result by requestId: {}", requestId);
        return searchResultRepository.findBySearchRequestId(requestId);
    }

    public List<SearchResult> findResultsByUserId(String userId) {
        log.debug("Finding search results for userId: {}", userId);
        return searchResultRepository.findByUserId(userId);
    }

    public List<SearchResult> findActiveResults() {
        log.debug("Finding active search results");
        return searchResultRepository.findByStatus("ACTIVE");
    }

    public List<SearchResult> findPersonalizedResults() {
        log.debug("Finding personalized search results");
        return searchResultRepository.findByPersonalized(true);
    }

    @CacheEvict(value = "searchRequests", key = "#request.id")
    public SearchRequest updateRequestStatus(SearchRequest request, String status) {
        log.info("Updating search request status to: {}", status);
        
        request.setStatus(status);
        request.preUpdate();
        
        if ("COMPLETED".equals(status)) {
            request.setProcessedAt(LocalDateTime.now());
        }
        
        SearchRequest updatedRequest = searchRequestRepository.save(request);
        log.info("Search request status updated successfully");
        
        return updatedRequest;
    }

    @CacheEvict(value = "searchResults", key = "#result.id")
    public SearchResult updateResultStatus(SearchResult result, String status) {
        log.info("Updating search result status to: {}", status);
        
        result.setStatus(status);
        result.preUpdate();
        
        SearchResult updatedResult = searchResultRepository.save(result);
        log.info("Search result status updated successfully");
        
        return updatedResult;
    }

    private void validateRequestData(SearchRequest request) {
        if (request.getUserId() == null || request.getUserId().trim().isEmpty()) {
            throw new com.smartshopai.common.exception.ValidationException("User ID is required");
        }
        
        if (request.getQuery() == null || request.getQuery().trim().isEmpty()) {
            throw new com.smartshopai.common.exception.ValidationException("Search query is required");
        }
        
        if (request.getSearchType() == null || request.getSearchType().trim().isEmpty()) {
            throw new com.smartshopai.common.exception.ValidationException("Search type is required");
        }
        
        if (request.getContext() == null || request.getContext().trim().isEmpty()) {
            throw new com.smartshopai.common.exception.ValidationException("Search context is required");
        }
    }
}
