package com.smartshopai.sessioncache.application.service;

import com.smartshopai.sessioncache.application.dto.request.CreateSessionRequest;
import com.smartshopai.sessioncache.application.dto.request.UpdateSessionRequest;
import com.smartshopai.sessioncache.application.dto.request.TrackActivityRequest;
import com.smartshopai.sessioncache.application.dto.response.UserSessionResponse;
import com.smartshopai.sessioncache.application.mapper.SessionMapper;
import com.smartshopai.sessioncache.domain.entity.UserSession;
import com.smartshopai.sessioncache.domain.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Application service for session management operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SessionApplicationService {

    private final SessionService sessionService;
    private final SessionMapper sessionMapper;

    public UserSessionResponse createSession(CreateSessionRequest request) {
        log.info("Creating new session for user: {} with session ID: {}", request.getUserId(), request.getSessionId());
        
        UserSession session = sessionService.createSession(
            request.getUserId(),
            request.getSessionId(),
            request.getUserAgent(),
            request.getIpAddress()
        );
        
        return sessionMapper.toResponse(session);
    }

    public Optional<UserSessionResponse> getSession(String sessionId) {
        log.debug("Getting session: {}", sessionId);
        
        Optional<UserSession> session = sessionService.getSession(sessionId);
        return session.map(sessionMapper::toResponse);
    }

    public UserSessionResponse updateSessionActivity(String sessionId) {
        log.debug("Updating session activity: {}", sessionId);
        
        UserSession session = sessionService.updateSessionActivity(sessionId);
        return sessionMapper.toResponse(session);
    }

    public UserSessionResponse updateSessionData(UpdateSessionRequest request) {
        log.debug("Updating session data: {}", request.getSessionId());
        
        UserSession session = sessionService.updateSessionData(request.getSessionId(), request.getSessionData());
        return sessionMapper.toResponse(session);
    }

    public UserSessionResponse updateSessionContext(String sessionId, String currentPage, String referrer) {
        log.debug("Updating session context: {} - page: {}", sessionId, currentPage);
        
        UserSession session = sessionService.updateSessionContext(sessionId, currentPage, referrer);
        return sessionMapper.toResponse(session);
    }

    public UserSessionResponse updateAIContext(String sessionId, Map<String, Object> aiContext) {
        log.debug("Updating AI context for session: {}", sessionId);
        
        UserSession session = sessionService.updateAIContext(sessionId, aiContext);
        return sessionMapper.toResponse(session);
    }

    public UserSessionResponse updateRecommendations(String sessionId, Map<String, Object> recommendations) {
        log.debug("Updating recommendations for session: {}", sessionId);
        
        UserSession session = sessionService.updateRecommendations(sessionId, recommendations);
        return sessionMapper.toResponse(session);
    }

    public UserSessionResponse updateUserPreferences(String sessionId, Map<String, Object> preferences) {
        log.debug("Updating user preferences for session: {}", sessionId);
        
        UserSession session = sessionService.updateUserPreferences(sessionId, preferences);
        return sessionMapper.toResponse(session);
    }

    public UserSessionResponse trackActivity(TrackActivityRequest request) {
        log.debug("Tracking activity for session: {} - type: {}", request.getSessionId(), request.getActivityType());
        
        UserSession session = null;
        
        switch (request.getActivityType()) {
            case "PAGE_VIEW":
                session = sessionService.trackPageView(request.getSessionId(), request.getActivityData());
                break;
            case "CLICK":
                session = sessionService.trackClick(request.getSessionId(), request.getActivityData());
                break;
            case "SEARCH":
                session = sessionService.trackSearch(request.getSessionId(), request.getActivityData());
                break;
            case "PRODUCT_VIEW":
                session = sessionService.trackProductView(request.getSessionId(), request.getActivityData());
                break;
            default:
                throw new IllegalArgumentException("Unknown activity type: " + request.getActivityType());
        }
        
        return sessionMapper.toResponse(session);
    }

    public UserSessionResponse setAuthenticationTokens(String sessionId, String authToken, String refreshToken, LocalDateTime expiryTime) {
        log.debug("Setting authentication tokens for session: {}", sessionId);
        
        UserSession session = sessionService.setAuthenticationTokens(sessionId, authToken, refreshToken, expiryTime);
        return sessionMapper.toResponse(session);
    }

    public boolean validateAuthenticationToken(String token) {
        log.debug("Validating authentication token");
        return sessionService.validateAuthenticationToken(token);
    }

    public UserSessionResponse refreshAuthenticationToken(String sessionId, String newToken, LocalDateTime expiryTime) {
        log.debug("Refreshing authentication token for session: {}", sessionId);
        
        UserSession session = sessionService.refreshAuthenticationToken(sessionId, newToken, expiryTime);
        return sessionMapper.toResponse(session);
    }

    public UserSessionResponse endSession(String sessionId) {
        log.info("Ending session: {}", sessionId);
        
        UserSession session = sessionService.endSession(sessionId);
        return sessionMapper.toResponse(session);
    }

    public List<UserSessionResponse> getActiveSessionsByUserId(String userId) {
        log.debug("Getting active sessions for user: {}", userId);
        
        List<UserSession> sessions = sessionService.getActiveSessionsByUserId(userId);
        return sessionMapper.toResponseList(sessions);
    }

    public List<UserSessionResponse> getAllActiveSessions() {
        log.debug("Getting all active sessions");
        
        List<UserSession> sessions = sessionService.getAllActiveSessions();
        return sessionMapper.toResponseList(sessions);
    }

    public Map<String, Object> getSessionStatistics() {
        log.debug("Getting session statistics");
        return sessionService.getSessionStatistics();
    }

    public Map<String, Object> getUserSessionStatistics(String userId) {
        log.debug("Getting session statistics for user: {}", userId);
        return sessionService.getUserSessionStatistics(userId);
    }

    public List<UserSessionResponse> getSessionsByTimeRange(LocalDateTime start, LocalDateTime end) {
        log.debug("Getting sessions by time range: {} to {}", start, end);
        
        List<UserSession> sessions = sessionService.getSessionsByTimeRange(start, end);
        return sessionMapper.toResponseList(sessions);
    }

    public List<UserSessionResponse> getSessionsByDeviceType(String deviceType) {
        log.debug("Getting sessions by device type: {}", deviceType);
        
        List<UserSession> sessions = sessionService.getSessionsByDeviceType(deviceType);
        return sessionMapper.toResponseList(sessions);
    }

    public List<UserSessionResponse> getSessionsByBrowser(String browser) {
        log.debug("Getting sessions by browser: {}", browser);
        
        List<UserSession> sessions = sessionService.getSessionsByBrowser(browser);
        return sessionMapper.toResponseList(sessions);
    }

    public List<UserSessionResponse> getSessionsByOperatingSystem(String operatingSystem) {
        log.debug("Getting sessions by operating system: {}", operatingSystem);
        
        List<UserSession> sessions = sessionService.getSessionsByOperatingSystem(operatingSystem);
        return sessionMapper.toResponseList(sessions);
    }

    public List<UserSessionResponse> getSessionsByIpAddress(String ipAddress) {
        log.debug("Getting sessions by IP address: {}", ipAddress);
        
        List<UserSession> sessions = sessionService.getSessionsByIpAddress(ipAddress);
        return sessionMapper.toResponseList(sessions);
    }

    public byte[] exportSessionData(String format, Map<String, Object> filters) {
        log.info("Exporting session data in format: {}", format);
        return sessionService.exportSessionData(format, filters);
    }

    public Map<String, Object> getRealTimeSessionMetrics() {
        log.debug("Getting real-time session metrics");
        return sessionService.getRealTimeSessionMetrics();
    }
}
