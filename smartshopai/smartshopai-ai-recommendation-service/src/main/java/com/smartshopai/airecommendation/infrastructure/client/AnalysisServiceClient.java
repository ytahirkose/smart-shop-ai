package com.smartshopai.airecommendation.infrastructure.client;

import com.smartshopai.airecommendation.infrastructure.dto.ProductAnalysisDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "smartshopai-ai-analysis-service")
public interface AnalysisServiceClient {

    @PostMapping("/api/analysis/find-similar")
    List<ProductAnalysisDto> findSimilarProducts(@RequestBody List<Double> userEmbedding, @RequestParam("topK") int topK);
}
