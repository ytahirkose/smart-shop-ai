package com.smartshopai.aianalysis.presentation.controller;

import com.smartshopai.aianalysis.domain.entity.ProductAnalysis;
import com.smartshopai.aianalysis.domain.service.ProductAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class AnalysisController {

    private final ProductAnalysisService analysisService;

    @PostMapping("/find-similar")
    public ResponseEntity<List<ProductAnalysis>> findSimilarProducts(
            @RequestBody List<Double> userEmbedding,
            @RequestParam(name = "topK", defaultValue = "5") int topK) {
        List<ProductAnalysis> similarProducts = analysisService.findSimilarProducts(userEmbedding, topK);
        return ResponseEntity.ok(similarProducts);
    }
    
    @PostMapping("/analyze/{productId}")
    public ResponseEntity<ProductAnalysis> analyzeProduct(
            @PathVariable String productId,
            @RequestHeader(value = "X-User-Roles", defaultValue = "ROLE_USER") String rolesHeader) {
        
        Set<String> roles = parseRoles(rolesHeader);
        ProductAnalysis analysis = analysisService.analyzeProduct(productId, roles);
        return ResponseEntity.ok(analysis);
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
