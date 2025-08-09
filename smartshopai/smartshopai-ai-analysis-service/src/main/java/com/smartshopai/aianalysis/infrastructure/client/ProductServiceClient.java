package com.smartshopai.aianalysis.infrastructure.client;

import com.smartshopai.aianalysis.infrastructure.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "smartshopai-product-service")
public interface ProductServiceClient {

    @GetMapping("/api/v1/products/{id}")
    Product getProductById(@PathVariable("id") String id);

    @PutMapping("/api/v1/products/{productId}/link-analysis")
    void linkAnalysisToProduct(@PathVariable("productId") String productId, @RequestBody String analysisId);
}
