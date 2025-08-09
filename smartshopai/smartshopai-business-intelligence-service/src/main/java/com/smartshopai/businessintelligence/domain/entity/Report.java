package com.smartshopai.businessintelligence.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Report entity for automated and scheduled reports
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "reports")
public class Report {

    @Id
    private String id;
    
    // Report identification
    private String name;
    private String description;
    private String type; // DAILY, WEEKLY, MONTHLY, CUSTOM
    private String format; // PDF, EXCEL, CSV, JSON, HTML
    
    // Report configuration
    private String query;
    private Map<String, Object> parameters;
    private List<String> metrics;
    private List<String> dimensions;
    private Map<String, String> filters;
    
    // Scheduling
    private String schedule; // Cron expression
    private LocalDateTime lastRunAt;
    private LocalDateTime nextRunAt;
    private String status; // ACTIVE, INACTIVE, PAUSED
    
    // Recipients
    private List<String> recipients;
    private String deliveryMethod; // EMAIL, WEBHOOK, FTP, S3
    
    // Output
    private String filePath;
    private String fileUrl;
    private Long fileSize;
    private String checksum;
    
    // Execution
    private LocalDateTime executionStartTime;
    private LocalDateTime executionEndTime;
    private Long executionDurationMs;
    private String executionStatus; // SUCCESS, FAILED, RUNNING
    private String errorMessage;
    
    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Metadata
    private String createdBy;
    private String updatedBy;
    private Map<String, Object> metadata;
    
    // Pre-save method to set timestamps
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        updatedAt = LocalDateTime.now();
    }
}
