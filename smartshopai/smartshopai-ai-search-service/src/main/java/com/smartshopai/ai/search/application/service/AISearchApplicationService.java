package com.smartshopai.ai.search.application.service;

import com.smartshopai.ai.search.application.dto.request.CreateSearchRequest;
import com.smartshopai.ai.search.application.dto.response.SearchRequestResponse;
import com.smartshopai.ai.search.application.dto.response.SearchResultResponse;
import com.smartshopai.ai.search.application.mapper.SearchMapper;
import com.smartshopai.ai.search.domain.entity.SearchRequest;
import com.smartshopai.ai.search.domain.entity.SearchResult;
import com.smartshopai.ai.search.domain.service.AISearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AISearchApplicationService {

    private final AISearchService aiSearchService;
    private final SearchMapper searchMapper;

    public SearchRequestResponse createSearchRequest(CreateSearchRequest request) {
        log.info("Creating search request for userId: {}", request.getUserId());
        
        SearchRequest entity = searchMapper.toEntity(request);
        SearchRequest savedRequest = aiSearchService.createSearchRequest(entity);
        
        return searchMapper.toRequestResponse(savedRequest);
    }

    public SearchRequestResponse getSearchRequest(String id) {
        log.debug("Getting search request by ID: {}", id);
        
        SearchRequest request = aiSearchService.findRequestById(id);
        return searchMapper.toRequestResponse(request);
    }

    public List<SearchRequestResponse> getSearchRequestsByUserId(String userId) {
        log.debug("Getting search requests for userId: {}", userId);
        
        List<SearchRequest> requests = aiSearchService.findRequestsByUserId(userId);
        return searchMapper.toRequestResponseList(requests);
    }

    public List<SearchRequestResponse> getSearchRequestsByStatus(String status) {
        log.debug("Getting search requests by status: {}", status);
        
        List<SearchRequest> requests = aiSearchService.findRequestsByStatus(status);
        return searchMapper.toRequestResponseList(requests);
    }

    public SearchResultResponse getSearchResult(String id) {
        log.debug("Getting search result by ID: {}", id);
        
        SearchResult result = aiSearchService.findResultById(id);
        return searchMapper.toResultResponse(result);
    }

    public Optional<SearchResultResponse> getSearchResultByRequestId(String requestId) {
        log.debug("Getting search result by requestId: {}", requestId);
        
        Optional<SearchResult> result = aiSearchService.findResultByRequestId(requestId);
        return result.map(searchMapper::toResultResponse);
    }

    public List<SearchResultResponse> getSearchResultsByUserId(String userId) {
        log.debug("Getting search results for userId: {}", userId);
        
        List<SearchResult> results = aiSearchService.findResultsByUserId(userId);
        return searchMapper.toResultResponseList(results);
    }

    public List<SearchResultResponse> getActiveSearchResults() {
        log.debug("Getting active search results");
        
        List<SearchResult> results = aiSearchService.findActiveResults();
        return searchMapper.toResultResponseList(results);
    }

    public List<SearchResultResponse> getPersonalizedSearchResults() {
        log.debug("Getting personalized search results");
        
        List<SearchResult> results = aiSearchService.findPersonalizedResults();
        return searchMapper.toResultResponseList(results);
    }

    public SearchRequestResponse updateRequestStatus(String id, String status) {
        log.info("Updating search request status - id: {}, status: {}", id, status);
        
        SearchRequest request = aiSearchService.findRequestById(id);
        SearchRequest updatedRequest = aiSearchService.updateRequestStatus(request, status);
        
        return searchMapper.toRequestResponse(updatedRequest);
    }

    public SearchResultResponse updateResultStatus(String id, String status) {
        log.info("Updating search result status - id: {}, status: {}", id, status);
        
        SearchResult result = aiSearchService.findResultById(id);
        SearchResult updatedResult = aiSearchService.updateResultStatus(result, status);
        
        return searchMapper.toResultResponse(updatedResult);
    }
}
