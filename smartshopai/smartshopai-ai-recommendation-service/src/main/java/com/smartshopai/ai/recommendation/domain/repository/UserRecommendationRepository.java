package com.smartshopai.ai.recommendation.domain.repository;

import com.smartshopai.ai.recommendation.domain.entity.UserRecommendation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRecommendationRepository extends MongoRepository<UserRecommendation, String> {
    
    Optional<UserRecommendation> findByUserIdAndRecommendationType(String userId, String recommendationType);
    
    List<UserRecommendation> findByUserId(String userId);
    
    @Query("{'userId': ?0, 'isActive': true, 'expiresAt': {$gt: ?1}}")
    List<UserRecommendation> findActiveByUserId(String userId, LocalDateTime now);
    
    @Query("{'recommendationType': ?0, 'isActive': true}")
    List<UserRecommendation> findActiveByRecommendationType(String recommendationType);
    
    @Query("{'confidenceScore': {$gte: ?0}, 'isActive': true}")
    List<UserRecommendation> findHighConfidenceRecommendations(Double minConfidence);
    
    @Query("{'userId': ?0, 'isViewed': false, 'isActive': true}")
    List<UserRecommendation> findUnviewedByUserId(String userId);
    
    boolean existsByUserIdAndRecommendationType(String userId, String recommendationType);
}
