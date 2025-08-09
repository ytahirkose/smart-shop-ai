package com.smartshopai.ai.recommendation.domain.repository;

import com.smartshopai.ai.recommendation.domain.entity.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Recommendation entity
 */
@Repository
public interface RecommendationRepository extends MongoRepository<Recommendation, String> {
    
    /**
     * Find recommendations by user ID
     */
    List<Recommendation> findByUserId(String userId);
    
    /**
     * Find recommendations by request type
     */
    List<Recommendation> findByRequestType(String requestType);
    
    /**
     * Find recommendations by user ID and request type
     */
    List<Recommendation> findByUserIdAndRequestType(String userId, String requestType);
    
    /**
     * Find recommendations by context
     */
    List<Recommendation> findByContext(String context);
    
    /**
     * Find recommendations by user ID and context
     */
    List<Recommendation> findByUserIdAndContext(String userId, String context);
}
