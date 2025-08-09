package com.smartshopai.ai.recommendation.domain.repository;

import com.smartshopai.ai.recommendation.domain.entity.RecommendationResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendationResultRepository extends MongoRepository<RecommendationResult, String> {
    
    List<RecommendationResult> findByUserId(String userId);
    
    List<RecommendationResult> findByUserIdAndStatus(String userId, String status);
    
    List<RecommendationResult> findByRequestType(String requestType);
    
    List<RecommendationResult> findByContext(String context);
    
    List<RecommendationResult> findByStatus(String status);
    
    List<RecommendationResult> findByPersonalized(boolean personalized);
    
    List<RecommendationResult> findByRealTime(boolean realTime);
    
    List<RecommendationResult> findByAiAnalysisCompleted(boolean completed);
    
    List<RecommendationResult> findByCreatedAtAfter(LocalDateTime date);
    
    List<RecommendationResult> findByExpiresAtBefore(LocalDateTime date);
    
    Optional<RecommendationResult> findByRecommendationRequestId(String requestId);
    
    Optional<RecommendationResult> findTopByUserIdOrderByCreatedAtDesc(String userId);
    
    List<RecommendationResult> findByUserIdAndRequestType(String userId, String requestType);
    
    List<RecommendationResult> findByUserIdAndPersonalized(String userId, boolean personalized);
}
