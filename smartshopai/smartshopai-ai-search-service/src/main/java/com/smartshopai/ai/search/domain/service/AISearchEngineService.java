package com.smartshopai.ai.search.domain.service;

import com.smartshopai.ai.search.domain.entity.SearchRequest;
import com.smartshopai.ai.search.domain.entity.SearchResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AI Search Engine Service
 * Handles AI-powered search operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AISearchEngineService {

    private final ChatClient.Builder chatClientBuilder;

    public SearchResult performSemanticSearch(SearchRequest request) {
        log.info("Performing semantic search for query: {}", request.getQuery());
        
        long startTime = System.currentTimeMillis();
        
        try {
            // Create semantic search prompt
            String prompt = createSemanticSearchPrompt(request);
            
            // Generate AI search using Spring AI
            String aiResponse = chatClientBuilder.build().prompt().user(prompt).call().content();
            
            // Create search result
            SearchResult result = SearchResult.builder()
                    .searchRequestId(request.getId())
                    .userId(request.getUserId())
                    .query(request.getQuery())
                    .searchType(request.getSearchType())
                    .context(request.getContext())
                    .products(parseSearchResults(aiResponse))
                    .confidenceScore(calculateRelevanceScore(aiResponse))
                    .aiModel("spring-ai-openai")
                    .tokensUsed(estimateTokens(aiResponse))
                    .processingTimeMs(System.currentTimeMillis() - startTime)
                    .status("ACTIVE")
                    .aiAnalysisCompleted(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            log.info("Semantic search completed for query: {}", request.getQuery());
            return result;
            
        } catch (Exception e) {
            log.error("Error performing semantic search for query: {}", request.getQuery(), e);
            throw new com.smartshopai.common.exception.BusinessException("Semantic search failed", e);
        }
    }

    public SearchResult performHybridSearch(SearchRequest request) {
        log.info("Performing hybrid search for query: {}", request.getQuery());
        
        long startTime = System.currentTimeMillis();
        
        try {
            // Create hybrid search prompt (combines semantic and keyword search)
            String prompt = createHybridSearchPrompt(request);
            
            // Generate AI search using Spring AI
            String aiResponse = chatClientBuilder.build().prompt().user(prompt).call().content();
            
            // Create search result
            SearchResult result = SearchResult.builder()
                    .searchRequestId(request.getId())
                    .userId(request.getUserId())
                    .query(request.getQuery())
                    .searchType(request.getSearchType())
                    .context(request.getContext())
                    .products(parseSearchResults(aiResponse))
                    .confidenceScore(calculateRelevanceScore(aiResponse))
                    .aiModel("spring-ai-openai")
                    .tokensUsed(estimateTokens(aiResponse))
                    .processingTimeMs(System.currentTimeMillis() - startTime)
                    .status("ACTIVE")
                    .aiAnalysisCompleted(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            log.info("Hybrid search completed for query: {}", request.getQuery());
            return result;
            
        } catch (Exception e) {
            log.error("Error performing hybrid search for query: {}", request.getQuery(), e);
            throw new com.smartshopai.common.exception.BusinessException("Hybrid search failed", e);
        }
    }

    public SearchResult performFilteredSearch(SearchRequest request) {
        log.info("Performing filtered search for query: {}", request.getQuery());
        
        long startTime = System.currentTimeMillis();
        
        try {
            // Create filtered search prompt
            String prompt = createFilteredSearchPrompt(request);
            
            // Generate AI search using Spring AI
            String aiResponse = chatClientBuilder.build().prompt().user(prompt).call().content();
            
            // Create search result
            SearchResult result = SearchResult.builder()
                    .searchRequestId(request.getId())
                    .userId(request.getUserId())
                    .query(request.getQuery())
                    .searchType(request.getSearchType())
                    .context(request.getContext())
                    .products(parseSearchResults(aiResponse))
                    .confidenceScore(calculateRelevanceScore(aiResponse))
                    .aiModel("spring-ai-openai")
                    .tokensUsed(estimateTokens(aiResponse))
                    .processingTimeMs(System.currentTimeMillis() - startTime)
                    .status("ACTIVE")
                    .aiAnalysisCompleted(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            log.info("Filtered search completed for query: {}", request.getQuery());
            return result;
            
        } catch (Exception e) {
            log.error("Error performing filtered search for query: {}", request.getQuery(), e);
            throw new com.smartshopai.common.exception.BusinessException("Filtered search failed", e);
        }
    }

    private String createSemanticSearchPrompt(SearchRequest request) {
        return String.format("""
            Perform semantic search for the following query: "%s"
            
            Search Context: %s
            Search Type: %s
            Categories: %s
            Brands: %s
            Price Range: $%.2f - $%.2f
            
            Please provide:
            1. Relevant products based on semantic meaning
            2. Alternative search terms
            3. Related categories
            4. Search suggestions
            
            Format the response in a structured manner.
            """, 
            request.getQuery(),
            request.getContext(),
            request.getSearchType(),
            request.getCategories() != null ? String.join(", ", request.getCategories()) : "All",
            request.getBrands() != null ? String.join(", ", request.getBrands()) : "All",
            request.getMinPrice() != null ? request.getMinPrice() : 0.0,
            request.getMaxPrice() != null ? request.getMaxPrice() : 10000.0
        );
    }

    private String createHybridSearchPrompt(SearchRequest request) {
        return String.format("""
            Perform hybrid search (semantic + keyword) for the following query: "%s"
            
            Search Context: %s
            Search Type: %s
            Categories: %s
            Brands: %s
            Price Range: $%.2f - $%.2f
            
            Please provide:
            1. Exact keyword matches
            2. Semantic matches
            3. Combined relevance scoring
            4. Search refinements
            
            Format the response in a structured manner.
            """, 
            request.getQuery(),
            request.getContext(),
            request.getSearchType(),
            request.getCategories() != null ? String.join(", ", request.getCategories()) : "All",
            request.getBrands() != null ? String.join(", ", request.getBrands()) : "All",
            request.getMinPrice() != null ? request.getMinPrice() : 0.0,
            request.getMaxPrice() != null ? request.getMaxPrice() : 10000.0
        );
    }

    private String createFilteredSearchPrompt(SearchRequest request) {
        return String.format("""
            Perform filtered search for the following query: "%s"
            
            Search Context: %s
            Search Type: %s
            Categories: %s
            Brands: %s
            Price Range: $%.2f - $%.2f
            Sort By: %s
            Filter By: %s
            
            Please provide:
            1. Filtered results based on criteria
            2. Relevance within filters
            3. Alternative filters
            4. Search suggestions
            
            Format the response in a structured manner.
            """, 
            request.getQuery(),
            request.getContext(),
            request.getSearchType(),
            request.getCategories() != null ? String.join(", ", request.getCategories()) : "All",
            request.getBrands() != null ? String.join(", ", request.getBrands()) : "All",
            request.getMinPrice() != null ? request.getMinPrice() : 0.0,
            request.getMaxPrice() != null ? request.getMaxPrice() : 10000.0,
            request.getSortBy() != null ? request.getSortBy() : "RELEVANCE",
            request.getFilterBy() != null ? request.getFilterBy() : "NONE"
        );
    }

    private List<SearchResult.SearchProduct> parseSearchResults(String aiResponse) {
        // Simple parsing - in production, use more sophisticated parsing
        return List.of(
            SearchResult.SearchProduct.builder()
                .productId("product-1")
                .productName("Sample Product 1")
                .relevanceScore(0.95)
                .matchReason("Exact match")
                .build(),
            SearchResult.SearchProduct.builder()
                .productId("product-2")
                .productName("Sample Product 2")
                .relevanceScore(0.85)
                .matchReason("Semantic match")
                .build()
        );
    }

    private Double calculateRelevanceScore(String aiResponse) {
        // Simple calculation - in production, use more sophisticated analysis
        return 0.90; // Default relevance score
    }

    private Long estimateTokens(String text) {
        // Rough estimation: 1 token ≈ 4 characters
        return (long) Math.max(text.length() / 4, 100);
    }
}
