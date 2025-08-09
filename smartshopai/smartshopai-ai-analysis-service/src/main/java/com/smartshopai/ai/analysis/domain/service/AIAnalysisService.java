package com.smartshopai.ai.analysis.domain.service;

import com.smartshopai.ai.analysis.domain.entity.AnalysisRequest;
import com.smartshopai.ai.analysis.domain.entity.AnalysisResult;
import com.smartshopai.ai.analysis.domain.entity.ProductComparison;

import java.util.List;

public interface AIAnalysisService {
    
    AnalysisRequest createAnalysisRequest(AnalysisRequest request);
    
    AnalysisResult analyzeProduct(String productId, String userId, String prompt);
    
    ProductComparison compareProducts(String userId, String requestId, List<String> productIds);
    
    AnalysisResult getAnalysisResult(String analysisId);
    
    List<AnalysisRequest> getUserAnalysisRequests(String userId);
    
    void updateAnalysisStatus(String requestId, String status);
}
