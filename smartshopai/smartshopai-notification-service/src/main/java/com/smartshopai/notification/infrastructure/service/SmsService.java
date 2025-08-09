package com.smartshopai.notification.infrastructure.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * SMS service for sending notifications
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SmsService {

    @Value("${sms.provider:mock}")
    private String smsProvider;
    
    @Value("${sms.api.key:}")
    private String apiKey;
    
    @Value("${sms.api.secret:}")
    private String apiSecret;

    public void sendSms(String phoneNumber, String message) {
        log.info("Sending SMS to: {}, message: {}", phoneNumber, message);
        
        try {
            switch (smsProvider.toLowerCase()) {
                case "twilio":
                    sendSmsViaTwilio(phoneNumber, message);
                    break;
                case "aws":
                    sendSmsViaAws(phoneNumber, message);
                    break;
                case "mock":
                default:
                    sendSmsViaMock(phoneNumber, message);
                    break;
            }
            
            log.info("SMS sent successfully to: {}", phoneNumber);
            
        } catch (Exception e) {
            log.error("Failed to send SMS to: {}", phoneNumber, e);
            throw new com.smartshopai.common.exception.BusinessException("SMS sending failed", e);
        }
    }

    private void sendSmsViaTwilio(String phoneNumber, String message) {
        log.info("Sending SMS via Twilio to: {}", phoneNumber);
        try {
            // Twilio implementation
            // Twilio.init(apiKey, apiSecret);
            // Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber("+1234567890"), message).create();
            
            // For now, just log the SMS
            log.info("Twilio SMS would be sent to: {} with message: {}", phoneNumber, message);
        } catch (Exception e) {
            log.error("Failed to send SMS via Twilio to: {}", phoneNumber, e);
            throw new com.smartshopai.common.exception.BusinessException("Twilio SMS sending failed", e);
        }
    }

    private void sendSmsViaAws(String phoneNumber, String message) {
        log.info("Sending SMS via AWS SNS to: {}", phoneNumber);
        try {
            // AWS SNS implementation
            // AmazonSNS snsClient = AmazonSNSClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
            // snsClient.publish(new PublishRequest().withPhoneNumber(phoneNumber).withMessage(message));
            
            // For now, just log the SMS
            log.info("AWS SNS SMS would be sent to: {} with message: {}", phoneNumber, message);
        } catch (Exception e) {
            log.error("Failed to send SMS via AWS SNS to: {}", phoneNumber, e);
            throw new com.smartshopai.common.exception.BusinessException("AWS SNS SMS sending failed", e);
        }
    }

    private void sendSmsViaMock(String phoneNumber, String message) {
        log.info("Mock SMS sent to: {} with message: {}", phoneNumber, message);
        // In development/testing, we just log the SMS
    }

    public boolean isSmsServiceConfigured() {
        return apiKey != null && !apiKey.isEmpty() && apiSecret != null && !apiSecret.isEmpty();
    }
}
