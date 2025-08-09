package com.smartshopai.businessintelligence.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Business metric entity for tracking key performance indicators
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "business_metrics")
public class BusinessMetric {

    @Id
    private String id;
    
    // Metric identification
    private String metricName; // REVENUE, CONVERSION_RATE, USER_ENGAGEMENT, etc.
    private String metricType; // COUNTER, GAUGE, HISTOGRAM, SUMMARY
    private String metricUnit; // USD, PERCENTAGE, COUNT, etc.
    
    // Business context
    private String businessUnit; // SALES, MARKETING, PRODUCT, etc.
    private String category; // FINANCIAL, OPERATIONAL, CUSTOMER, etc.
    private String dimension; // TIME, PRODUCT, USER, LOCATION, etc.
    
    // Metric value
    private Double value;
    private String stringValue;
    private Map<String, Object> objectValue;
    
    // Aggregation
    private String aggregationType; // SUM, AVG, COUNT, MIN, MAX
    private String timeGranularity; // HOURLY, DAILY, WEEKLY, MONTHLY, YEARLY
    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;
    
    // Dimensions and filters
    private Map<String, String> dimensions; // product_id, user_id, category, etc.
    private Map<String, String> filters; // date_range, user_segment, etc.
    
    // Comparison
    private Double previousValue;
    private Double changePercent;
    private String trend; // INCREASING, DECREASING, STABLE
    
    // Targets and thresholds
    private Double targetValue;
    private Double thresholdValue;
    private String thresholdType; // MIN, MAX, RANGE
    private String status; // ON_TRACK, BELOW_TARGET, ABOVE_TARGET
    
    // Timestamps
    private LocalDateTime timestamp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Metadata
    private String description;
    private String formula;
    private String dataSource;
    private Map<String, Object> metadata;
    
    // Pre-save method to set timestamps
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        updatedAt = LocalDateTime.now();
    }
}
