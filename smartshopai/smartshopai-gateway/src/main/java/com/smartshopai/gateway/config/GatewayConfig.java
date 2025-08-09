package com.smartshopai.gateway.config;

import com.smartshopai.gateway.filter.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthenticationFilter authenticationFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user_service_route", r -> r.path("/api/users/**", "/api/auth/**", "/api/profiles/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://smartshopai-user-service"))
                .route("product_service_route", r -> r.path("/api/products/**", "/api/comparisons/**", "/api/searches/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://smartshopai-product-service"))
                .route("notification_service_route", r -> r.path("/api/notifications/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://smartshopai-notification-service"))
                .route("ai_analysis_service_route", r -> r.path("/api/ai/analysis/**", "/api/analysis/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://smartshopai-ai-analysis-service"))
                .route("ai_recommendation_service_route", r -> r.path("/api/ai/recommendations/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://smartshopai-ai-recommendation-service"))
                .route("ai_search_service_route", r -> r.path("/api/ai/search/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://smartshopai-ai-search-service"))
                .build();
    }
}
