package com.smartshopai.aianalysis.domain.service;

import com.smartshopai.aianalysis.domain.entity.ProductAnalysis;
import com.smartshopai.aianalysis.domain.repository.ProductAnalysisRepository;
import com.smartshopai.aianalysis.infrastructure.client.ProductServiceClient;
import com.smartshopai.aianalysis.infrastructure.dto.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductAnalysisService {

    private final ProductAnalysisRepository analysisRepository;
    private final ProductServiceClient productServiceClient;

    public ProductAnalysis analyzeProduct(String productId, Set<String> roles) {
        log.info("Starting AI analysis for product ID: {} with roles: {}", productId, roles);

        Product product = productServiceClient.getProductById(productId);

        String analysisContent;
        if (roles.contains("ROLE_PREMIUM")) {
            log.info("Generating PREMIUM analysis for user.");
            analysisContent = generatePremiumAnalysis(product);
        } else {
            log.info("Generating STANDARD analysis for user.");
            analysisContent = generateStandardAnalysis(product);
        }

        // Mock embeddings for now - will be replaced with real AI when Spring AI is ready
        List<Double> embeddings = generateMockEmbeddings(product.getName() + " " + product.getDescription() + " " + analysisContent);

        ProductAnalysis analysis = ProductAnalysis.builder()
                .productId(productId)
                .analysisContent(analysisContent)
                .embeddings(embeddings)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return analysisRepository.save(analysis);
    }

    private String generatePremiumAnalysis(Product product) {
        return String.format("""
            **PREMIUM ANALYSIS REPORT**
            
            **Product**: %s
            **Price**: %s %s
            
            **In-Depth Key Features**:
            - Technical specifications: %s
            - Advanced features analysis
            
            **Deep Pros and Cons**:
            - Pros: High-quality build, advanced features, good value
            - Cons: Premium pricing, may be overkill for basic users
            
            **Competitor Comparison**:
            - Compared to similar products in this price range
            - This product offers better value and features
            
            **Long-term Viability**:
            - Built to last with quality materials
            - Future-proof design and technology
            
            **Final Verdict for Premium User**:
            - RECOMMENDED: Excellent choice for users who value quality and advanced features
            """,
            product.getName(),
            product.getCurrentPrice(),
            product.getCurrency(),
            product.getSpecifications()
        );
    }

    private String generateStandardAnalysis(Product product) {
        return String.format("""
            **STANDARD ANALYSIS REPORT**
            
            **Product**: %s
            **Price**: %s %s
            
            **Key Features Summary**:
            - %s
            
            **Pros and Cons**:
            - Pros: Good features, reasonable price, reliable
            - Cons: Basic design, limited advanced features
            
            **Target Audience**:
            - Students, professionals, general users
            
            **Value Proposition**:
            - Good value for money
            - Suitable for everyday use
            """,
            product.getName(),
            product.getCurrentPrice(),
            product.getCurrency(),
            product.getSpecifications()
        );
    }

    private List<Double> generateMockEmbeddings(String text) {
        // Mock embeddings - will be replaced with real AI when Spring AI is ready
        List<Double> embeddings = new java.util.ArrayList<>();
        for (int i = 0; i < 768; i++) {
            embeddings.add(Math.random());
        }
        return embeddings;
    }

    public List<ProductAnalysis> findSimilarProducts(List<Double> userEmbedding, int topK) {
        log.info("Finding similar products for user embedding with topK: {}", topK);
        
        // Mock similarity search - will be replaced with real AI when Spring AI is ready
        List<ProductAnalysis> allAnalyses = analysisRepository.findAll();
        return allAnalyses.stream()
                .limit(topK)
                .collect(Collectors.toList());
    }
}
