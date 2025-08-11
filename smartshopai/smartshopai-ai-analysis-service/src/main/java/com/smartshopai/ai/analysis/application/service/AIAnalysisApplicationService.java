package com.smartshopai.ai.analysis.application.service;

import com.smartshopai.ai.analysis.application.dto.request.CreateAnalysisRequest;
import com.smartshopai.ai.analysis.application.dto.response.AnalysisResponse;
import com.smartshopai.ai.analysis.application.dto.response.ComparisonResponse;
import com.smartshopai.ai.analysis.application.mapper.AnalysisMapper;
import com.smartshopai.ai.analysis.domain.entity.ProductAnalysis;
import com.smartshopai.ai.analysis.domain.repository.ProductAnalysisRepository;
import com.smartshopai.ai.analysis.domain.service.AnalysisEngineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Application service for AI Analysis operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AIAnalysisApplicationService {
    
    private final AnalysisMapper analysisMapper;
    private final ProductAnalysisRepository productAnalysisRepository;
    private final AnalysisEngineService analysisEngineService;
    
    /**
     * Create a new product analysis
     */
    public AnalysisResponse createAnalysis(CreateAnalysisRequest request) {
        log.info("Creating analysis for product: {}", request.getProductId());
        
        ProductAnalysis entity = analysisMapper.toEntity(request);
        ProductAnalysis savedEntity = productAnalysisRepository.save(entity);
        
        // Perform AI analysis
        ProductAnalysis analyzedEntity = analysisEngineService.analyzeProduct(savedEntity);
        ProductAnalysis finalEntity = productAnalysisRepository.save(analyzedEntity);
        
        return analysisMapper.toResponse(finalEntity);
    }
    
    /**
     * Get analysis by ID
     */
    @Transactional(readOnly = true)
    public AnalysisResponse getAnalysisById(String analysisId) {
        log.info("Getting analysis by ID: {}", analysisId);
        
        ProductAnalysis entity = productAnalysisRepository.findById(analysisId)
                .orElseThrow(() -> new RuntimeException("Analysis not found"));
        
        return analysisMapper.toResponse(entity);
    }
    
    /**
     * Get all analyses for a product
     */
    @Transactional(readOnly = true)
    public List<AnalysisResponse> getAnalysesByProductId(String productId) {
        log.info("Getting analyses for product: {}", productId);
        
        List<ProductAnalysis> entities = productAnalysisRepository.findByProductId(productId);
        return analysisMapper.toResponseList(entities);
    }
    
    /**
     * Get active analyses by product ID
     */
    @Transactional(readOnly = true)
    public List<AnalysisResponse> getActiveAnalysesByProductId(String productId) {
        log.info("Getting active analyses for product: {}", productId);
        
        // Mock implementation - will be replaced with real filtering when needed
        List<ProductAnalysis> entities = productAnalysisRepository.findByProductId(productId);
        return analysisMapper.toResponseList(entities);
    }
    
    /**
     * Get analyses by type
     */
    @Transactional(readOnly = true)
    public List<AnalysisResponse> getAnalysesByType(String analysisType) {
        log.info("Getting analyses by type: {}", analysisType);
        
        // Mock implementation - will be replaced with real filtering when needed
        List<ProductAnalysis> entities = productAnalysisRepository.findByAnalysisType(analysisType);
        return analysisMapper.toResponseList(entities);
    }
    
    /**
     * Delete analysis
     */
    public void deleteAnalysis(String analysisId) {
        log.info("Deleting analysis: {}", analysisId);
        
        ProductAnalysis entity = productAnalysisRepository.findById(analysisId)
                .orElseThrow(() -> new RuntimeException("Analysis not found"));
        
        entity.setAnalysisStatus("DELETED");
        productAnalysisRepository.save(entity);
    }
    
    /**
     * Analyze product using AI
     */
    public AnalysisResponse analyzeProduct(CreateAnalysisRequest request) {
        log.info("Analyzing product: {}", request.getProductId());
        
        ProductAnalysis analysis = analysisMapper.toEntity(request);
        analysis = analysisEngineService.analyzeProduct(analysis);
        analysis = productAnalysisRepository.save(analysis);
        
        return analysisMapper.toResponse(analysis);
    }
    
    /**
     * Compare products using AI
     */
    public ComparisonResponse compareProducts(CreateAnalysisRequest request) {
        log.info("Comparing products: {}", request.getProductData());
        
        // Extract product IDs from the request
        List<String> productIds = extractProductIds(request.getProductData());
        
        // Perform individual analyses for each product
        List<ProductAnalysis> analyses = productIds.stream()
                .map(productId -> {
                    CreateAnalysisRequest individualRequest = CreateAnalysisRequest.builder()
                            .productId(productId)
                            .analysisType("comparison")
                            .userId(request.getUserId())
                            .build();
                    AnalysisResponse response = analyzeProduct(individualRequest);
                    return productAnalysisRepository.findById(response.getAnalysisId())
                            .orElseThrow(() -> new RuntimeException("Analysis not found"));
                })
                .collect(Collectors.toList());
        
        // Generate comparison content
        String comparisonContent = generateComparisonContent(analyses);
        String summary = generateComparisonSummary(analyses);
        
        ComparisonResponse response = ComparisonResponse.builder()
                .comparisonId("comp_" + System.currentTimeMillis())
                .productIds(productIds)
                .comparisonType("overall")
                .comparisonContent(comparisonContent)
                .summary(summary)
                .confidenceScore(calculateConfidenceScore(analyses))
                .modelUsed("gpt-4")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .isActive(true)
                .build();
        
        return response;
    }
    
    /**
     * Get analysis result by ID
     */
    public AnalysisResponse getAnalysisResult(String analysisId) {
        log.info("Getting analysis result: {}", analysisId);
        
        ProductAnalysis analysis = productAnalysisRepository.findById(analysisId)
                .orElseThrow(() -> new RuntimeException("Analysis not found"));
        
        return analysisMapper.toResponse(analysis);
    }
    
    /**
     * Get user analysis history
     */
    public List<AnalysisResponse> getUserAnalysisHistory(String userId) {
        log.info("Getting analysis history for user: {}", userId);
        
        List<ProductAnalysis> analyses = productAnalysisRepository.findByUserId(userId);
        return analysisMapper.toResponseList(analyses);
    }
    
    /**
     * Get health status
     */
    public String getHealthStatus() {
        return "AI Analysis Service is healthy";
    }
    
    /**
     * Extract product IDs from product data
     */
    private List<String> extractProductIds(Map<String, Object> productData) {
        // This is a simplified implementation
        // In a real scenario, you would parse the product data more carefully
        if (productData.containsKey("productIds")) {
            return (List<String>) productData.get("productIds");
        }
        return List.of("product1", "product2"); // Default fallback
    }
    
    /**
     * Generate comparison content from analyses
     */
    private String generateComparisonContent(List<ProductAnalysis> analyses) {
        if (analyses.size() < 2) {
            return "Insufficient data for comparison";
        }
        
        StringBuilder content = new StringBuilder();
        content.append("Product Comparison Analysis:\n\n");
        
        for (int i = 0; i < analyses.size(); i++) {
            ProductAnalysis analysis = analyses.get(i);
            content.append("Product ").append(i + 1).append(":\n");
            content.append("- Summary: ").append(analysis.getUserFriendlyDescription()).append("\n");
            content.append("- Key Features: ").append(String.join(", ", analysis.getKeyFeatures())).append("\n");
            content.append("- Pros: ").append(String.join(", ", analysis.getPros())).append("\n");
            content.append("- Cons: ").append(String.join(", ", analysis.getCons())).append("\n\n");
        }
        
        return content.toString();
    }
    
    /**
     * Generate comparison summary
     */
    private String generateComparisonSummary(List<ProductAnalysis> analyses) {
        if (analyses.isEmpty()) {
            return "No products to compare";
        }
        
        return "Comparison of " + analyses.size() + " products completed. " +
               "Each product has been analyzed for features, pros, and cons.";
    }
    
    /**
     * Calculate confidence score for comparison
     */
    private double calculateConfidenceScore(List<ProductAnalysis> analyses) {
        if (analyses.isEmpty()) {
            return 0.0;
        }
        
        // Calculate average confidence score
        double totalConfidence = analyses.stream()
                .mapToDouble(ProductAnalysis::getConfidenceScore)
                .sum();
        
        return totalConfidence / analyses.size();
    }
}
