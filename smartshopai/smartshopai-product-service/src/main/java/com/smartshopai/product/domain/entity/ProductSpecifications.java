package com.smartshopai.product.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product_specifications")
public class ProductSpecifications {

    @Id
    private String id;

    private String productId;

    private Map<String, String> specifications;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
