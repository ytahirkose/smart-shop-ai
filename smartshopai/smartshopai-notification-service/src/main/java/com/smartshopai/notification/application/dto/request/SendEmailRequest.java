package com.smartshopai.notification.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Map;

/**
 * Request DTO for sending email notifications
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailRequest {

    @NotBlank(message = "Recipient email is required")
    @Email(message = "Invalid email format")
    private String to;

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "Content is required")
    private String content;

    private String templateName;
    private Map<String, Object> templateData;
    private String from;
    private String replyTo;
    private Boolean isHtml;
}
