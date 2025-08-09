package com.smartshopai.product.domain.repository;

import com.smartshopai.product.domain.entity.PriceHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceHistoryRepository extends MongoRepository<PriceHistory, String> {
    List<PriceHistory> findByProductIdOrderByTimestampDesc(String productId);
    List<PriceHistory> findByProductIdAndVendorOrderByTimestampDesc(String productId, String vendor);
}
