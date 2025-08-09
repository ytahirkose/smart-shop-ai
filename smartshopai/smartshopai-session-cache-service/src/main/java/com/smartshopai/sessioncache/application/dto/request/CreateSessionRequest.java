package com.smartshopai.sessioncache.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

/**
 * Request DTO for creating a new user session
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSessionRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Session ID is required")
    private String sessionId;

    @NotBlank(message = "User agent is required")
    private String userAgent;

    @NotBlank(message = "IP address is required")
    private String ipAddress;

    private String deviceType; // MOBILE, DESKTOP, TABLET
    private String browser;
    private String operatingSystem;
    private String referrer;
    private Map<String, Object> initialData;
    private Map<String, String> cookies;
}
