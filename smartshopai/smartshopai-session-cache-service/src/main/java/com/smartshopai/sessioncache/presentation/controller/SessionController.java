package com.smartshopai.sessioncache.presentation.controller;

import com.smartshopai.common.dto.BaseResponse;
import com.smartshopai.sessioncache.application.dto.request.CreateSessionRequest;
import com.smartshopai.sessioncache.application.dto.request.UpdateSessionRequest;
import com.smartshopai.sessioncache.application.dto.request.TrackActivityRequest;
import com.smartshopai.sessioncache.application.dto.response.UserSessionResponse;
import com.smartshopai.sessioncache.application.service.SessionApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for session management operations
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/sessions")
@RequiredArgsConstructor
@Tag(name = "Session Management", description = "Session management and tracking operations")
public class SessionController {

    private final SessionApplicationService sessionApplicationService;

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Create session", description = "Creates a new user session")
    public ResponseEntity<BaseResponse<UserSessionResponse>> createSession(@Valid @RequestBody CreateSessionRequest request) {
        log.info("Creating new session for user: {} with session ID: {}", request.getUserId(), request.getSessionId());
        
        UserSessionResponse response = sessionApplicationService.createSession(request);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(response, "Session created successfully"));
    }

    @GetMapping("/{sessionId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get session", description = "Retrieves session information by session ID")
    public ResponseEntity<BaseResponse<UserSessionResponse>> getSession(@PathVariable String sessionId) {
        log.debug("Getting session: {}", sessionId);
        
        Optional<UserSessionResponse> response = sessionApplicationService.getSession(sessionId);
        
        if (response.isPresent()) {
            return ResponseEntity.ok(BaseResponse.success(response.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{sessionId}/activity")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Update session activity", description = "Updates session activity timestamp")
    public ResponseEntity<BaseResponse<UserSessionResponse>> updateSessionActivity(@PathVariable String sessionId) {
        log.debug("Updating session activity: {}", sessionId);
        
        UserSessionResponse response = sessionApplicationService.updateSessionActivity(sessionId);
        
        return ResponseEntity.ok(BaseResponse.success(response, "Session activity updated successfully"));
    }

    @PutMapping("/{sessionId}/data")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Update session data", description = "Updates session data")
    public ResponseEntity<BaseResponse<UserSessionResponse>> updateSessionData(
            @PathVariable String sessionId,
            @Valid @RequestBody UpdateSessionRequest request) {
        log.debug("Updating session data: {}", sessionId);
        
        request.setSessionId(sessionId);
        UserSessionResponse response = sessionApplicationService.updateSessionData(request);
        
        return ResponseEntity.ok(BaseResponse.success(response, "Session data updated successfully"));
    }

    @PutMapping("/{sessionId}/context")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Update session context", description = "Updates session context (current page, referrer)")
    public ResponseEntity<BaseResponse<UserSessionResponse>> updateSessionContext(
            @PathVariable String sessionId,
            @RequestParam String currentPage,
            @RequestParam(required = false) String referrer) {
        log.debug("Updating session context: {} - page: {}", sessionId, currentPage);
        
        UserSessionResponse response = sessionApplicationService.updateSessionContext(sessionId, currentPage, referrer);
        
        return ResponseEntity.ok(BaseResponse.success(response, "Session context updated successfully"));
    }

    @PutMapping("/{sessionId}/ai-context")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Update AI context", description = "Updates AI context for the session")
    public ResponseEntity<BaseResponse<UserSessionResponse>> updateAIContext(
            @PathVariable String sessionId,
            @RequestBody Map<String, Object> aiContext) {
        log.debug("Updating AI context for session: {}", sessionId);
        
        UserSessionResponse response = sessionApplicationService.updateAIContext(sessionId, aiContext);
        
        return ResponseEntity.ok(BaseResponse.success(response, "AI context updated successfully"));
    }

    @PutMapping("/{sessionId}/recommendations")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Update recommendations", description = "Updates recommendations for the session")
    public ResponseEntity<BaseResponse<UserSessionResponse>> updateRecommendations(
            @PathVariable String sessionId,
            @RequestBody Map<String, Object> recommendations) {
        log.debug("Updating recommendations for session: {}", sessionId);
        
        UserSessionResponse response = sessionApplicationService.updateRecommendations(sessionId, recommendations);
        
        return ResponseEntity.ok(BaseResponse.success(response, "Recommendations updated successfully"));
    }

    @PutMapping("/{sessionId}/preferences")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Update user preferences", description = "Updates user preferences for the session")
    public ResponseEntity<BaseResponse<UserSessionResponse>> updateUserPreferences(
            @PathVariable String sessionId,
            @RequestBody Map<String, Object> preferences) {
        log.debug("Updating user preferences for session: {}", sessionId);
        
        UserSessionResponse response = sessionApplicationService.updateUserPreferences(sessionId, preferences);
        
        return ResponseEntity.ok(BaseResponse.success(response, "User preferences updated successfully"));
    }

    @PostMapping("/{sessionId}/track")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Track activity", description = "Tracks user activity in the session")
    public ResponseEntity<BaseResponse<UserSessionResponse>> trackActivity(
            @PathVariable String sessionId,
            @Valid @RequestBody TrackActivityRequest request) {
        log.debug("Tracking activity for session: {} - type: {}", sessionId, request.getActivityType());
        
        request.setSessionId(sessionId);
        UserSessionResponse response = sessionApplicationService.trackActivity(request);
        
        return ResponseEntity.ok(BaseResponse.success(response, "Activity tracked successfully"));
    }

    @PostMapping("/{sessionId}/auth-tokens")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Set authentication tokens", description = "Sets authentication tokens for the session")
    public ResponseEntity<BaseResponse<UserSessionResponse>> setAuthenticationTokens(
            @PathVariable String sessionId,
            @RequestParam String authToken,
            @RequestParam String refreshToken,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime expiryTime) {
        log.debug("Setting authentication tokens for session: {}", sessionId);
        
        UserSessionResponse response = sessionApplicationService.setAuthenticationTokens(sessionId, authToken, refreshToken, expiryTime);
        
        return ResponseEntity.ok(BaseResponse.success(response, "Authentication tokens set successfully"));
    }

    @PostMapping("/validate-token")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Validate authentication token", description = "Validates an authentication token")
    public ResponseEntity<BaseResponse<Boolean>> validateAuthenticationToken(@RequestParam String token) {
        log.debug("Validating authentication token");
        
        boolean isValid = sessionApplicationService.validateAuthenticationToken(token);
        
        return ResponseEntity.ok(BaseResponse.success(isValid, "Token validation completed"));
    }

    @PutMapping("/{sessionId}/refresh-token")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Refresh authentication token", description = "Refreshes the authentication token for the session")
    public ResponseEntity<BaseResponse<UserSessionResponse>> refreshAuthenticationToken(
            @PathVariable String sessionId,
            @RequestParam String newToken,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime expiryTime) {
        log.debug("Refreshing authentication token for session: {}", sessionId);
        
        UserSessionResponse response = sessionApplicationService.refreshAuthenticationToken(sessionId, newToken, expiryTime);
        
        return ResponseEntity.ok(BaseResponse.success(response, "Authentication token refreshed successfully"));
    }

    @DeleteMapping("/{sessionId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "End session", description = "Ends the session")
    public ResponseEntity<BaseResponse<UserSessionResponse>> endSession(@PathVariable String sessionId) {
        log.info("Ending session: {}", sessionId);
        
        UserSessionResponse response = sessionApplicationService.endSession(sessionId);
        
        return ResponseEntity.ok(BaseResponse.success(response, "Session ended successfully"));
    }

    @GetMapping("/user/{userId}/active")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get active sessions by user", description = "Retrieves active sessions for a specific user")
    public ResponseEntity<BaseResponse<List<UserSessionResponse>>> getActiveSessionsByUserId(@PathVariable String userId) {
        log.debug("Getting active sessions for user: {}", userId);
        
        List<UserSessionResponse> responses = sessionApplicationService.getActiveSessionsByUserId(userId);
        
        return ResponseEntity.ok(BaseResponse.success(responses));
    }

    @GetMapping("/active")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get all active sessions", description = "Retrieves all active sessions")
    public ResponseEntity<BaseResponse<List<UserSessionResponse>>> getAllActiveSessions() {
        log.debug("Getting all active sessions");
        
        List<UserSessionResponse> responses = sessionApplicationService.getAllActiveSessions();
        
        return ResponseEntity.ok(BaseResponse.success(responses));
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get session statistics", description = "Retrieves session statistics")
    public ResponseEntity<BaseResponse<Map<String, Object>>> getSessionStatistics() {
        log.debug("Getting session statistics");
        
        Map<String, Object> statistics = sessionApplicationService.getSessionStatistics();
        
        return ResponseEntity.ok(BaseResponse.success(statistics));
    }

    @GetMapping("/user/{userId}/statistics")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get user session statistics", description = "Retrieves session statistics for a specific user")
    public ResponseEntity<BaseResponse<Map<String, Object>>> getUserSessionStatistics(@PathVariable String userId) {
        log.debug("Getting session statistics for user: {}", userId);
        
        Map<String, Object> statistics = sessionApplicationService.getUserSessionStatistics(userId);
        
        return ResponseEntity.ok(BaseResponse.success(statistics));
    }

    @GetMapping("/time-range")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get sessions by time range", description = "Retrieves sessions within a time range")
    public ResponseEntity<BaseResponse<List<UserSessionResponse>>> getSessionsByTimeRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        log.debug("Getting sessions by time range: {} to {}", start, end);
        
        List<UserSessionResponse> responses = sessionApplicationService.getSessionsByTimeRange(start, end);
        
        return ResponseEntity.ok(BaseResponse.success(responses));
    }

    @GetMapping("/device-type/{deviceType}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get sessions by device type", description = "Retrieves sessions by device type")
    public ResponseEntity<BaseResponse<List<UserSessionResponse>>> getSessionsByDeviceType(@PathVariable String deviceType) {
        log.debug("Getting sessions by device type: {}", deviceType);
        
        List<UserSessionResponse> responses = sessionApplicationService.getSessionsByDeviceType(deviceType);
        
        return ResponseEntity.ok(BaseResponse.success(responses));
    }

    @GetMapping("/browser/{browser}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get sessions by browser", description = "Retrieves sessions by browser")
    public ResponseEntity<BaseResponse<List<UserSessionResponse>>> getSessionsByBrowser(@PathVariable String browser) {
        log.debug("Getting sessions by browser: {}", browser);
        
        List<UserSessionResponse> responses = sessionApplicationService.getSessionsByBrowser(browser);
        
        return ResponseEntity.ok(BaseResponse.success(responses));
    }

    @GetMapping("/os/{operatingSystem}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get sessions by operating system", description = "Retrieves sessions by operating system")
    public ResponseEntity<BaseResponse<List<UserSessionResponse>>> getSessionsByOperatingSystem(@PathVariable String operatingSystem) {
        log.debug("Getting sessions by operating system: {}", operatingSystem);
        
        List<UserSessionResponse> responses = sessionApplicationService.getSessionsByOperatingSystem(operatingSystem);
        
        return ResponseEntity.ok(BaseResponse.success(responses));
    }

    @GetMapping("/ip/{ipAddress}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get sessions by IP address", description = "Retrieves sessions by IP address")
    public ResponseEntity<BaseResponse<List<UserSessionResponse>>> getSessionsByIpAddress(@PathVariable String ipAddress) {
        log.debug("Getting sessions by IP address: {}", ipAddress);
        
        List<UserSessionResponse> responses = sessionApplicationService.getSessionsByIpAddress(ipAddress);
        
        return ResponseEntity.ok(BaseResponse.success(responses));
    }

    @PostMapping("/export")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Export session data", description = "Exports session data in specified format")
    public ResponseEntity<byte[]> exportSessionData(
            @RequestParam String format,
            @RequestBody Map<String, Object> filters) {
        log.info("Exporting session data in format: {}", format);
        
        byte[] data = sessionApplicationService.exportSessionData(format, filters);
        
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=session_export." + format.toLowerCase())
                .body(data);
    }

    @GetMapping("/metrics/real-time")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get real-time session metrics", description = "Retrieves real-time session metrics")
    public ResponseEntity<BaseResponse<Map<String, Object>>> getRealTimeSessionMetrics() {
        log.debug("Getting real-time session metrics");
        
        Map<String, Object> metrics = sessionApplicationService.getRealTimeSessionMetrics();
        
        return ResponseEntity.ok(BaseResponse.success(metrics));
    }

    @GetMapping("/health")
    @Operation(summary = "Session service health check", description = "Checks if session service is healthy")
    public ResponseEntity<BaseResponse<String>> healthCheck() {
        log.debug("Health check requested");
        
        return ResponseEntity.ok(BaseResponse.success("Session Service is healthy"));
    }
}
