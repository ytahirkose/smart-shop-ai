package com.smartshopai.ai.analysis.domain.repository;

import com.smartshopai.ai.analysis.domain.entity.ProductComparison;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for ProductComparison entity
 */
@Repository
public interface ProductComparisonRepository extends MongoRepository<ProductComparison, String> {

    List<ProductComparison> findByUserId(String userId);
    
    List<ProductComparison> findByRequestId(String requestId);
    
    List<ProductComparison> findByComparisonType(String comparisonType);
    
    List<ProductComparison> findByProductIdsContaining(String productId);
    
    List<ProductComparison> findByUserIdAndComparisonType(String userId, String comparisonType);
}
