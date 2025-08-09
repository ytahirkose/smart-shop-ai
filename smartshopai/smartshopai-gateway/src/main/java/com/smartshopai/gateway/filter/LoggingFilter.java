package com.smartshopai.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Slf4j
public class LoggingFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String correlationId = UUID.randomUUID().toString();
        exchange.getRequest().mutate()
                .header("X-Correlation-ID", correlationId)
                .build();

        log.info("Incoming request: {} {} with correlation ID: {}", 
                exchange.getRequest().getMethod(), 
                exchange.getRequest().getPath(), 
                correlationId);

        return chain.filter(exchange)
                .doFinally(signalType -> 
                    log.info("Request completed with correlation ID: {}", correlationId)
                );
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
