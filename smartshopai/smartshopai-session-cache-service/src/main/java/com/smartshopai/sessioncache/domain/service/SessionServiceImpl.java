package com.smartshopai.sessioncache.domain.service;

import com.smartshopai.sessioncache.domain.entity.UserSession;
import com.smartshopai.sessioncache.domain.repository.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of SessionService
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final UserSessionRepository userSessionRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    
    private static final String SESSION_CACHE_PREFIX = "session:";
    private static final String USER_SESSIONS_PREFIX = "user_sessions:";
    private static final int SESSION_TIMEOUT_MINUTES = 30;

    @Override
    public UserSession createSession(String userId, String sessionId, String userAgent, String ipAddress) {
        log.info("Creating new session for user: {} with session ID: {}", userId, sessionId);
        
        UserSession session = UserSession.builder()
                .sessionId(sessionId)
                .userId(userId)
                .userAgent(userAgent)
                .ipAddress(ipAddress)
                .status("ACTIVE")
                .startTime(LocalDateTime.now())
                .lastActivityTime(LocalDateTime.now())
                .pageViews(0)
                .clicks(0)
                .searches(0)
                .visitedPages(new HashSet<>())
                .searchedTerms(new HashSet<>())
                .viewedProducts(new HashSet<>())
                .sessionData(new HashMap<>())
                .cookies(new HashMap<>())
                .aiContext(new HashMap<>())
                .recommendations(new HashMap<>())
                .userPreferences(new HashMap<>())
                .analyticsData(new HashMap<>())
                .metrics(new HashMap<>())
                .isAuthenticated(false)
                .build();
        
        UserSession savedSession = userSessionRepository.save(session);
        
        // Cache the session in Redis
        String cacheKey = SESSION_CACHE_PREFIX + sessionId;
        redisTemplate.opsForValue().set(cacheKey, savedSession, SESSION_TIMEOUT_MINUTES, TimeUnit.MINUTES);
        
        // Add to user sessions set
        String userSessionsKey = USER_SESSIONS_PREFIX + userId;
        redisTemplate.opsForSet().add(userSessionsKey, sessionId);
        
        log.debug("Session created successfully: {}", sessionId);
        return savedSession;
    }

    @Override
    public Optional<UserSession> getSession(String sessionId) {
        log.debug("Getting session: {}", sessionId);
        
        // Try Redis cache first
        String cacheKey = SESSION_CACHE_PREFIX + sessionId;
        UserSession cachedSession = (UserSession) redisTemplate.opsForValue().get(cacheKey);
        
        if (cachedSession != null) {
            return Optional.of(cachedSession);
        }
        
        // Fallback to database
        Optional<UserSession> session = userSessionRepository.findBySessionId(sessionId);
        if (session.isPresent()) {
            // Cache the session
            redisTemplate.opsForValue().set(cacheKey, session.get(), SESSION_TIMEOUT_MINUTES, TimeUnit.MINUTES);
        }
        
        return session;
    }

    @Override
    public UserSession updateSessionActivity(String sessionId) {
        log.debug("Updating session activity: {}", sessionId);
        
        Optional<UserSession> sessionOpt = getSession(sessionId);
        if (sessionOpt.isEmpty()) {
            throw new com.smartshopai.common.exception.NotFoundException("Session not found: " + sessionId);
        }
        
        UserSession session = sessionOpt.get();
        session.updateLastActivity();
        
        UserSession updatedSession = userSessionRepository.save(session);
        
        // Update cache
        String cacheKey = SESSION_CACHE_PREFIX + sessionId;
        redisTemplate.opsForValue().set(cacheKey, updatedSession, SESSION_TIMEOUT_MINUTES, TimeUnit.MINUTES);
        
        return updatedSession;
    }

    @Override
    public UserSession updateSessionData(String sessionId, Map<String, Object> sessionData) {
        log.debug("Updating session data: {}", sessionId);
        
        Optional<UserSession> sessionOpt = getSession(sessionId);
        if (sessionOpt.isEmpty()) {
            throw new com.smartshopai.common.exception.NotFoundException("Session not found: " + sessionId);
        }
        
        UserSession session = sessionOpt.get();
        session.setSessionData(sessionData);
        session.preUpdate();
        
        UserSession updatedSession = userSessionRepository.save(session);
        
        // Update cache
        String cacheKey = SESSION_CACHE_PREFIX + sessionId;
        redisTemplate.opsForValue().set(cacheKey, updatedSession, SESSION_TIMEOUT_MINUTES, TimeUnit.MINUTES);
        
        return updatedSession;
    }

    @Override
    public UserSession updateSessionContext(String sessionId, String currentPage, String referrer) {
        log.debug("Updating session context: {} - page: {}", sessionId, currentPage);
        
        Optional<UserSession> sessionOpt = getSession(sessionId);
        if (sessionOpt.isEmpty()) {
            throw new com.smartshopai.common.exception.NotFoundException("Session not found: " + sessionId);
        }
        
        UserSession session = sessionOpt.get();
        session.setCurrentPage(currentPage);
        session.setReferrer(referrer);
        session.updateLastActivity();
        
        UserSession updatedSession = userSessionRepository.save(session);
        
        // Update cache
        String cacheKey = SESSION_CACHE_PREFIX + sessionId;
        redisTemplate.opsForValue().set(cacheKey, updatedSession, SESSION_TIMEOUT_MINUTES, TimeUnit.MINUTES);
        
        return updatedSession;
    }

    @Override
    public UserSession updateAIContext(String sessionId, Map<String, Object> aiContext) {
        log.debug("Updating AI context for session: {}", sessionId);
        
        Optional<UserSession> sessionOpt = getSession(sessionId);
        if (sessionOpt.isEmpty()) {
            throw new com.smartshopai.common.exception.NotFoundException("Session not found: " + sessionId);
        }
        
        UserSession session = sessionOpt.get();
        session.setAiContext(aiContext);
        session.preUpdate();
        
        UserSession updatedSession = userSessionRepository.save(session);
        
        // Update cache
        String cacheKey = SESSION_CACHE_PREFIX + sessionId;
        redisTemplate.opsForValue().set(cacheKey, updatedSession, SESSION_TIMEOUT_MINUTES, TimeUnit.MINUTES);
        
        return updatedSession;
    }

    @Override
    public UserSession updateRecommendations(String sessionId, Map<String, Object> recommendations) {
        log.debug("Updating recommendations for session: {}", sessionId);
        
        Optional<UserSession> sessionOpt = getSession(sessionId);
        if (sessionOpt.isEmpty()) {
            throw new com.smartshopai.common.exception.NotFoundException("Session not found: " + sessionId);
        }
        
        UserSession session = sessionOpt.get();
        session.setRecommendations(recommendations);
        session.preUpdate();
        
        UserSession updatedSession = userSessionRepository.save(session);
        
        // Update cache
        String cacheKey = SESSION_CACHE_PREFIX + sessionId;
        redisTemplate.opsForValue().set(cacheKey, updatedSession, SESSION_TIMEOUT_MINUTES, TimeUnit.MINUTES);
        
        return updatedSession;
    }

    @Override
    public UserSession updateUserPreferences(String sessionId, Map<String, Object> preferences) {
        log.debug("Updating user preferences for session: {}", sessionId);
        
        Optional<UserSession> sessionOpt = getSession(sessionId);
        if (sessionOpt.isEmpty()) {
            throw new com.smartshopai.common.exception.NotFoundException("Session not found: " + sessionId);
        }
        
        UserSession session = sessionOpt.get();
        session.setUserPreferences(preferences);
        session.preUpdate();
        
        UserSession updatedSession = userSessionRepository.save(session);
        
        // Update cache
        String cacheKey = SESSION_CACHE_PREFIX + sessionId;
        redisTemplate.opsForValue().set(cacheKey, updatedSession, SESSION_TIMEOUT_MINUTES, TimeUnit.MINUTES);
        
        return updatedSession;
    }

    @Override
    public UserSession trackPageView(String sessionId, String pageUrl) {
        log.debug("Tracking page view for session: {} - page: {}", sessionId, pageUrl);
        
        Optional<UserSession> sessionOpt = getSession(sessionId);
        if (sessionOpt.isEmpty()) {
            throw new com.smartshopai.common.exception.NotFoundException("Session not found: " + sessionId);
        }
        
        UserSession session = sessionOpt.get();
        session.incrementPageViews();
        session.getVisitedPages().add(pageUrl);
        session.setCurrentPage(pageUrl);
        session.updateLastActivity();
        
        UserSession updatedSession = userSessionRepository.save(session);
        
        // Update cache
        String cacheKey = SESSION_CACHE_PREFIX + sessionId;
        redisTemplate.opsForValue().set(cacheKey, updatedSession, SESSION_TIMEOUT_MINUTES, TimeUnit.MINUTES);
        
        return updatedSession;
    }

    @Override
    public UserSession trackClick(String sessionId, String elementId) {
        log.debug("Tracking click for session: {} - element: {}", sessionId, elementId);
        
        Optional<UserSession> sessionOpt = getSession(sessionId);
        if (sessionOpt.isEmpty()) {
            throw new com.smartshopai.common.exception.NotFoundException("Session not found: " + sessionId);
        }
        
        UserSession session = sessionOpt.get();
        session.incrementClicks();
        session.updateLastActivity();
        
        UserSession updatedSession = userSessionRepository.save(session);
        
        // Update cache
        String cacheKey = SESSION_CACHE_PREFIX + sessionId;
        redisTemplate.opsForValue().set(cacheKey, updatedSession, SESSION_TIMEOUT_MINUTES, TimeUnit.MINUTES);
        
        return updatedSession;
    }

    @Override
    public UserSession trackSearch(String sessionId, String searchTerm) {
        log.debug("Tracking search for session: {} - term: {}", sessionId, searchTerm);
        
        Optional<UserSession> sessionOpt = getSession(sessionId);
        if (sessionOpt.isEmpty()) {
            throw new com.smartshopai.common.exception.NotFoundException("Session not found: " + sessionId);
        }
        
        UserSession session = sessionOpt.get();
        session.incrementSearches();
        session.getSearchedTerms().add(searchTerm);
        session.updateLastActivity();
        
        UserSession updatedSession = userSessionRepository.save(session);
        
        // Update cache
        String cacheKey = SESSION_CACHE_PREFIX + sessionId;
        redisTemplate.opsForValue().set(cacheKey, updatedSession, SESSION_TIMEOUT_MINUTES, TimeUnit.MINUTES);
        
        return updatedSession;
    }

    @Override
    public UserSession trackProductView(String sessionId, String productId) {
        log.debug("Tracking product view for session: {} - product: {}", sessionId, productId);
        
        Optional<UserSession> sessionOpt = getSession(sessionId);
        if (sessionOpt.isEmpty()) {
            throw new com.smartshopai.common.exception.NotFoundException("Session not found: " + sessionId);
        }
        
        UserSession session = sessionOpt.get();
        session.getViewedProducts().add(productId);
        session.updateLastActivity();
        
        UserSession updatedSession = userSessionRepository.save(session);
        
        // Update cache
        String cacheKey = SESSION_CACHE_PREFIX + sessionId;
        redisTemplate.opsForValue().set(cacheKey, updatedSession, SESSION_TIMEOUT_MINUTES, TimeUnit.MINUTES);
        
        return updatedSession;
    }

    @Override
    public UserSession setAuthenticationTokens(String sessionId, String authToken, String refreshToken, LocalDateTime expiryTime) {
        log.debug("Setting authentication tokens for session: {}", sessionId);
        
        Optional<UserSession> sessionOpt = getSession(sessionId);
        if (sessionOpt.isEmpty()) {
            throw new com.smartshopai.common.exception.NotFoundException("Session not found: " + sessionId);
        }
        
        UserSession session = sessionOpt.get();
        session.setAuthenticationToken(authToken);
        session.setRefreshToken(refreshToken);
        session.setTokenExpiryTime(expiryTime);
        session.setIsAuthenticated(true);
        session.preUpdate();
        
        UserSession updatedSession = userSessionRepository.save(session);
        
        // Update cache
        String cacheKey = SESSION_CACHE_PREFIX + sessionId;
        redisTemplate.opsForValue().set(cacheKey, updatedSession, SESSION_TIMEOUT_MINUTES, TimeUnit.MINUTES);
        
        return updatedSession;
    }

    @Override
    public boolean validateAuthenticationToken(String token) {
        log.debug("Validating authentication token");
        
        Optional<UserSession> session = userSessionRepository.findByAuthenticationToken(token);
        if (session.isEmpty()) {
            return false;
        }
        
        UserSession userSession = session.get();
        if (userSession.getTokenExpiryTime() == null || 
            LocalDateTime.now().isAfter(userSession.getTokenExpiryTime())) {
            return false;
        }
        
        return true;
    }

    @Override
    public UserSession refreshAuthenticationToken(String sessionId, String newToken, LocalDateTime expiryTime) {
        log.debug("Refreshing authentication token for session: {}", sessionId);
        
        Optional<UserSession> sessionOpt = getSession(sessionId);
        if (sessionOpt.isEmpty()) {
            throw new RuntimeException("Session not found: " + sessionId);
        }
        
        UserSession session = sessionOpt.get();
        session.setAuthenticationToken(newToken);
        session.setTokenExpiryTime(expiryTime);
        session.preUpdate();
        
        UserSession updatedSession = userSessionRepository.save(session);
        
        // Update cache
        String cacheKey = SESSION_CACHE_PREFIX + sessionId;
        redisTemplate.opsForValue().set(cacheKey, updatedSession, SESSION_TIMEOUT_MINUTES, TimeUnit.MINUTES);
        
        return updatedSession;
    }

    @Override
    public UserSession endSession(String sessionId) {
        log.info("Ending session: {}", sessionId);
        
        Optional<UserSession> sessionOpt = getSession(sessionId);
        if (sessionOpt.isEmpty()) {
            throw new RuntimeException("Session not found: " + sessionId);
        }
        
        UserSession session = sessionOpt.get();
        session.setStatus("TERMINATED");
        session.setEndTime(LocalDateTime.now());
        session.setDurationSeconds(ChronoUnit.SECONDS.between(session.getStartTime(), session.getEndTime()));
        session.preUpdate();
        
        UserSession updatedSession = userSessionRepository.save(session);
        
        // Remove from cache
        String cacheKey = SESSION_CACHE_PREFIX + sessionId;
        redisTemplate.delete(cacheKey);
        
        // Remove from user sessions set
        String userSessionsKey = USER_SESSIONS_PREFIX + session.getUserId();
        redisTemplate.opsForSet().remove(userSessionsKey, sessionId);
        
        return updatedSession;
    }

    @Override
    public List<UserSession> getActiveSessionsByUserId(String userId) {
        log.debug("Getting active sessions for user: {}", userId);
        return userSessionRepository.findActiveSessionsByUserId(userId);
    }

    @Override
    public List<UserSession> getAllActiveSessions() {
        log.debug("Getting all active sessions");
        return userSessionRepository.findByStatus("ACTIVE");
    }

    @Override
    public Map<String, Object> getSessionStatistics() {
        log.debug("Getting session statistics");
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalActiveSessions", userSessionRepository.countActiveSessions());
        stats.put("totalSessions", userSessionRepository.count());
        stats.put("sessionsByDeviceType", getSessionsByDeviceType("DESKTOP").size());
        stats.put("mobileSessions", getSessionsByDeviceType("MOBILE").size());
        stats.put("tabletSessions", getSessionsByDeviceType("TABLET").size());
        
        return stats;
    }

    @Override
    public Map<String, Object> getUserSessionStatistics(String userId) {
        log.debug("Getting session statistics for user: {}", userId);
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("activeSessions", userSessionRepository.countActiveSessionsByUserId(userId));
        stats.put("totalSessions", userSessionRepository.findByUserId(userId).size());
        
        return stats;
    }

    @Override
    @Scheduled(fixedRate = 300000) // Run every 5 minutes
    public void cleanupExpiredSessions() {
        log.info("Cleaning up expired sessions");
        
        LocalDateTime expiryTime = LocalDateTime.now().minusMinutes(SESSION_TIMEOUT_MINUTES);
        List<UserSession> expiredSessions = userSessionRepository.findExpiredActiveSessions(expiryTime);
        
        for (UserSession session : expiredSessions) {
            session.setStatus("EXPIRED");
            session.setEndTime(LocalDateTime.now());
            session.setDurationSeconds(ChronoUnit.SECONDS.between(session.getStartTime(), session.getEndTime()));
            userSessionRepository.save(session);
            
            // Remove from cache
            String cacheKey = SESSION_CACHE_PREFIX + session.getSessionId();
            redisTemplate.delete(cacheKey);
        }
        
        log.info("Cleaned up {} expired sessions", expiredSessions.size());
    }

    @Override
    public List<UserSession> getSessionsByTimeRange(LocalDateTime start, LocalDateTime end) {
        log.debug("Getting sessions by time range: {} to {}", start, end);
        return userSessionRepository.findSessionsByActivityTimeRange(start, end);
    }

    @Override
    public List<UserSession> getSessionsByDeviceType(String deviceType) {
        log.debug("Getting sessions by device type: {}", deviceType);
        return userSessionRepository.findActiveSessionsByDeviceType(deviceType);
    }

    @Override
    public List<UserSession> getSessionsByBrowser(String browser) {
        log.debug("Getting sessions by browser: {}", browser);
        return userSessionRepository.findActiveSessionsByBrowser(browser);
    }

    @Override
    public List<UserSession> getSessionsByOperatingSystem(String operatingSystem) {
        log.debug("Getting sessions by operating system: {}", operatingSystem);
        return userSessionRepository.findActiveSessionsByOperatingSystem(operatingSystem);
    }

    @Override
    public List<UserSession> getSessionsByIpAddress(String ipAddress) {
        log.debug("Getting sessions by IP address: {}", ipAddress);
        return userSessionRepository.findActiveSessionsByIpAddress(ipAddress);
    }

    @Override
    public byte[] exportSessionData(String format, Map<String, Object> filters) {
        log.info("Exporting session data in format: {}", format);
        
        // This is a simplified implementation
        // In a real implementation, you would generate the actual export
        String exportData = "Session export data in " + format + " format";
        return exportData.getBytes();
    }

    @Override
    public Map<String, Object> getRealTimeSessionMetrics() {
        log.debug("Getting real-time session metrics");
        
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("activeSessions", userSessionRepository.countActiveSessions());
        metrics.put("totalSessionsToday", userSessionRepository.findByStartTimeBetween(
            LocalDateTime.now().withHour(0).withMinute(0).withSecond(0),
            LocalDateTime.now()
        ).size());
        metrics.put("averageSessionDuration", 1800.0); // Mock value
        metrics.put("sessionsByDevice", Map.of(
            "DESKTOP", getSessionsByDeviceType("DESKTOP").size(),
            "MOBILE", getSessionsByDeviceType("MOBILE").size(),
            "TABLET", getSessionsByDeviceType("TABLET").size()
        ));
        
        return metrics;
    }
}
