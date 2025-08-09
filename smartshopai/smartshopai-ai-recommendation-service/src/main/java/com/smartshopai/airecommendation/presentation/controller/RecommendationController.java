package com.smartshopai.airecommendation.presentation.controller;

import com.smartshopai.airecommendation.domain.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ai/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, String>> getRecommendations(
            @PathVariable String userId,
            @RequestHeader(value = "X-User-Roles", defaultValue = "ROLE_USER") String rolesHeader) {
        
        Set<String> roles = parseRoles(rolesHeader);
        String recommendationText = recommendationService.getPersonalizedRecommendations(userId, roles);
        return ResponseEntity.ok(Map.of("recommendation", recommendationText));
    }

    private Set<String> parseRoles(String rolesHeader) {
        if (rolesHeader == null || rolesHeader.isBlank() || rolesHeader.equals("[]")) {
            return Collections.emptySet();
        }
        // Assuming roles are passed like "[ROLE_USER, ROLE_PREMIUM]"
        return Arrays.stream(rolesHeader.replace("[", "").replace("]", "").split(","))
                .map(String::trim)
                .filter(role -> !role.isBlank())
                .collect(Collectors.toSet());
    }
}
