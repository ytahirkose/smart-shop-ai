package com.smartshopai.notification.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.Map;

/**
 * Request DTO for sending SMS notifications
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendSmsRequest {

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format")
    private String to;

    @NotBlank(message = "Message is required")
    private String message;

    private String from;
    private Map<String, Object> data;
    private String templateName;
}
