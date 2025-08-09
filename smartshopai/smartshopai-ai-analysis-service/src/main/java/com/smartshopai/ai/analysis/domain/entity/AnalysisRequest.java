package com.smartshopai.ai.analysis.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "analysis_requests")
public class AnalysisRequest {

    @Id
    private String id;
    
    private String userId;
    private String productId;
    private List<String> productIds;
    private String prompt;
    private String analysisType;
    private String status; // PENDING, PROCESSING, COMPLETED, FAILED
    private String errorMessage;
    private String requestId;
    
    // AI processing flags
    private boolean aiAnalysisCompleted;
    private LocalDateTime processedAt;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Pre-save method to set timestamps
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        updatedAt = LocalDateTime.now();
    }
    
    // Pre-update method to set updated timestamp
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
