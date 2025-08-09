package com.smartshopai.ai.analysis.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisRequestResponse {

    private String id;
    private String userId;
    private String productId;
    private String requestType;
    private String status;
    private String prompt;
    private List<String> productIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String errorMessage;
}
