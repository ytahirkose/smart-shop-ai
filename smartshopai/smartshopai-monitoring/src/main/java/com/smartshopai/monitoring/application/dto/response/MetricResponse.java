package com.smartshopai.monitoring.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Response DTO for metric data
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetricResponse {

    private String id;
    private String serviceName;
    private String metricName;
    private String metricType;
    private String metricUnit;
    
    // Metric value
    private Double value;
    private String stringValue;
    private Map<String, Object> objectValue;
    
    // Labels and tags
    private Map<String, String> labels;
    private Map<String, String> tags;
    
    // Timestamp
    private LocalDateTime timestamp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Metadata
    private String description;
    private String help;
    private Map<String, Object> metadata;
    
    // Status
    private String status;
    private boolean enabled;
}
