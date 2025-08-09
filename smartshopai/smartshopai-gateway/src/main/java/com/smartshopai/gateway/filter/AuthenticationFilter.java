package com.smartshopai.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.List;

@Component
public class AuthenticationFilter implements GatewayFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // Bypass authentication for public endpoints (e.g., login, register)
        if (request.getURI().getPath().contains("/api/auth")) {
            return chain.filter(exchange);
        }

        // 1. Get Authorization header
        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return onError(exchange, "Authorization header is missing or invalid");
        }

        // 2. Extract and validate token
        try {
            String token = authHeader.substring(7);
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            Claims claims = Jwts.parser()
                                .verifyWith(key)
                                .build()
                                .parseSignedClaims(token)
                                .getPayload();

            // 3. Add user info to request headers for downstream services
            ServerHttpRequest mutatedRequest = request.mutate()
                    .header("X-User-ID", claims.getSubject())
                    .header("X-User-Roles", ((List<String>) claims.get("roles")).toString())
                    .build();
            
            return chain.filter(exchange.mutate().request(mutatedRequest).build());

        } catch (Exception e) {
            return onError(exchange, "Invalid token");
        }
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
