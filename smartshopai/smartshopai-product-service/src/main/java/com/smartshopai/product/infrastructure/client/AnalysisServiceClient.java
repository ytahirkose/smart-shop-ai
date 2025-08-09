package com.smartshopai.product.infrastructure.client;

import com.smartshopai.product.application.dto.response.ProductAnalysisResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "smartshopai-ai-analysis-service")
public interface AnalysisServiceClient {

    @PostMapping("/api/analysis/analyze/{productId}")
    ProductAnalysisResponse analyzeProduct(@PathVariable("productId") String productId);

    @PostMapping("/api/analysis/find-similar")
    List<String> findSimilarProductIds(@RequestBody List<Double> embedding, @RequestParam("topK") int topK);
}
