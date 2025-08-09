package com.smartshopai.airecommendation.infrastructure.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProductAnalysisDto {
    private String productId;
    private List<Double> embeddings;
}
