package com.smartshopai.monitoring.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Metric entity for SmartShopAI application
 * Represents various system and business metrics
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "metrics")
public class Metric {

    @Id
    private String id;
    
    private String serviceName;
    private String metricName;
    private String metricType; // COUNTER, GAUGE, HISTOGRAM, SUMMARY
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
    private String status; // ACTIVE, INACTIVE, DEPRECATED
    private boolean enabled;
    
    // Pre-save method to set timestamps
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        updatedAt = LocalDateTime.now();
    }
}
