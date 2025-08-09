package com.smartshopai.sessioncache.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.util.Map;

/**
 * Request DTO for updating session data
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSessionRequest {

    @NotBlank(message = "Session ID is required")
    private String sessionId;

    private String currentPage;
    private String referrer;
    private Map<String, Object> sessionData;
    private Map<String, Object> aiContext;
    private Map<String, Object> recommendations;
    private Map<String, Object> userPreferences;
    private Map<String, Object> analyticsData;
    private Map<String, Double> metrics;
}
