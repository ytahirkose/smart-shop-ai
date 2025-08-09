package com.smartshopai.sessioncache.domain.service;

import com.smartshopai.sessioncache.domain.entity.UserSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Domain service for session management operations
 */
public interface SessionService {

    /**
     * Create a new user session
     */
    UserSession createSession(String userId, String sessionId, String userAgent, String ipAddress);

    /**
     * Get session by session ID
     */
    Optional<UserSession> getSession(String sessionId);

    /**
     * Update session activity
     */
    UserSession updateSessionActivity(String sessionId);

    /**
     * Update session data
     */
    UserSession updateSessionData(String sessionId, Map<String, Object> sessionData);

    /**
     * Update session context
     */
    UserSession updateSessionContext(String sessionId, String currentPage, String referrer);

    /**
     * Update AI context
     */
    UserSession updateAIContext(String sessionId, Map<String, Object> aiContext);

    /**
     * Update recommendations
     */
    UserSession updateRecommendations(String sessionId, Map<String, Object> recommendations);

    /**
     * Update user preferences
     */
    UserSession updateUserPreferences(String sessionId, Map<String, Object> preferences);

    /**
     * Track page view
     */
    UserSession trackPageView(String sessionId, String pageUrl);

    /**
     * Track click
     */
    UserSession trackClick(String sessionId, String elementId);

    /**
     * Track search
     */
    UserSession trackSearch(String sessionId, String searchTerm);

    /**
     * Track product view
     */
    UserSession trackProductView(String sessionId, String productId);

    /**
     * Set authentication tokens
     */
    UserSession setAuthenticationTokens(String sessionId, String authToken, String refreshToken, LocalDateTime expiryTime);

    /**
     * Validate authentication token
     */
    boolean validateAuthenticationToken(String token);

    /**
     * Refresh authentication token
     */
    UserSession refreshAuthenticationToken(String sessionId, String newToken, LocalDateTime expiryTime);

    /**
     * End session
     */
    UserSession endSession(String sessionId);

    /**
     * Get active sessions for user
     */
    List<UserSession> getActiveSessionsByUserId(String userId);

    /**
     * Get all active sessions
     */
    List<UserSession> getAllActiveSessions();

    /**
     * Get session statistics
     */
    Map<String, Object> getSessionStatistics();

    /**
     * Get user session statistics
     */
    Map<String, Object> getUserSessionStatistics(String userId);

    /**
     * Clean up expired sessions
     */
    void cleanupExpiredSessions();

    /**
     * Get sessions by time range
     */
    List<UserSession> getSessionsByTimeRange(LocalDateTime start, LocalDateTime end);

    /**
     * Get sessions by device type
     */
    List<UserSession> getSessionsByDeviceType(String deviceType);

    /**
     * Get sessions by browser
     */
    List<UserSession> getSessionsByBrowser(String browser);

    /**
     * Get sessions by operating system
     */
    List<UserSession> getSessionsByOperatingSystem(String operatingSystem);

    /**
     * Get sessions by IP address
     */
    List<UserSession> getSessionsByIpAddress(String ipAddress);

    /**
     * Export session data
     */
    byte[] exportSessionData(String format, Map<String, Object> filters);

    /**
     * Get real-time session metrics
     */
    Map<String, Object> getRealTimeSessionMetrics();
}
