package com.smartshopai.ai.analysis.domain.repository;

import com.smartshopai.ai.analysis.domain.entity.AnalysisRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnalysisRequestRepository extends MongoRepository<AnalysisRequest, String> {
    
    Optional<AnalysisRequest> findByRequestId(String requestId);
    
    List<AnalysisRequest> findByUserId(String userId);
    
    List<AnalysisRequest> findByAnalysisType(String analysisType);
    
    List<AnalysisRequest> findByStatus(String status);
    
    List<AnalysisRequest> findByCreatedAtBetween(java.time.LocalDateTime start, java.time.LocalDateTime end);
    
    boolean existsByRequestId(String requestId);
    
    long countByUserId(String userId);
    
    long countByAnalysisType(String analysisType);
    
    long countByStatus(String status);
}
