package com.smartshopai.product.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Response DTO for review data
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {

    private String id;
    private String userId;
    private String userName;
    private Integer rating;
    private String title;
    private String comment;
    private LocalDateTime reviewDate;
    private boolean verified;
    private boolean helpful;
    private Integer helpfulCount;
}
