package com.smartshopai.ai.search.domain.repository;

import com.smartshopai.ai.search.domain.entity.SearchResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SearchResultRepository extends MongoRepository<SearchResult, String> {
    
    List<SearchResult> findByUserId(String userId);
    
    List<SearchResult> findByUserIdAndStatus(String userId, String status);
    
    List<SearchResult> findBySearchType(String searchType);
    
    List<SearchResult> findByContext(String context);
    
    List<SearchResult> findByStatus(String status);
    
    List<SearchResult> findByPersonalized(boolean personalized);
    
    List<SearchResult> findByRealTime(boolean realTime);
    
    List<SearchResult> findByAiAnalysisCompleted(boolean completed);
    
    List<SearchResult> findByCreatedAtAfter(LocalDateTime date);
    
    List<SearchResult> findByExpiresAtBefore(LocalDateTime date);
    
    Optional<SearchResult> findBySearchRequestId(String requestId);
    
    Optional<SearchResult> findTopByUserIdOrderByCreatedAtDesc(String userId);
    
    List<SearchResult> findByUserIdAndSearchType(String userId, String searchType);
    
    List<SearchResult> findByUserIdAndPersonalized(String userId, boolean personalized);
}
