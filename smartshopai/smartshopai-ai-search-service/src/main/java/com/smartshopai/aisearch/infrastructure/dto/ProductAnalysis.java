package com.smartshopai.aisearch.infrastructure.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProductAnalysis {
    private String productId;
    private List<Double> embeddings;
}
