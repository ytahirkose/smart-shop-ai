package com.smartshopai.monitoring.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Request DTO for getting metrics
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMetricsRequest {

    @NotBlank(message = "Metric name is required")
    private String metricName;

    private String serviceName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String timeGranularity; // MINUTE, HOUR, DAY, WEEK, MONTH
    private Map<String, Object> filters;
    private Integer limit;
}
