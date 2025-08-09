package com.smartshopai.aisearch.infrastructure.client;

import com.smartshopai.aisearch.infrastructure.dto.ProductAnalysis;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "smartshopai-ai-analysis-service")
public interface AnalysisServiceClient {

    @PostMapping("/api/analysis/find-similar")
    List<ProductAnalysis> findSimilarProducts(@RequestBody List<Double> userEmbedding, @RequestParam(name = "topK", defaultValue = "10") int topK);
}
