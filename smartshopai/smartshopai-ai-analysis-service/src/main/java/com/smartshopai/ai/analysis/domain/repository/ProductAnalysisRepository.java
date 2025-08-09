package com.smartshopai.ai.analysis.domain.repository;

import com.smartshopai.ai.analysis.domain.entity.ProductAnalysis;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductAnalysisRepository extends MongoRepository<ProductAnalysis, String> {
    
    Optional<ProductAnalysis> findByProductIdAndAnalysisType(String productId, String analysisType);
    
    List<ProductAnalysis> findByProductId(String productId);
    
    List<ProductAnalysis> findByUserId(String userId);
    
    @Query("{'productId': ?0, 'isActive': true}")
    List<ProductAnalysis> findActiveByProductId(String productId);
    
    @Query("{'analysisType': ?0, 'isActive': true}")
    List<ProductAnalysis> findActiveByAnalysisType(String analysisType);
    
    @Query("{'confidenceScore': {$gte: ?0}, 'isActive': true}")
    List<ProductAnalysis> findHighConfidenceAnalyses(Double minConfidence);
    
    boolean existsByProductIdAndAnalysisType(String productId, String analysisType);
}
