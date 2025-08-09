package com.smartshopai.product.domain.repository;

import com.smartshopai.product.domain.entity.ProductSpecifications;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductSpecificationsRepository extends MongoRepository<ProductSpecifications, String> {
    Optional<ProductSpecifications> findByProductId(String productId);
}
