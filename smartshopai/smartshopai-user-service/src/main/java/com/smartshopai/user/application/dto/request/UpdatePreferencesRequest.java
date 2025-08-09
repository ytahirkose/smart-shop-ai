package com.smartshopai.user.application.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class UpdatePreferencesRequest {
    private Double minBudget;
    private Double maxBudget;
    private List<String> preferredCategories;
    private List<String> excludedCategories;
    private List<String> preferredBrands;
    private List<String> excludedBrands;
    private Boolean emailNotifications;
    private Boolean smsNotifications;
    private Boolean pushNotifications;
}
