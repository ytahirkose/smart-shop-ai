package com.smartshopai.ai.analysis.domain.repository;

import com.smartshopai.ai.analysis.domain.entity.AnalysisResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for AnalysisResult entity
 */
@Repository
public interface AnalysisResultRepository extends MongoRepository<AnalysisResult, String> {

    List<AnalysisResult> findByUserId(String userId);
    
    List<AnalysisResult> findByProductId(String productId);
    
    List<AnalysisResult> findByAnalysisType(String analysisType);
    
    Optional<AnalysisResult> findByAnalysisRequestId(String analysisRequestId);
    
    List<AnalysisResult> findByUserIdAndAnalysisType(String userId, String analysisType);
    
    List<AnalysisResult> findByProductIdAndAnalysisType(String productId, String analysisType);
}
