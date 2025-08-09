package com.smartshopai.search.application.service;

import com.smartshopai.search.application.dto.request.SearchRequest;
import com.smartshopai.search.application.dto.response.SearchResponse;
import com.smartshopai.search.application.mapper.SearchMapper;
import com.smartshopai.search.domain.entity.SearchIndex;
import com.smartshopai.search.domain.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Application service for search operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SearchApplicationService {

    private final SearchService searchService;
    private final SearchMapper searchMapper;

    public SearchResponse search(SearchRequest request) {
        log.info("Searching for query: {}", request.getQuery());
        
        List<SearchIndex> results = searchService.search(
            request.getQuery(),
            request.getCategory(),
            request.getBrand(),
            request.getMinPrice(),
            request.getMaxPrice(),
            request.getSortBy(),
            request.getSortOrder(),
            request.getPage(),
            request.getSize(),
            request.getFilters()
        );
        
        List<SearchResponse.SearchResult> searchResults = searchMapper.toSearchResultList(results);
        
        return SearchResponse.builder()
                .query(request.getQuery())
                .results(searchResults)
                .totalResults(results.size())
                .page(request.getPage())
                .size(request.getSize())
                .build();
    }

    public SearchResponse searchByCategory(String category, SearchRequest request) {
        log.debug("Searching in category: {}", category);
        
        List<SearchIndex> results = searchService.searchByCategory(
            category,
            request.getQuery(),
            request.getBrand(),
            request.getMinPrice(),
            request.getMaxPrice(),
            request.getSortBy(),
            request.getSortOrder(),
            request.getPage(),
            request.getSize()
        );
        
        List<SearchResponse.SearchResult> searchResults = searchMapper.toSearchResultList(results);
        
        return SearchResponse.builder()
                .query(request.getQuery())
                .results(searchResults)
                .totalResults(results.size())
                .page(request.getPage())
                .size(request.getSize())
                .build();
    }

    public SearchResponse searchByBrand(String brand, SearchRequest request) {
        log.debug("Searching in brand: {}", brand);
        
        List<SearchIndex> results = searchService.searchByBrand(
            brand,
            request.getQuery(),
            request.getCategory(),
            request.getMinPrice(),
            request.getMaxPrice(),
            request.getSortBy(),
            request.getSortOrder(),
            request.getPage(),
            request.getSize()
        );
        
        List<SearchResponse.SearchResult> searchResults = searchMapper.toSearchResultList(results);
        
        return SearchResponse.builder()
                .query(request.getQuery())
                .results(searchResults)
                .totalResults(results.size())
                .page(request.getPage())
                .size(request.getSize())
                .build();
    }

    public void indexProduct(String productId, String name, String description, String brand, 
                           String category, Double price, Double rating, String imageUrl, 
                           Map<String, Object> attributes) {
        log.info("Indexing product: {}", productId);
        
        searchService.indexProduct(productId, name, description, brand, category, price, rating, imageUrl, attributes);
    }

    public void removeFromIndex(String productId) {
        log.info("Removing product from index: {}", productId);
        searchService.removeFromIndex(productId);
    }

    public void updateIndex(String productId, String name, String description, String brand, 
                          String category, Double price, Double rating, String imageUrl, 
                          Map<String, Object> attributes) {
        log.info("Updating product in index: {}", productId);
        
        searchService.updateIndex(productId, name, description, brand, category, price, rating, imageUrl, attributes);
    }

    public void reindexAll() {
        log.info("Reindexing all products");
        searchService.reindexAll();
    }

    public Map<String, Object> getSearchStatistics() {
        log.debug("Getting search statistics");
        return searchService.getSearchStatistics();
    }
}
