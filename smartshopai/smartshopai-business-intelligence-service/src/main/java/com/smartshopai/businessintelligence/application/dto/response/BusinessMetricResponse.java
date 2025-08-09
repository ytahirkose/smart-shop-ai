package com.smartshopai.businessintelligence.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Response DTO for business metrics
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessMetricResponse {

    private String id;
    private String metricName;
    private String metricType;
    private String metricUnit;
    private String businessUnit;
    private String category;
    private String dimension;
    private Double value;
    private String stringValue;
    private Map<String, Object> objectValue;
    private String aggregationType;
    private String timeGranularity;
    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;
    private Map<String, String> dimensions;
    private Map<String, String> filters;
    private Double previousValue;
    private Double changePercent;
    private String trend;
    private Double targetValue;
    private Double thresholdValue;
    private String thresholdType;
    private String status;
    private LocalDateTime timestamp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String description;
    private String formula;
    private String dataSource;
    private Map<String, Object> metadata;
}
