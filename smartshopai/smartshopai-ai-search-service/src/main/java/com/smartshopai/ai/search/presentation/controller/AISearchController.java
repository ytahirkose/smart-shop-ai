package com.smartshopai.ai.search.presentation.controller;

import com.smartshopai.common.dto.BaseResponse;
import com.smartshopai.ai.search.application.dto.request.CreateSearchRequest;
import com.smartshopai.ai.search.application.dto.response.SearchRequestResponse;
import com.smartshopai.ai.search.application.dto.response.SearchResultResponse;
import com.smartshopai.ai.search.application.service.AISearchApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/ai/search")
@RequiredArgsConstructor
@Tag(name = "AI Search", description = "AI-powered search endpoints")
public class AISearchController {

    private final AISearchApplicationService aiSearchApplicationService;

    @PostMapping("/requests")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Create search request", description = "Create a new AI-powered search request")
    public ResponseEntity<BaseResponse<SearchRequestResponse>> createSearchRequest(
            @Valid @RequestBody CreateSearchRequest request) {
        
        log.info("Received search request - userId: {}, query: {}", request.getUserId(), request.getQuery());
        
        SearchRequestResponse response = aiSearchApplicationService.createSearchRequest(request);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(response, "Search request created successfully"));
    }

    @GetMapping("/requests/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get search request", description = "Get search request by ID")
    public ResponseEntity<BaseResponse<SearchRequestResponse>> getSearchRequest(
            @PathVariable String id) {
        
        log.info("Getting search request - id: {}", id);
        
        SearchRequestResponse response = aiSearchApplicationService.getSearchRequest(id);
        
        return ResponseEntity.ok(BaseResponse.success(response, "Search request retrieved successfully"));
    }

    @GetMapping("/requests/user/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get user search requests", description = "Get all search requests for a user")
    public ResponseEntity<BaseResponse<List<SearchRequestResponse>>> getUserSearchRequests(
            @PathVariable String userId) {
        
        log.info("Getting search requests for user - userId: {}", userId);
        
        List<SearchRequestResponse> responses = aiSearchApplicationService.getSearchRequestsByUserId(userId);
        
        return ResponseEntity.ok(BaseResponse.success(responses, "User search requests retrieved successfully"));
    }

    @GetMapping("/requests/status/{status}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get requests by status", description = "Get search requests by status")
    public ResponseEntity<BaseResponse<List<SearchRequestResponse>>> getSearchRequestsByStatus(
            @PathVariable String status) {
        
        log.info("Getting search requests by status - status: {}", status);
        
        List<SearchRequestResponse> responses = aiSearchApplicationService.getSearchRequestsByStatus(status);
        
        return ResponseEntity.ok(BaseResponse.success(responses, "Search requests retrieved successfully"));
    }

    @GetMapping("/results/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get search result", description = "Get search result by ID")
    public ResponseEntity<BaseResponse<SearchResultResponse>> getSearchResult(
            @PathVariable String id) {
        
        log.info("Getting search result - id: {}", id);
        
        SearchResultResponse response = aiSearchApplicationService.getSearchResult(id);
        
        return ResponseEntity.ok(BaseResponse.success(response, "Search result retrieved successfully"));
    }

    @GetMapping("/results/request/{requestId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get result by request ID", description = "Get search result by request ID")
    public ResponseEntity<BaseResponse<SearchResultResponse>> getSearchResultByRequestId(
            @PathVariable String requestId) {
        
        log.info("Getting search result by request ID - requestId: {}", requestId);
        
        Optional<SearchResultResponse> response = aiSearchApplicationService.getSearchResultByRequestId(requestId);
        
        if (response.isPresent()) {
            return ResponseEntity.ok(BaseResponse.success(response.get(), "Search result retrieved successfully"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/results/user/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get user search results", description = "Get all search results for a user")
    public ResponseEntity<BaseResponse<List<SearchResultResponse>>> getUserSearchResults(
            @PathVariable String userId) {
        
        log.info("Getting search results for user - userId: {}", userId);
        
        List<SearchResultResponse> responses = aiSearchApplicationService.getSearchResultsByUserId(userId);
        
        return ResponseEntity.ok(BaseResponse.success(responses, "User search results retrieved successfully"));
    }

    @GetMapping("/results/active")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get active results", description = "Get all active search results")
    public ResponseEntity<BaseResponse<List<SearchResultResponse>>> getActiveSearchResults() {
        
        log.info("Getting active search results");
        
        List<SearchResultResponse> responses = aiSearchApplicationService.getActiveSearchResults();
        
        return ResponseEntity.ok(BaseResponse.success(responses, "Active search results retrieved successfully"));
    }

    @GetMapping("/results/personalized")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get personalized results", description = "Get all personalized search results")
    public ResponseEntity<BaseResponse<List<SearchResultResponse>>> getPersonalizedSearchResults() {
        
        log.info("Getting personalized search results");
        
        List<SearchResultResponse> responses = aiSearchApplicationService.getPersonalizedSearchResults();
        
        return ResponseEntity.ok(BaseResponse.success(responses, "Personalized search results retrieved successfully"));
    }

    @PatchMapping("/requests/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update request status", description = "Update search request status")
    public ResponseEntity<BaseResponse<SearchRequestResponse>> updateRequestStatus(
            @PathVariable String id,
            @RequestParam String status) {
        
        log.info("Updating search request status - id: {}, status: {}", id, status);
        
        SearchRequestResponse response = aiSearchApplicationService.updateRequestStatus(id, status);
        
        return ResponseEntity.ok(BaseResponse.success(response, "Search request status updated successfully"));
    }

    @PatchMapping("/results/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update result status", description = "Update search result status")
    public ResponseEntity<BaseResponse<SearchResultResponse>> updateResultStatus(
            @PathVariable String id,
            @RequestParam String status) {
        
        log.info("Updating search result status - id: {}, status: {}", id, status);
        
        SearchResultResponse response = aiSearchApplicationService.updateResultStatus(id, status);
        
        return ResponseEntity.ok(BaseResponse.success(response, "Search result status updated successfully"));
    }
    
    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    @Operation(summary = "AI Search service health check", description = "Checks if AI Search service is healthy")
    public ResponseEntity<BaseResponse<String>> healthCheck() {
        log.debug("Health check requested");
        
        return ResponseEntity.ok(BaseResponse.success("AI Search Service is healthy"));
    }
}
