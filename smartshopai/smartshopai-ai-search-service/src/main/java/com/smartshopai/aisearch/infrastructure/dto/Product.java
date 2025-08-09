package com.smartshopai.aisearch.infrastructure.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class Product {
    private String id;
    private String name;
    private String description;
    private BigDecimal currentPrice;
    private String currency;
    private String mainImageUrl;
}
