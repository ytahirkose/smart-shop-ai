package com.smartshopai.ai.analysis.domain.repository;

import com.smartshopai.ai.analysis.domain.entity.ProductAnalysis;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductAnalysisRepository extends MongoRepository<ProductAnalysis, String> {
    
    List<ProductAnalysis> findByProductId(String productId);
    
    List<ProductAnalysis> findByUserId(String userId);
    
    List<ProductAnalysis> findByCategory(String category);
    
    List<ProductAnalysis> findByAnalysisType(String analysisType);
    
    List<ProductAnalysis> findByCreatedAtBetween(java.time.LocalDateTime start, java.time.LocalDateTime end);
    
    boolean existsByProductId(String productId);
    
    long countByUserId(String userId);
    
    long countByCategory(String category);
}
