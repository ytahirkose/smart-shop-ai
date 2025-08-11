package com.smartshopai.user.domain.repository;

import com.smartshopai.user.domain.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findByUsernameOrEmail(String username, String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    @Query("{'enabled': true, 'roles': ?0}")
    List<User> findByRole(String role);
    
    @Query("{'enabled': true, 'userPreferences.defaultCategory': ?0}")
    List<User> findByPreferredCategory(String category);
    
    @Query("{'enabled': true, 'userPreferences.budgetLimit': {$gte: ?0}}")
    List<User> findByBudgetLimitGreaterThan(Double budget);
    
    @Query("{'enabled': true, 'userPreferences.qualityPreference': ?0}")
    List<User> findByQualityPreference(String qualityPreference);
    
    @Query("{'enabled': true, 'createdAt': {$gte: ?0}}")
    List<User> findActiveUsersCreatedAfter(java.time.LocalDateTime date);
    
    @Query("{'enabled': true, 'lastLoginAt': {$gte: ?0}}")
    List<User> findActiveUsersLoggedInAfter(java.time.LocalDateTime date);
}
