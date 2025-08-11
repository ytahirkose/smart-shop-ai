package com.smartshopai.ai.analysis.domain.repository;

import com.smartshopai.ai.analysis.domain.entity.AnalysisResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnalysisResultRepository extends MongoRepository<AnalysisResult, String> {
    
    Optional<AnalysisResult> findByResultId(String resultId);
    
    List<AnalysisResult> findByUserId(String userId);
    
    List<AnalysisResult> findByAnalysisType(String analysisType);
    
    List<AnalysisResult> findByStatus(String status);
    
    List<AnalysisResult> findByCreatedAtBetween(java.time.LocalDateTime start, java.time.LocalDateTime end);
    
    boolean existsByResultId(String resultId);
    
    long countByUserId(String userId);
    
    long countByAnalysisType(String analysisType);
    
    long countByStatus(String status);
}
