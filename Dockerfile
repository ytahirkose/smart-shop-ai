# Multi-stage build for SmartShopAI
FROM openjdk:21-jdk-slim as builder

# Set working directory
WORKDIR /app

# Copy only files required for dependency resolution
COPY smartshopai/build.gradle smartshopai/settings.gradle ./
COPY smartshopai/gradle/ ./gradle/
COPY smartshopai/gradlew ./

# Download dependencies
RUN ./gradlew dependencies --no-daemon

# Copy the rest of the source code
COPY smartshopai/ ./

# Build the application, skipping tests
RUN ./gradlew build -x test --no-daemon

# Runtime stage
FROM openjdk:21-jre-slim

# Set working directory
WORKDIR /app

# Create a non-root user
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser
USER appuser

# Copy built artifacts from builder stage
ARG SERVICE_NAME
COPY --from=builder /app/smartshopai/${SERVICE_NAME}/build/libs/*.jar app.jar

# Expose default port (can be overridden)
EXPOSE 8080

# Set entrypoint
ENTRYPOINT ["java", "-jar", "app.jar"]
