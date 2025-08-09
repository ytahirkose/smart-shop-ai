package com.smartshopai.aisearch.infrastructure.client;

import com.smartshopai.aisearch.infrastructure.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "smartshopai-product-service")
public interface ProductServiceClient {

    @PostMapping("/api/v1/products/by-ids")
    List<Product> getProductsByIds(@RequestBody List<String> productIds);
}
