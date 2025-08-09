package com.smartshopai.notification.infrastructure.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Email service for sending notifications
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    
    @Value("${spring.mail.username}")
    private String fromEmail;
    
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean smtpAuth;
    
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean startTls;

    public void sendEmail(String to, String subject, String content) {
        log.info("Sending email to: {}, subject: {}", to, subject);
        
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            
            mailSender.send(message);
            
            log.info("Email sent successfully to: {}", to);
            
        } catch (Exception e) {
            log.error("Failed to send email to: {}", to, e);
            throw new com.smartshopai.common.exception.BusinessException("Email sending failed", e);
        }
    }

    public void sendEmailWithHtml(String to, String subject, String htmlContent) {
        log.info("Sending HTML email to: {}, subject: {}", to, subject);
        
        try {
            // For HTML emails, you would use MimeMessageHelper
            // This is a simplified version
            sendEmail(to, subject, htmlContent);
            
        } catch (Exception e) {
            log.error("Failed to send HTML email to: {}", to, e);
            throw new com.smartshopai.common.exception.BusinessException("HTML email sending failed", e);
        }
    }

    public boolean isEmailServiceConfigured() {
        return smtpAuth && startTls && fromEmail != null && !fromEmail.isEmpty();
    }
}
