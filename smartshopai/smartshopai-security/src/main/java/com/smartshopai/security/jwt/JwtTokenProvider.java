package com.smartshopai.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * JWT Token Provider for authentication and authorization
 */
@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret:your-secret-key-here-make-it-long-and-secure-in-production}")
    private String jwtSecret;

    @Value("${jwt.expiration:86400000}")
    private long jwtExpiration;

    @Value("${jwt.refresh-expiration:604800000}")
    private long jwtRefreshExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateTokenFromUsername(String username, Map<String, Object> claims) {
        return Jwts.builder()
                .setSubject(username)
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtRefreshExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            log.error("Error getting username from token: {}", e.getMessage());
            return null;
        }
    }

    public String getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .parseClaimsJws(token)
                    .getBody();
            
            // Try to get userId from claims, fallback to username if not present
            String userId = claims.get("userId", String.class);
            if (userId != null) {
                return userId;
            }
            
            // Fallback to username if userId is not in claims
            return claims.getSubject();
        } catch (Exception e) {
            log.error("Error getting userId from token: {}", e.getMessage());
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    public boolean isRefreshToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .parseClaimsJws(token)
                    .getBody();
            
            // Check if token has no additional claims (indicating it's a refresh token)
            return claims.size() == 3; // subject, issuedAt, expiration
        } catch (Exception e) {
            log.error("Error checking if token is refresh token: {}", e.getMessage());
            return false;
        }
    }

    public Date getExpirationDateFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
        } catch (Exception e) {
            log.error("Error getting expiration date from token: {}", e.getMessage());
            return null;
        }
    }

    public boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration != null && expiration.before(new Date());
    }
}
