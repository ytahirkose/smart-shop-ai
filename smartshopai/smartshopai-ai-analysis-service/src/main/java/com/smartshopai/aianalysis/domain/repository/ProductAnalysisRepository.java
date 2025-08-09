package com.smartshopai.aianalysis.domain.repository;

import com.smartshopai.aianalysis.domain.entity.ProductAnalysis;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductAnalysisRepository extends MongoRepository<ProductAnalysis, String> {
    Optional<ProductAnalysis> findByProductId(String productId);
}
