package com.smartshopai.sessioncache.domain.repository;

import com.smartshopai.sessioncache.domain.entity.UserSession;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for UserSession entity
 */
@Repository
public interface UserSessionRepository extends MongoRepository<UserSession, String> {

    Optional<UserSession> findBySessionId(String sessionId);
    
    List<UserSession> findByUserId(String userId);
    
    List<UserSession> findByUserIdAndStatus(String userId, String status);
    
    List<UserSession> findByStatus(String status);
    
    List<UserSession> findByLastActivityTimeBefore(LocalDateTime time);
    
    List<UserSession> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("{'userId': ?0, 'lastActivityTime': {$gte: ?1, $lte: ?2}}")
    List<UserSession> findByUserIdAndLastActivityTimeBetween(String userId, LocalDateTime start, LocalDateTime end);
    
    @Query("{'status': 'ACTIVE', 'lastActivityTime': {$lt: ?0}}")
    List<UserSession> findExpiredActiveSessions(LocalDateTime expiryTime);
    
    @Query("{'userId': ?0, 'status': 'ACTIVE'}")
    List<UserSession> findActiveSessionsByUserId(String userId);
    
    @Query(value = "{'status': 'ACTIVE'}", count = true)
    Long countActiveSessions();
    
    @Query(value = "{'userId': ?0, 'status': 'ACTIVE'}", count = true)
    Long countActiveSessionsByUserId(String userId);
    
    @Query("{'lastActivityTime': {$gte: ?0, $lte: ?1}}")
    List<UserSession> findSessionsByActivityTimeRange(LocalDateTime start, LocalDateTime end);
    
    @Query("{'deviceType': ?0, 'status': 'ACTIVE'}")
    List<UserSession> findActiveSessionsByDeviceType(String deviceType);
    
    @Query("{'browser': ?0, 'status': 'ACTIVE'}")
    List<UserSession> findActiveSessionsByBrowser(String browser);
    
    @Query("{'operatingSystem': ?0, 'status': 'ACTIVE'}")
    List<UserSession> findActiveSessionsByOperatingSystem(String operatingSystem);
    
    @Query("{'ipAddress': ?0, 'status': 'ACTIVE'}")
    List<UserSession> findActiveSessionsByIpAddress(String ipAddress);
    
    @Query("{'authenticationToken': ?0}")
    Optional<UserSession> findByAuthenticationToken(String token);
    
    @Query("{'refreshToken': ?0}")
    Optional<UserSession> findByRefreshToken(String token);
    
    @Query(value = "{'status': 'ACTIVE'}", fields = "{'sessionId': 1, 'userId': 1, 'lastActivityTime': 1}")
    List<UserSession> findActiveSessionSummaries();
}
