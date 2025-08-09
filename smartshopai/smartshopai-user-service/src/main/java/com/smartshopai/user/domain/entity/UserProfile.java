package com.smartshopai.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_profiles")
public class UserProfile {

    @Id
    private String id;

    // Personal Information
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String profilePictureUrl;

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
