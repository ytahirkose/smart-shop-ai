package com.smartshopai.ai.analysis.domain.repository;

import com.smartshopai.ai.analysis.domain.entity.ProductComparison;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductComparisonRepository extends MongoRepository<ProductComparison, String> {
    
    Optional<ProductComparison> findByComparisonId(String comparisonId);
    
    List<ProductComparison> findByUserId(String userId);
    
    List<ProductComparison> findByProductIdsContaining(String productId);
    
    List<ProductComparison> findByCategory(String category);
    
    List<ProductComparison> findByCreatedAtBetween(java.time.LocalDateTime start, java.time.LocalDateTime end);
    
    boolean existsByComparisonId(String comparisonId);
    
    long countByUserId(String userId);
    
    long countByCategory(String category);
}
