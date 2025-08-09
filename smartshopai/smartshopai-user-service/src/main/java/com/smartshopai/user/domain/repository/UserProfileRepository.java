package com.smartshopai.user.domain.repository;

import com.smartshopai.user.domain.entity.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for UserProfile entity
 */
@Repository
public interface UserProfileRepository extends MongoRepository<UserProfile, String> {
    
    /**
     * Find user profile by user ID
     */
    Optional<UserProfile> findByUserId(String userId);
    
    /**
     * Check if profile exists by user ID
     */
    boolean existsByUserId(String userId);
    
    /**
     * Find profiles by preferred categories
     */
    @Query("{'preferredCategories': {$in: ?0}}")
    List<UserProfile> findByPreferredCategoriesIn(List<String> categories);
    
    /**
     * Find profiles by max budget less than or equal to
     */
    @Query("{'maxBudget': {$lte: ?0}}")
    List<UserProfile> findByMaxBudgetLessThanEqual(Double maxBudget);
    
    /**
     * Find profiles by preferred categories and max budget
     */
    @Query("{'preferredCategories': {$in: ?0}, 'maxBudget': {$lte: ?1}}")
    List<UserProfile> findByPreferredCategoriesInAndMaxBudgetLessThanEqual(List<String> categories, Double maxBudget);
    
    /**
     * Find completed profiles
     */
    List<UserProfile> findByProfileCompletedTrue();
    
    /**
     * Find profiles by notification preferences
     */
    List<UserProfile> findByReceiveNotificationsTrue();
    
    /**
     * Find profiles by email alert preferences
     */
    List<UserProfile> findByReceiveEmailAlertsTrue();
    
    /**
     * Find profiles by SMS alert preferences
     */
    List<UserProfile> findByReceiveSmsAlertsTrue();
    
    /**
     * Find profiles by push notification preferences
     */
    List<UserProfile> findByReceivePushNotificationsTrue();
}
