package com.smartshopai.ai.search.presentation.controller;

import com.smartshopai.ai.search.application.dto.request.CreateSearchRequest;
import com.smartshopai.ai.search.application.dto.request.SearchProductsRequest;
import com.smartshopai.ai.search.application.dto.request.SemanticSearchRequest;
import com.smartshopai.ai.search.application.dto.response.SearchRequestResponse;
import com.smartshopai.ai.search.application.dto.response.SearchResultResponse;
import com.smartshopai.ai.search.application.service.SearchApplicationService;
import com.smartshopai.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST controller for AI search operations
 */
@Slf4j
@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchApplicationService searchApplicationService;

    @PostMapping("/request")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<SearchRequestResponse>> createSearchRequest(
            @Valid @RequestBody CreateSearchRequest request) {
        log.info("Creating search request for user: {}", request.getUserId());
        
        SearchRequestResponse response = searchApplicationService.createSearchRequest(request);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PostMapping("/products")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<List<SearchResultResponse>>> searchProducts(
            @Valid @RequestBody SearchProductsRequest request) {
        log.info("Searching products with AI for user: {}", request.getUserId());
        
        List<SearchResultResponse> response = searchApplicationService.searchProducts(request);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PostMapping("/semantic")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<List<SearchResultResponse>>> semanticSearch(
            @Valid @RequestBody SemanticSearchRequest request) {
        log.info("Performing semantic search for user: {}", request.getUserId());
        
        List<SearchResultResponse> response = searchApplicationService.semanticSearch(request);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @GetMapping("/hybrid")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<List<SearchResultResponse>>> hybridSearch(
            @RequestParam String query, @RequestParam String userId) {
        log.debug("Performing hybrid search for user: {}", userId);
        
        List<SearchResultResponse> response = searchApplicationService.hybridSearch(query, userId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @GetMapping("/natural")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<List<SearchResultResponse>>> naturalLanguageSearch(
            @RequestParam String query, @RequestParam String userId) {
        log.debug("Performing natural language search for user: {}", userId);
        
        List<SearchResultResponse> response = searchApplicationService.naturalLanguageSearch(query, userId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @GetMapping("/history/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<List<SearchResultResponse>>> getSearchHistory(
            @PathVariable String userId) {
        log.debug("Getting search history for user: {}", userId);
        
        List<SearchResultResponse> response = searchApplicationService.getSearchHistory(userId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PostMapping("/track")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<String>> trackSearchInteraction(
            @RequestParam String userId, @RequestParam String query, 
            @RequestParam String resultId, @RequestParam String interactionType) {
        log.debug("Tracking search interaction - userId: {}, query: {}, resultId: {}, type: {}", 
                 userId, query, resultId, interactionType);
        
        searchApplicationService.trackSearchInteraction(userId, query, resultId, interactionType);
        return ResponseEntity.ok(BaseResponse.success("Interaction tracked successfully"));
    }

    @PostMapping("/index/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<String>> updateSearchIndex() {
        log.info("Updating search index");
        
        searchApplicationService.updateSearchIndex();
        return ResponseEntity.ok(BaseResponse.success("Search index updated successfully"));
    }

    @GetMapping("/suggestions")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<String>> getSearchSuggestions(
            @RequestParam String query, @RequestParam String userId) {
        log.debug("Getting search suggestions for query: {}", query);
        
        String response = searchApplicationService.getSearchSuggestions(query, userId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }
}
