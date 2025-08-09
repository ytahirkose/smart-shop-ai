package com.smartshopai.product.domain.repository;

import com.smartshopai.product.domain.entity.ProductAnalysis;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAnalysisRepository extends MongoRepository<ProductAnalysis, String> {
}
