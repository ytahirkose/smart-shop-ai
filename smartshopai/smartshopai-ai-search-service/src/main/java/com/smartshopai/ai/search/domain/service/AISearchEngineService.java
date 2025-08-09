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
        String prompt = createSemanticSearchPrompt(request);
        return runSearch(request, prompt, "Semantic search");
    }

    public SearchResult performHybridSearch(SearchRequest request) {
        String prompt = createHybridSearchPrompt(request);
        return runSearch(request, prompt, "Hybrid search");
    }

    public SearchResult performFilteredSearch(SearchRequest request) {
        String prompt = createFilteredSearchPrompt(request);
        return runSearch(request, prompt, "Filtered search");
    }

    private SearchResult runSearch(SearchRequest request, String prompt, String searchTypeLog) {
        log.info("Performing {} for query: {}", searchTypeLog, request.getQuery());
        long startTime = System.currentTimeMillis();
        try {
            String aiResponse = chatClientBuilder.build().prompt().user(prompt).call().content();
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
            log.info("{} completed for query: {}", searchTypeLog, request.getQuery());
            return result;
        } catch (Exception e) {
            log.error("Error performing {} for query: {}", searchTypeLog, request.getQuery(), e);
            throw new com.smartshopai.common.exception.BusinessException(searchTypeLog + " failed", e);
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
        if (aiResponse == null || aiResponse.isBlank()) {
            return List.of();
        }
        java.util.List<SearchResult.SearchProduct> products = new java.util.ArrayList<>();
        String[] lines = aiResponse.split("\r?\n");
        int counter = 1;
        for (String line : lines) {
            String trimmed = line.strip();
            if (trimmed.isEmpty()) {
                continue;
            }
            // Identify list bullets like "1.", "-", "*"
            if (trimmed.matches("^(?:\\d+\\.|[-*])\\s+.*")) {
                // Extract after bullet
                String content = trimmed.replaceFirst("^(?:\\d+\\.|[-*])\\s+", "").trim();
                // Attempt to split product name and reason by "-" delimiter
                String[] parts = content.split(" - ", 2);
                String namePart = parts[0].trim();
                String reasonPart = parts.length > 1 ? parts[1].trim() : "Matched your query";

                SearchResult.SearchProduct product = SearchResult.SearchProduct.builder()
                        .productId("auto-" + counter)
                        .productName(namePart)
                        .matchReason(reasonPart)
                        .relevanceScore(Math.round((1.0 - (counter - 1) * 0.05) * 100.0) / 100.0)
                        .build();
                products.add(product);
                counter++;
            }
            if (counter > 20) {
                break; // Limit results to 20
            }
        }
        if (products.isEmpty()) {
            // Fallback: single dummy product with the response snippet
            products.add(SearchResult.SearchProduct.builder()
                    .productId("auto-1")
                    .productName(aiResponse.substring(0, Math.min(50, aiResponse.length())).replaceAll("\n", " ") + "...")
                    .matchReason("AI response snippet")
                    .relevanceScore(0.5)
                    .build());
        }
        return products;
    }

    private Double calculateRelevanceScore(String aiResponse) {
        if (aiResponse == null || aiResponse.isBlank()) {
            return 0.0;
        }
        // If AI provided explicit relevance/confidence, parse it
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("(?i)(relevance|confidence)[\n\r:\\s]*([0-9]+(?:\\.[0-9]+)?)").matcher(aiResponse);
        if (matcher.find()) {
            try {
                double parsed = Double.parseDouble(matcher.group(2));
                if (parsed <= 1.0) {
                    return parsed;
                }
                return parsed / 100.0;
            } catch (NumberFormatException ignored) {}
        }
        // Heuristic: the shorter the response, the higher our confidence
        double score = 1.0 / (1 + aiResponse.length() / 1000.0);
        return Math.round(score * 100.0) / 100.0;
    }

    private Long estimateTokens(String text) {
        // Rough estimation: 1 token ≈ 4 characters
        return (long) Math.max(text.length() / 4, 100);
    }
}
