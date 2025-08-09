# Multi-stage build for SmartShopAI
FROM openjdk:21-jdk-slim as builder

# Set working directory
WORKDIR /app

# Copy gradle files
COPY smartshopai/gradle/ gradle/
COPY smartshopai/gradlew smartshopai/gradlew.bat smartshopai/build.gradle smartshopai/settings.gradle ./

# Copy source code
COPY smartshopai/ ./smartshopai/

# Make gradlew executable
RUN chmod +x gradlew

# Build the application
RUN ./gradlew build -x test

# Runtime stage
FROM openjdk:21-jre-slim

# Set working directory
WORKDIR /app

# Copy built artifacts from builder stage
COPY --from=builder /app/smartshopai/smartshopai-discovery/build/libs/*.jar discovery-service.jar
COPY --from=builder /app/smartshopai/smartshopai-gateway/build/libs/*.jar gateway-service.jar
COPY --from=builder /app/smartshopai/smartshopai-user-service/build/libs/*.jar user-service.jar
COPY --from=builder /app/smartshopai/smartshopai-product-service/build/libs/*.jar product-service.jar
COPY --from=builder /app/smartshopai/smartshopai-ai-analysis-service/build/libs/*.jar ai-analysis-service.jar
COPY --from=builder /app/smartshopai/smartshopai-ai-recommendation-service/build/libs/*.jar ai-recommendation-service.jar
COPY --from=builder /app/smartshopai/smartshopai-ai-search-service/build/libs/*.jar ai-search-service.jar
COPY --from=builder /app/smartshopai/smartshopai-notification-service/build/libs/*.jar notification-service.jar
COPY --from=builder /app/smartshopai/smartshopai-monitoring/build/libs/*.jar monitoring-service.jar
COPY --from=builder /app/smartshopai/smartshopai-business-intelligence-service/build/libs/*.jar business-intelligence-service.jar
COPY --from=builder /app/smartshopai/smartshopai-cache-service/build/libs/*.jar cache-service.jar

# Create startup script
RUN echo '#!/bin/bash\n\
case "$1" in\n\
  "discovery")\n\
    java -jar discovery-service.jar\n\
    ;;\n\
  "gateway")\n\
    java -jar gateway-service.jar\n\
    ;;\n\
  "user")\n\
    java -jar user-service.jar\n\
    ;;\n\
  "product")\n\
    java -jar product-service.jar\n\
    ;;\n\
  "ai-analysis")\n\
    java -jar ai-analysis-service.jar\n\
    ;;\n\
  "ai-recommendation")\n\
    java -jar ai-recommendation-service.jar\n\
    ;;\n\
  "ai-search")\n\
    java -jar ai-search-service.jar\n\
    ;;\n\
  "notification")\n\
    java -jar notification-service.jar\n\
    ;;\n\
  "monitoring")\n\
    java -jar monitoring-service.jar\n\
    ;;\n\
  "business-intelligence")\n\
    java -jar business-intelligence-service.jar\n\
    ;;\n\
  "cache")\n\
    java -jar cache-service.jar\n\
    ;;\n\
  *)\n\
    echo "Usage: $0 {discovery|gateway|user|product|ai-analysis|ai-recommendation|ai-search|notification|monitoring|business-intelligence|cache}"\n\
    exit 1\n\
    ;;\n\
esac' > /app/start.sh && chmod +x /app/start.sh

# Expose ports
EXPOSE 8761 8080 8081 8082 8083 8084 8085 8086 8087 8088

# Set entrypoint
ENTRYPOINT ["/app/start.sh"]
