package com.smartshopai.ai.recommendation.domain.repository;

import com.smartshopai.ai.recommendation.domain.entity.RecommendationRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendationRequestRepository extends MongoRepository<RecommendationRequest, String> {
    
    List<RecommendationRequest> findByUserId(String userId);
    
    List<RecommendationRequest> findByUserIdAndStatus(String userId, String status);
    
    List<RecommendationRequest> findByRequestType(String requestType);
    
    List<RecommendationRequest> findByContext(String context);
    
    List<RecommendationRequest> findByStatus(String status);
    
    List<RecommendationRequest> findByAiAnalysisCompleted(boolean completed);
    
    List<RecommendationRequest> findByCreatedAtAfter(LocalDateTime date);
    
    List<RecommendationRequest> findByUserIdAndRequestType(String userId, String requestType);
    
    Optional<RecommendationRequest> findTopByUserIdOrderByCreatedAtDesc(String userId);
    
    boolean existsByUserIdAndRequestTypeAndStatus(String userId, String requestType, String status);
}
