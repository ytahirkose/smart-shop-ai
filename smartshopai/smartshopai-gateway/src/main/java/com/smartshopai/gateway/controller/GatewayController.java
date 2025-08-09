package com.smartshopai.gateway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Gateway controller for health checks and status
 */
@Slf4j
@RestController
@RequestMapping("/api/gateway")
@RequiredArgsConstructor
public class GatewayController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        log.debug("Gateway health check");
        
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "service", "smartshopai-gateway",
            "timestamp", System.currentTimeMillis()
        ));
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> status() {
        log.debug("Gateway status check");
        
        return ResponseEntity.ok(Map.of(
            "status", "RUNNING",
            "version", "1.0.0",
            "uptime", System.currentTimeMillis()
        ));
    }
}
