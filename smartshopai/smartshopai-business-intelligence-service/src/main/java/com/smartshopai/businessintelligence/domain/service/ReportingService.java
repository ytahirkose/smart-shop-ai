package com.smartshopai.businessintelligence.domain.service;

import com.smartshopai.businessintelligence.domain.entity.Report;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Domain service for reporting operations
 */
public interface ReportingService {

    /**
     * Create a new report
     */
    Report createReport(Report report);

    /**
     * Get report by ID
     */
    Report getReportById(String reportId);

    /**
     * Get all reports
     */
    List<Report> getAllReports();

    /**
     * Get reports by type
     */
    List<Report> getReportsByType(String type);

    /**
     * Get reports by status
     */
    List<Report> getReportsByStatus(String status);

    /**
     * Update report
     */
    Report updateReport(Report report);

    /**
     * Delete report
     */
    void deleteReport(String reportId);

    /**
     * Execute report
     */
    Report executeReport(String reportId);

    /**
     * Execute report with parameters
     */
    Report executeReportWithParameters(String reportId, Map<String, Object> parameters);

    /**
     * Schedule report
     */
    Report scheduleReport(String reportId, String cronExpression);

    /**
     * Pause report
     */
    Report pauseReport(String reportId);

    /**
     * Resume report
     */
    Report resumeReport(String reportId);

    /**
     * Generate custom report
     */
    byte[] generateCustomReport(String query, String format, Map<String, Object> parameters);

    /**
     * Get report execution history
     */
    List<Report> getReportExecutionHistory(String reportId, LocalDateTime start, LocalDateTime end);

    /**
     * Get report statistics
     */
    Map<String, Object> getReportStatistics();

    /**
     * Validate report configuration
     */
    boolean validateReportConfiguration(Report report);

    /**
     * Test report query
     */
    Map<String, Object> testReportQuery(String query, Map<String, Object> parameters);

    /**
     * Export report data
     */
    byte[] exportReportData(String reportId, String format, Map<String, Object> filters);

    /**
     * Get report templates
     */
    List<Map<String, Object>> getReportTemplates();

    /**
     * Create report from template
     */
    Report createReportFromTemplate(String templateId, Map<String, Object> parameters);
}
