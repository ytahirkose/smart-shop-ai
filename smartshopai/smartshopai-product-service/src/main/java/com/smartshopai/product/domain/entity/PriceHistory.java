package com.smartshopai.product.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "price_histories")
public class PriceHistory {

    @Id
    private String id;

    private String productId;
    
    private String vendor; // e.g., "Amazon", "Trendyol"

    private BigDecimal price;
    
    private String currency;

    private LocalDateTime timestamp;
}
