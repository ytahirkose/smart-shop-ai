package com.smartshopai.ai.analysis.domain.repository;

import com.smartshopai.ai.analysis.domain.entity.AnalysisRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalysisRepository extends MongoRepository<AnalysisRequest, String> {
    
    List<AnalysisRequest> findByUserId(String userId);
    
    List<AnalysisRequest> findByStatus(String status);
    
    List<AnalysisRequest> findByUserIdAndStatus(String userId, String status);
    
    List<AnalysisRequest> findByProductId(String productId);
}
