package com.smartshopai.monitoring.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

/**
 * Request DTO for recording metrics
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordMetricRequest {

    @NotBlank(message = "Service name is required")
    private String serviceName;

    @NotBlank(message = "Metric name is required")
    private String metricName;

    @NotBlank(message = "Metric type is required")
    private String metricType; // COUNTER, GAUGE, HISTOGRAM, SUMMARY

    private String metricUnit;

    @NotNull(message = "Metric value is required")
    private Double value;

    private String stringValue;
    private Map<String, Object> objectValue;

    // Labels and tags
    private Map<String, String> labels;
    private Map<String, String> tags;

    // Metadata
    private String description;
    private String help;
    private Map<String, Object> metadata;
}
