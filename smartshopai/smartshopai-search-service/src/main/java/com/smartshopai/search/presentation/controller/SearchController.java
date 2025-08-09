package com.smartshopai.search.presentation.controller;

import com.smartshopai.search.application.dto.request.SearchRequest;
import com.smartshopai.search.application.dto.response.SearchResponse;
import com.smartshopai.search.application.service.SearchApplicationService;
import com.smartshopai.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;

/**
 * REST controller for search operations
 */
@Slf4j
@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchApplicationService searchApplicationService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<SearchResponse>> search(
            @Valid @RequestBody SearchRequest request) {
        log.info("Searching for query: {}", request.getQuery());
        
        SearchResponse response = searchApplicationService.search(request);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @GetMapping("/category/{category}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<SearchResponse>> searchByCategory(
            @PathVariable String category, @Valid SearchRequest request) {
        log.debug("Searching in category: {}", category);
        
        SearchResponse response = searchApplicationService.searchByCategory(category, request);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @GetMapping("/brand/{brand}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse<SearchResponse>> searchByBrand(
            @PathVariable String brand, @Valid SearchRequest request) {
        log.debug("Searching in brand: {}", brand);
        
        SearchResponse response = searchApplicationService.searchByBrand(brand, request);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PostMapping("/index")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<String>> indexProduct(
            @RequestParam String productId, @RequestParam String name, 
            @RequestParam String description, @RequestParam String brand,
            @RequestParam String category, @RequestParam Double price, 
            @RequestParam Double rating, @RequestParam String imageUrl,
            @RequestBody(required = false) Map<String, Object> attributes) {
        log.info("Indexing product: {}", productId);
        
        searchApplicationService.indexProduct(productId, name, description, brand, category, price, rating, imageUrl, attributes);
        return ResponseEntity.ok(BaseResponse.success("Product indexed successfully"));
    }

    @DeleteMapping("/index/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<String>> removeFromIndex(@PathVariable String productId) {
        log.info("Removing product from index: {}", productId);
        
        searchApplicationService.removeFromIndex(productId);
        return ResponseEntity.ok(BaseResponse.success("Product removed from index successfully"));
    }

    @PutMapping("/index/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<String>> updateIndex(
            @PathVariable String productId, @RequestParam String name, 
            @RequestParam String description, @RequestParam String brand,
            @RequestParam String category, @RequestParam Double price, 
            @RequestParam Double rating, @RequestParam String imageUrl,
            @RequestBody(required = false) Map<String, Object> attributes) {
        log.info("Updating product in index: {}", productId);
        
        searchApplicationService.updateIndex(productId, name, description, brand, category, price, rating, imageUrl, attributes);
        return ResponseEntity.ok(BaseResponse.success("Product index updated successfully"));
    }

    @PostMapping("/reindex")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<String>> reindexAll() {
        log.info("Reindexing all products");
        
        searchApplicationService.reindexAll();
        return ResponseEntity.ok(BaseResponse.success("All products reindexed successfully"));
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<Map<String, Object>>> getSearchStatistics() {
        log.debug("Getting search statistics");
        
        Map<String, Object> response = searchApplicationService.getSearchStatistics();
        return ResponseEntity.ok(BaseResponse.success(response));
    }
}
