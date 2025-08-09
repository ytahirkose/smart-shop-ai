package com.smartshopai.sessioncache.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO for tracking user activity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackActivityRequest {

    @NotBlank(message = "Session ID is required")
    private String sessionId;

    private String activityType; // PAGE_VIEW, CLICK, SEARCH, PRODUCT_VIEW
    private String activityData; // page URL, element ID, search term, product ID
    private String additionalInfo;
}
