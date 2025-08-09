package com.smartshopai.monitoring.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.util.Map;

/**
 * Request DTO for health check
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthCheckRequest {

    @NotBlank(message = "Service name is required")
    private String serviceName;

    private String endpoint;
    private Map<String, Object> parameters;
    private Integer timeout;
    private Boolean detailed;
}
