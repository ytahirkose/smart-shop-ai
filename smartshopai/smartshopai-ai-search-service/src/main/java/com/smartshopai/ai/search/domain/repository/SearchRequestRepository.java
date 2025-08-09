package com.smartshopai.ai.search.domain.repository;

import com.smartshopai.ai.search.domain.entity.SearchRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SearchRequestRepository extends MongoRepository<SearchRequest, String> {
    
    List<SearchRequest> findByUserId(String userId);
    
    List<SearchRequest> findByUserIdAndStatus(String userId, String status);
    
    List<SearchRequest> findBySearchType(String searchType);
    
    List<SearchRequest> findByContext(String context);
    
    List<SearchRequest> findByStatus(String status);
    
    List<SearchRequest> findByAiAnalysisCompleted(boolean completed);
    
    List<SearchRequest> findByCreatedAtAfter(LocalDateTime date);
    
    List<SearchRequest> findByUserIdAndSearchType(String userId, String searchType);
    
    Optional<SearchRequest> findTopByUserIdOrderByCreatedAtDesc(String userId);
    
    boolean existsByUserIdAndSearchTypeAndStatus(String userId, String searchType, String status);
}
