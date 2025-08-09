package com.smartshopai.aianalysis.infrastructure.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String id;
    private String name;
    private String description;
    private BigDecimal currentPrice;
    private String currency;
    private Map<String, String> specifications;
}
