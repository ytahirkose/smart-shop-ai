package com.smartshopai.aisearch.domain.service;

import com.smartshopai.aisearch.infrastructure.client.AnalysisServiceClient;
import com.smartshopai.aisearch.infrastructure.client.ProductServiceClient;
import com.smartshopai.aisearch.infrastructure.dto.Product;
import com.smartshopai.aisearch.infrastructure.dto.ProductAnalysis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final EmbeddingModel embeddingModel;
    private final AnalysisServiceClient analysisServiceClient;
    private final ProductServiceClient productServiceClient;

    public List<Product> semanticSearch(String query) {
        log.info("Performing semantic search for query: '{}'", query);

        // 1. Convert the search query into an embedding vector
        float[] rawEmb = embeddingModel.embed(query);
        List<Double> queryEmbedding = new java.util.ArrayList<>(rawEmb.length);
        for (float f : rawEmb) {
            queryEmbedding.add((double) f);
        }
        log.debug("Generated embedding for query.");

        // 2. Find similar product analyses using the analysis service
        // We are reusing the same logic as the recommendation service
        List<ProductAnalysis> similarAnalyses = analysisServiceClient.findSimilarProducts(queryEmbedding, 5);
        if (similarAnalyses.isEmpty()) {
            log.warn("No semantically similar products found for query: '{}'", query);
            return List.of();
        }

        // 3. Extract the product IDs from the analysis results
        List<String> productIds = similarAnalyses.stream()
                .map(ProductAnalysis::getProductId)
                .collect(Collectors.toList());
        log.info("Found {} similar product IDs: {}", productIds.size(), productIds);

        // 4. Fetch the full product details for the found IDs from the product service
        // This requires a new endpoint in product-service that accepts a list of IDs
        return productServiceClient.getProductsByIds(productIds);
    }
}
