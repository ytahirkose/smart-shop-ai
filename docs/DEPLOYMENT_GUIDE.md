# SmartShopAI Deployment Guide

## 📋 **Genel Bakış**

Bu dokümantasyon, SmartShopAI sisteminin production ortamına deployment sürecini detaylandırır. Sistem, microservices mimarisi kullanır ve Kubernetes üzerinde çalışır.

---

## 🏗️ **Sistem Mimarisi**

### **Production Environment Stack**

- **Container Orchestration**: Kubernetes 1.30
- **Container Runtime**: Docker 25.0
- **Service Mesh**: Istio (opsiyonel)
- **Load Balancer**: NGINX Ingress Controller
- **SSL/TLS**: Let's Encrypt + cert-manager
- **Monitoring**: Prometheus + Grafana + Jaeger
- **Logging**: ELK Stack (Elasticsearch + Logstash + Kibana)
- **Database**: MongoDB 8.0 (sharded cluster)
- **Cache**: Redis 7.4 (cluster mode)
- **Message Queue**: Apache Kafka 3.7.0
- **Search Engine**: Elasticsearch 8.13.0
- **Vector Database**: Pinecone/Weaviate

---

## 🚀 **Pre-Deployment Checklist**

### **1. Infrastructure Requirements**

#### **Kubernetes Cluster**
```bash
# Minimum cluster specs
- CPU: 16 cores
- RAM: 32 GB
- Storage: 500 GB SSD
- Nodes: 3+ worker nodes
```

#### **Storage Requirements**
```bash
# Persistent volumes
- MongoDB: 200 GB
- Redis: 50 GB
- Elasticsearch: 100 GB
- Kafka: 100 GB
- Application logs: 100 GB
```

#### **Network Requirements**
```bash
# Network policies
- Ingress: 80, 443
- Internal services: 8080-8090
- Database: 27017, 6379, 9200, 9092
- Monitoring: 9090, 3000, 16686
```

### **2. Security Requirements**

#### **SSL Certificates**
```bash
# Domain certificates
- api.smartshopai.com
- admin.smartshopai.com
- monitoring.smartshopai.com
```

#### **Secrets Management**
```bash
# Required secrets
- JWT_SECRET
- OPENAI_API_KEY
- TWILIO_API_KEY
- AWS_ACCESS_KEY
- AWS_SECRET_KEY
- MONGODB_PASSWORD
- REDIS_PASSWORD
```

### **3. Environment Variables**

#### **Production Environment**
```bash
# Core configuration
SPRING_PROFILES_ACTIVE=production
SERVER_PORT=8080
LOG_LEVEL=INFO

# Database
MONGODB_HOST=smartshopai-mongodb
MONGODB_PORT=27017
MONGODB_DATABASE=smartshopai_production

# Cache
REDIS_HOST=smartshopai-redis
REDIS_PORT=6379

# AI Services
OPENAI_API_KEY=your-openai-api-key
PINECONE_API_KEY=your-pinecone-api-key
WEAVIATE_API_KEY=your-weaviate-api-key

# Monitoring
PROMETHEUS_ENDPOINT=http://smartshopai-prometheus:9090
GRAFANA_ENDPOINT=http://smartshopai-grafana:3000
JAEGER_ENDPOINT=http://smartshopai-jaeger:16686
```

---

## 🐳 **Docker Configuration**

### **1. Multi-Stage Dockerfile Template**

```dockerfile
# Base stage
FROM eclipse-temurin:21-jdk-alpine AS base
WORKDIR /app
COPY gradle gradle
COPY gradlew .
COPY build.gradle settings.gradle ./
RUN ./gradlew dependencies

# Build stage
FROM base AS build
COPY src src
RUN ./gradlew build -x test

# Runtime stage
FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

# Security: non-root user
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup
USER appuser

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# JVM optimization
ENV JAVA_OPTS="-Xms512m -Xmx2g -XX:+UseG1GC -XX:+UseContainerSupport"

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
```

### **2. Docker Compose (Development)**

```yaml
version: '3.8'

services:
  # Discovery Service
  smartshopai-discovery:
    build: ./smartshopai-discovery
    ports:
      - "8761:8761"
    environment:
      - SPRING_PROFILES_ACTIVE=dev
    networks:
      - smartshopai-network

  # API Gateway
  smartshopai-gateway:
    build: ./smartshopai-gateway
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=dev
    depends_on:
      - smartshopai-discovery
    networks:
      - smartshopai-network

  # User Service
  smartshopai-user-service:
    build: ./smartshopai-user-service
    ports:
      - "8081:8081"
    environment:
      - SPRING_PROFILES_ACTIVE=dev
    depends_on:
      - smartshopai-discovery
      - mongodb
      - redis
    networks:
      - smartshopai-network

  # Product Service
  smartshopai-product-service:
    build: ./smartshopai-product-service
    ports:
      - "8082:8082"
    environment:
      - SPRING_PROFILES_ACTIVE=dev
    depends_on:
      - smartshopai-discovery
      - mongodb
      - redis
    networks:
      - smartshopai-network

  # AI Services
  smartshopai-ai-analysis-service:
    build: ./smartshopai-ai-analysis-service
    ports:
      - "8083:8083"
    environment:
      - SPRING_PROFILES_ACTIVE=dev
    depends_on:
      - smartshopai-discovery
      - mongodb
      - redis
    networks:
      - smartshopai-network

  smartshopai-ai-recommendation-service:
    build: ./smartshopai-ai-recommendation-service
    ports:
      - "8087:8087"
    environment:
      - SPRING_PROFILES_ACTIVE=dev
    depends_on:
      - smartshopai-discovery
      - mongodb
      - redis
    networks:
      - smartshopai-network

  smartshopai-ai-search-service:
    build: ./smartshopai-ai-search-service
    ports:
      - "8088:8088"
    environment:
      - SPRING_PROFILES_ACTIVE=dev
    depends_on:
      - smartshopai-discovery
      - mongodb
      - redis
      - elasticsearch
    networks:
      - smartshopai-network

  # Notification Service
  smartshopai-notification-service:
    build: ./smartshopai-notification-service
    ports:
      - "8084:8084"
    environment:
      - SPRING_PROFILES_ACTIVE=dev
    depends_on:
      - smartshopai-discovery
      - mongodb
      - redis
    networks:
      - smartshopai-network

  # Monitoring Service
  smartshopai-monitoring:
    build: ./smartshopai-monitoring
    ports:
      - "8085:8085"
    environment:
      - SPRING_PROFILES_ACTIVE=dev
    depends_on:
      - smartshopai-discovery
      - mongodb
      - redis
    networks:
      - smartshopai-network

  # Infrastructure Services
  mongodb:
    image: mongo:8.0
    ports:
      - "27017:27017"
    environment:
      - MONGO_INITDB_ROOT_USERNAME=admin
      - MONGO_INITDB_ROOT_PASSWORD=password
    volumes:
      - mongodb_data:/data/db
    networks:
      - smartshopai-network

  redis:
    image: redis:7.4-alpine
    ports:
      - "6379:6379"
    command: redis-server --requirepass password
    volumes:
      - redis_data:/data
    networks:
      - smartshopai-network

  elasticsearch:
    image: docker.elastic.co/elasticsearch/elasticsearch:8.13.0
    ports:
      - "9200:9200"
    environment:
      - discovery.type=single-node
      - xpack.security.enabled=false
    volumes:
      - elasticsearch_data:/usr/share/elasticsearch/data
    networks:
      - smartshopai-network

  kafka:
    image: confluentinc/cp-kafka:7.7.0
    ports:
      - "9092:9092"
    environment:
      - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181
      - KAFKA_CFG_LISTENERS=PLAINTEXT://:9092
      - KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092
    depends_on:
      - zookeeper
    networks:
      - smartshopai-network

  zookeeper:
    image: confluentinc/cp-zookeeper:7.7.0
    environment:
      - ZOOKEEPER_CLIENT_PORT=2181
    networks:
      - smartshopai-network

  # Monitoring Stack
  prometheus:
    image: prom/prometheus:v2.50.0
    ports:
      - "9090:9090"
    volumes:
      - ./monitoring/prometheus.yml:/etc/prometheus/prometheus.yml
      - prometheus_data:/prometheus
    networks:
      - smartshopai-network

  grafana:
    image: grafana/grafana:11.0.0
    ports:
      - "3000:3000"
    environment:
      - GF_SECURITY_ADMIN_PASSWORD=admin
    volumes:
      - grafana_data:/var/lib/grafana
    networks:
      - smartshopai-network

  jaeger:
    image: jaegertracing/all-in-one:1.57.0
    ports:
      - "16686:16686"
      - "14268:14268"
    networks:
      - smartshopai-network

volumes:
  mongodb_data:
  redis_data:
  elasticsearch_data:
  prometheus_data:
  grafana_data:

networks:
  smartshopai-network:
    driver: bridge
```

---

## ☸️ **Kubernetes Deployment**

### **1. Namespace Setup**

```bash
# Create namespace
kubectl create namespace smartshopai-production

# Set context
kubectl config set-context --current --namespace=smartshopai-production
```

### **2. Storage Classes**

```yaml
# storage-class.yaml
apiVersion: storage.k8s.io/v1
kind: StorageClass
metadata:
  name: smartshopai-storage
provisioner: kubernetes.io/aws-ebs
parameters:
  type: gp3
  iops: "3000"
  throughput: "125"
reclaimPolicy: Retain
volumeBindingMode: WaitForFirstConsumer
```

### **3. Persistent Volumes**

```yaml
# persistent-volumes.yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: mongodb-pvc
spec:
  accessModes:
    - ReadWriteOnce
  storageClassName: smartshopai-storage
  resources:
    requests:
      storage: 200Gi
---
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: redis-pvc
spec:
  accessModes:
    - ReadWriteOnce
  storageClassName: smartshopai-storage
  resources:
    requests:
      storage: 50Gi
---
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: elasticsearch-pvc
spec:
  accessModes:
    - ReadWriteOnce
  storageClassName: smartshopai-storage
  resources:
    requests:
      storage: 100Gi
```

### **4. Secrets Management**

```bash
# Create secrets
kubectl create secret generic smartshopai-secrets \
  --from-literal=jwt-secret="your-jwt-secret-key" \
  --from-literal=openai-api-key="your-openai-api-key" \
  --from-literal=twilio-api-key="your-twilio-api-key" \
  --from-literal=twilio-api-secret="your-twilio-api-secret" \
  --from-literal=aws-access-key="your-aws-access-key" \
  --from-literal=aws-secret-key="your-aws-secret-key" \
  --from-literal=mongodb-password="your-mongodb-password" \
  --from-literal=redis-password="your-redis-password" \
  --from-literal=elasticsearch-password="your-elasticsearch-password"
```

### **5. ConfigMaps**

```yaml
# configmaps.yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: smartshopai-config
data:
  # Application configuration
  SPRING_PROFILES_ACTIVE: "production"
  SERVER_PORT: "8080"
  LOG_LEVEL: "INFO"
  
  # Database configuration
  MONGODB_HOST: "smartshopai-mongodb"
  MONGODB_PORT: "27017"
  MONGODB_DATABASE: "smartshopai_production"
  
  # Cache configuration
  REDIS_HOST: "smartshopai-redis"
  REDIS_PORT: "6379"
  
  # AI configuration
  OPENAI_API_KEY: "your-openai-api-key"
  PINECONE_API_KEY: "your-pinecone-api-key"
  WEAVIATE_API_KEY: "your-weaviate-api-key"
  
  # Monitoring configuration
  PROMETHEUS_ENDPOINT: "http://smartshopai-prometheus:9090"
  GRAFANA_ENDPOINT: "http://smartshopai-grafana:3000"
  JAEGER_ENDPOINT: "http://smartshopai-jaeger:16686"
```

### **6. Service Deployments**

#### **Discovery Service**
```yaml
# discovery-deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: smartshopai-discovery
spec:
  replicas: 2
  selector:
    matchLabels:
      app: smartshopai-discovery
  template:
    metadata:
      labels:
        app: smartshopai-discovery
    spec:
      containers:
      - name: discovery
        image: ghcr.io/smartshopai/smartshopai-discovery:latest
        ports:
        - containerPort: 8761
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: "production"
        resources:
          requests:
            memory: "512Mi"
            cpu: "250m"
          limits:
            memory: "1Gi"
            cpu: "500m"
        livenessProbe:
          httpGet:
            path: /actuator/health
            port: 8761
          initialDelaySeconds: 60
          periodSeconds: 30
        readinessProbe:
          httpGet:
            path: /actuator/health
            port: 8761
          initialDelaySeconds: 30
          periodSeconds: 10
```

#### **API Gateway**
```yaml
# gateway-deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: smartshopai-gateway
spec:
  replicas: 3
  selector:
    matchLabels:
      app: smartshopai-gateway
  template:
    metadata:
      labels:
        app: smartshopai-gateway
    spec:
      containers:
      - name: gateway
        image: ghcr.io/smartshopai/smartshopai-gateway:latest
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: "production"
        resources:
          requests:
            memory: "1Gi"
            cpu: "500m"
          limits:
            memory: "2Gi"
            cpu: "1000m"
        livenessProbe:
          httpGet:
            path: /actuator/health
            port: 8080
          initialDelaySeconds: 60
          periodSeconds: 30
        readinessProbe:
          httpGet:
            path: /actuator/health
            port: 8080
          initialDelaySeconds: 30
          periodSeconds: 10
```

### **7. Ingress Configuration**

```yaml
# ingress.yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: smartshopai-ingress
  annotations:
    kubernetes.io/ingress.class: "nginx"
    cert-manager.io/cluster-issuer: "letsencrypt-prod"
    nginx.ingress.kubernetes.io/ssl-redirect: "true"
    nginx.ingress.kubernetes.io/force-ssl-redirect: "true"
    nginx.ingress.kubernetes.io/rate-limit: "100"
    nginx.ingress.kubernetes.io/rate-limit-window: "1m"
    nginx.ingress.kubernetes.io/proxy-body-size: "10m"
    nginx.ingress.kubernetes.io/proxy-read-timeout: "300"
    nginx.ingress.kubernetes.io/proxy-send-timeout: "300"
spec:
  tls:
  - hosts:
    - api.smartshopai.com
    - admin.smartshopai.com
    - monitoring.smartshopai.com
    secretName: smartshopai-tls
  rules:
  - host: api.smartshopai.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: smartshopai-gateway
            port:
              number: 80
  - host: admin.smartshopai.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: smartshopai-admin
            port:
              number: 80
  - host: monitoring.smartshopai.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: smartshopai-grafana
            port:
              number: 3000
```

---

## 🔧 **Deployment Scripts**

### **1. Automated Deployment Script**

```bash
#!/bin/bash
# deploy.sh

set -e

# Configuration
NAMESPACE="smartshopai-production"
REGISTRY="ghcr.io/smartshopai"
VERSION="${1:-latest}"

echo "🚀 Starting SmartShopAI deployment..."

# 1. Create namespace
echo "📦 Creating namespace..."
kubectl create namespace $NAMESPACE --dry-run=client -o yaml | kubectl apply -f -

# 2. Apply storage classes
echo "💾 Applying storage classes..."
kubectl apply -f k8s/storage-class.yaml

# 3. Create secrets
echo "🔐 Creating secrets..."
kubectl create secret generic smartshopai-secrets \
  --from-literal=jwt-secret="$JWT_SECRET" \
  --from-literal=openai-api-key="$OPENAI_API_KEY" \
  --from-literal=twilio-api-key="$TWILIO_API_KEY" \
  --from-literal=twilio-api-secret="$TWILIO_API_SECRET" \
  --from-literal=aws-access-key="$AWS_ACCESS_KEY" \
  --from-literal=aws-secret-key="$AWS_SECRET_KEY" \
  --from-literal=mongodb-password="$MONGODB_PASSWORD" \
  --from-literal=redis-password="$REDIS_PASSWORD" \
  --from-literal=elasticsearch-password="$ELASTICSEARCH_PASSWORD" \
  --dry-run=client -o yaml | kubectl apply -f -

# 4. Apply configmaps
echo "⚙️ Applying configmaps..."
kubectl apply -f k8s/configmaps.yaml

# 5. Deploy infrastructure services
echo "🏗️ Deploying infrastructure services..."
kubectl apply -f k8s/infrastructure/

# 6. Wait for infrastructure to be ready
echo "⏳ Waiting for infrastructure to be ready..."
kubectl wait --for=condition=ready pod -l app=mongodb --timeout=300s
kubectl wait --for=condition=ready pod -l app=redis --timeout=300s
kubectl wait --for=condition=ready pod -l app=elasticsearch --timeout=300s

# 7. Deploy application services
echo "🚀 Deploying application services..."
kubectl apply -f k8s/services/

# 8. Wait for services to be ready
echo "⏳ Waiting for services to be ready..."
kubectl wait --for=condition=ready pod -l app=smartshopai-discovery --timeout=300s
kubectl wait --for=condition=ready pod -l app=smartshopai-gateway --timeout=300s

# 9. Deploy monitoring
echo "📊 Deploying monitoring stack..."
kubectl apply -f k8s/monitoring/

# 10. Apply ingress
echo "🌐 Applying ingress..."
kubectl apply -f k8s/ingress.yaml

# 11. Run health checks
echo "🏥 Running health checks..."
sleep 60
curl -f https://api.smartshopai.com/actuator/health || exit 1

echo "✅ Deployment completed successfully!"
echo "🌐 API Gateway: https://api.smartshopai.com"
echo "📊 Monitoring: https://monitoring.smartshopai.com"
echo "🔧 Admin Panel: https://admin.smartshopai.com"
```

### **2. Rollback Script**

```bash
#!/bin/bash
# rollback.sh

set -e

NAMESPACE="smartshopai-production"
PREVIOUS_VERSION="${1}"

if [ -z "$PREVIOUS_VERSION" ]; then
    echo "❌ Please provide the previous version to rollback to"
    exit 1
fi

echo "🔄 Rolling back to version $PREVIOUS_VERSION..."

# Update deployments to previous version
kubectl set image deployment/smartshopai-discovery discovery=ghcr.io/smartshopai/smartshopai-discovery:$PREVIOUS_VERSION -n $NAMESPACE
kubectl set image deployment/smartshopai-gateway gateway=ghcr.io/smartshopai/smartshopai-gateway:$PREVIOUS_VERSION -n $NAMESPACE
kubectl set image deployment/smartshopai-user-service user-service=ghcr.io/smartshopai/smartshopai-user-service:$PREVIOUS_VERSION -n $NAMESPACE
kubectl set image deployment/smartshopai-product-service product-service=ghcr.io/smartshopai/smartshopai-product-service:$PREVIOUS_VERSION -n $NAMESPACE
kubectl set image deployment/smartshopai-ai-analysis-service ai-analysis-service=ghcr.io/smartshopai/smartshopai-ai-analysis-service:$PREVIOUS_VERSION -n $NAMESPACE
kubectl set image deployment/smartshopai-ai-recommendation-service ai-recommendation-service=ghcr.io/smartshopai/smartshopai-ai-recommendation-service:$PREVIOUS_VERSION -n $NAMESPACE
kubectl set image deployment/smartshopai-ai-search-service ai-search-service=ghcr.io/smartshopai/smartshopai-ai-search-service:$PREVIOUS_VERSION -n $NAMESPACE
kubectl set image deployment/smartshopai-notification-service notification-service=ghcr.io/smartshopai/smartshopai-notification-service:$PREVIOUS_VERSION -n $NAMESPACE
kubectl set image deployment/smartshopai-monitoring monitoring=ghcr.io/smartshopai/smartshopai-monitoring:$PREVIOUS_VERSION -n $NAMESPACE

echo "✅ Rollback completed successfully!"
```

### **3. Health Check Script**

```bash
#!/bin/bash
# health-check.sh

set -e

echo "🏥 Running comprehensive health checks..."

# Check all services
SERVICES=(
    "smartshopai-discovery:8761"
    "smartshopai-gateway:8080"
    "smartshopai-user-service:8081"
    "smartshopai-product-service:8082"
    "smartshopai-ai-analysis-service:8083"
    "smartshopai-ai-recommendation-service:8087"
    "smartshopai-ai-search-service:8088"
    "smartshopai-notification-service:8084"
    "smartshopai-monitoring:8085"
)

for service in "${SERVICES[@]}"; do
    IFS=':' read -r name port <<< "$service"
    echo "Checking $name..."
    
    # Check if pod is running
    if kubectl get pods -l app=$name --no-headers | grep -q Running; then
        echo "✅ $name is running"
    else
        echo "❌ $name is not running"
        exit 1
    fi
    
    # Check health endpoint
    if curl -f http://$name:$port/actuator/health > /dev/null 2>&1; then
        echo "✅ $name health check passed"
    else
        echo "❌ $name health check failed"
        exit 1
    fi
done

# Check external endpoints
echo "Checking external endpoints..."

if curl -f https://api.smartshopai.com/actuator/health > /dev/null 2>&1; then
    echo "✅ API Gateway is accessible"
else
    echo "❌ API Gateway is not accessible"
    exit 1
fi

if curl -f https://monitoring.smartshopai.com > /dev/null 2>&1; then
    echo "✅ Monitoring is accessible"
else
    echo "❌ Monitoring is not accessible"
    exit 1
fi

echo "✅ All health checks passed!"
```

---

## 📊 **Monitoring & Observability**

### **1. Prometheus Configuration**

```yaml
# prometheus.yml
global:
  scrape_interval: 15s
  evaluation_interval: 15s

rule_files:
  - "alert_rules.yml"

scrape_configs:
  - job_name: 'smartshopai-services'
    static_configs:
      - targets: 
        - 'smartshopai-discovery:8761'
        - 'smartshopai-gateway:8080'
        - 'smartshopai-user-service:8081'
        - 'smartshopai-product-service:8082'
        - 'smartshopai-ai-analysis-service:8083'
        - 'smartshopai-ai-recommendation-service:8087'
        - 'smartshopai-ai-search-service:8088'
        - 'smartshopai-notification-service:8084'
        - 'smartshopai-monitoring:8085'
    metrics_path: '/actuator/prometheus'
    scrape_interval: 30s

  - job_name: 'mongodb'
    static_configs:
      - targets: ['smartshopai-mongodb:9216']

  - job_name: 'redis'
    static_configs:
      - targets: ['smartshopai-redis:9121']

  - job_name: 'elasticsearch'
    static_configs:
      - targets: ['smartshopai-elasticsearch:9114']
```

### **2. Grafana Dashboards**

#### **Application Metrics Dashboard**
```json
{
  "dashboard": {
    "title": "SmartShopAI Application Metrics",
    "panels": [
      {
        "title": "Request Rate",
        "type": "graph",
        "targets": [
          {
            "expr": "rate(http_requests_total[5m])",
            "legendFormat": "{{service}}"
          }
        ]
      },
      {
        "title": "Response Time",
        "type": "graph",
        "targets": [
          {
            "expr": "histogram_quantile(0.95, rate(http_request_duration_seconds_bucket[5m]))",
            "legendFormat": "{{service}}"
          }
        ]
      },
      {
        "title": "Error Rate",
        "type": "graph",
        "targets": [
          {
            "expr": "rate(http_requests_total{status=~\"5..\"}[5m])",
            "legendFormat": "{{service}}"
          }
        ]
      }
    ]
  }
}
```

### **3. Alerting Rules**

```yaml
# alert_rules.yml
groups:
  - name: smartshopai-alerts
    rules:
      - alert: HighErrorRate
        expr: rate(http_requests_total{status=~"5.."}[5m]) > 0.1
        for: 5m
        labels:
          severity: critical
        annotations:
          summary: "High error rate detected"
          description: "Service {{ $labels.service }} has high error rate"

      - alert: HighResponseTime
        expr: histogram_quantile(0.95, rate(http_request_duration_seconds_bucket[5m])) > 2
        for: 5m
        labels:
          severity: warning
        annotations:
          summary: "High response time detected"
          description: "Service {{ $labels.service }} has high response time"

      - alert: ServiceDown
        expr: up == 0
        for: 1m
        labels:
          severity: critical
        annotations:
          summary: "Service is down"
          description: "Service {{ $labels.service }} is not responding"
```

---

## 🔒 **Security Hardening**

### **1. Network Policies**

```yaml
# network-policies.yaml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: smartshopai-network-policy
spec:
  podSelector:
    matchLabels:
      app: smartshopai-gateway
  policyTypes:
  - Ingress
  - Egress
  ingress:
  - from:
    - namespaceSelector:
        matchLabels:
          name: ingress-nginx
    ports:
    - protocol: TCP
      port: 8080
  egress:
  - to:
    - podSelector:
        matchLabels:
          app: smartshopai-discovery
    ports:
    - protocol: TCP
      port: 8761
  - to:
    - podSelector:
        matchLabels:
          app: smartshopai-user-service
    ports:
    - protocol: TCP
      port: 8081
```

### **2. Security Context**

```yaml
# security-context.yaml
securityContext:
  runAsNonRoot: true
  runAsUser: 1001
  runAsGroup: 1001
  fsGroup: 1001
  capabilities:
    drop:
      - ALL
  readOnlyRootFilesystem: true
  allowPrivilegeEscalation: false
```

### **3. Pod Security Standards**

```yaml
# pod-security.yaml
apiVersion: v1
kind: PodSecurityPolicy
metadata:
  name: smartshopai-psp
spec:
  privileged: false
  allowPrivilegeEscalation: false
  requiredDropCapabilities:
    - ALL
  volumes:
    - 'configMap'
    - 'emptyDir'
    - 'projected'
    - 'secret'
    - 'downwardAPI'
    - 'persistentVolumeClaim'
  hostNetwork: false
  hostIPC: false
  hostPID: false
  runAsUser:
    rule: 'MustRunAsNonRoot'
  seLinux:
    rule: 'RunAsAny'
  supplementalGroups:
    rule: 'MustRunAs'
    ranges:
      - min: 1
        max: 65535
  fsGroup:
    rule: 'MustRunAs'
    ranges:
      - min: 1
        max: 65535
  readOnlyRootFilesystem: true
```

---

## 📈 **Performance Optimization**

### **1. Resource Limits**

```yaml
# resource-limits.yaml
resources:
  requests:
    memory: "1Gi"
    cpu: "500m"
  limits:
    memory: "2Gi"
    cpu: "1000m"
```

### **2. Horizontal Pod Autoscaler**

```yaml
# hpa.yaml
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: smartshopai-gateway-hpa
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: smartshopai-gateway
  minReplicas: 3
  maxReplicas: 10
  metrics:
  - type: Resource
    resource:
      name: cpu
      target:
        type: Utilization
        averageUtilization: 70
  - type: Resource
    resource:
      name: memory
      target:
        type: Utilization
        averageUtilization: 80
```

### **3. JVM Optimization**

```bash
# JVM options for production
JAVA_OPTS="-Xms1g -Xmx2g \
  -XX:+UseG1GC \
  -XX:+UseContainerSupport \
  -XX:MaxGCPauseMillis=200 \
  -XX:+UnlockExperimentalVMOptions \
  -XX:+UseCGroupMemoryLimitForHeap \
  -XX:+HeapDumpOnOutOfMemoryError \
  -XX:HeapDumpPath=/tmp \
  -Djava.security.egd=file:/dev/./urandom"
```

---

## 🔄 **Backup & Disaster Recovery**

### **1. Database Backup Strategy**

```bash
#!/bin/bash
# backup.sh

# MongoDB backup
mongodump --host smartshopai-mongodb --port 27017 \
  --username admin --password $MONGODB_PASSWORD \
  --out /backup/mongodb/$(date +%Y%m%d_%H%M%S)

# Redis backup
redis-cli -h smartshopai-redis -a $REDIS_PASSWORD BGSAVE

# Elasticsearch backup
curl -X PUT "smartshopai-elasticsearch:9200/_snapshot/backup_repo/snapshot_$(date +%Y%m%d_%H%M%S)" \
  -H "Content-Type: application/json" \
  -d '{"indices": "*"}'

# Compress and upload to S3
tar -czf backup_$(date +%Y%m%d_%H%M%S).tar.gz /backup/
aws s3 cp backup_*.tar.gz s3://smartshopai-backups/
```

### **2. Disaster Recovery Plan**

```bash
#!/bin/bash
# disaster-recovery.sh

# 1. Restore from backup
aws s3 cp s3://smartshopai-backups/latest_backup.tar.gz .
tar -xzf latest_backup.tar.gz

# 2. Restore MongoDB
mongorestore --host smartshopai-mongodb --port 27017 \
  --username admin --password $MONGODB_PASSWORD \
  /backup/mongodb/

# 3. Restore Redis
redis-cli -h smartshopai-redis -a $REDIS_PASSWORD FLUSHALL
redis-cli -h smartshopai-redis -a $REDIS_PASSWORD < /backup/redis/dump.rdb

# 4. Restore Elasticsearch
curl -X POST "smartshopai-elasticsearch:9200/_snapshot/backup_repo/snapshot_latest/_restore" \
  -H "Content-Type: application/json" \
  -d '{"indices": "*"}'

# 5. Restart services
kubectl rollout restart deployment/smartshopai-gateway
kubectl rollout restart deployment/smartshopai-user-service
kubectl rollout restart deployment/smartshopai-product-service
```

---

## 📞 **Support & Troubleshooting**

### **1. Common Issues**

#### **Service Not Starting**
```bash
# Check pod status
kubectl get pods -n smartshopai-production

# Check pod logs
kubectl logs -f deployment/smartshopai-gateway

# Check events
kubectl get events -n smartshopai-production --sort-by='.lastTimestamp'
```

#### **Database Connection Issues**
```bash
# Check MongoDB connectivity
kubectl exec -it deployment/smartshopai-user-service -- nc -zv smartshopai-mongodb 27017

# Check Redis connectivity
kubectl exec -it deployment/smartshopai-user-service -- nc -zv smartshopai-redis 6379
```

#### **Memory Issues**
```bash
# Check memory usage
kubectl top pods -n smartshopai-production

# Check JVM heap
kubectl exec -it deployment/smartshopai-gateway -- jstat -gc
```

### **2. Monitoring Commands**

```bash
# Check service health
kubectl get endpoints -n smartshopai-production

# Check ingress status
kubectl get ingress -n smartshopai-production

# Check persistent volumes
kubectl get pvc -n smartshopai-production

# Check secrets
kubectl get secrets -n smartshopai-production
```

### **3. Log Analysis**

```bash
# View logs from all services
kubectl logs -f -l app=smartshopai-gateway --tail=100

# Search for errors
kubectl logs -l app=smartshopai-gateway | grep ERROR

# Follow logs in real-time
kubectl logs -f deployment/smartshopai-gateway
```

---

## 📚 **Additional Resources**

- **Kubernetes Documentation**: [kubernetes.io](https://kubernetes.io/docs/)
- **Helm Charts**: [helm.sh](https://helm.sh/)
- **Prometheus Documentation**: [prometheus.io](https://prometheus.io/docs/)
- **Grafana Documentation**: [grafana.com](https://grafana.com/docs/)
- **Istio Documentation**: [istio.io](https://istio.io/docs/)

---

## 📞 **Support Contacts**

- **DevOps Team**: devops@smartshopai.com
- **Infrastructure Issues**: infrastructure@smartshopai.com
- **Security Issues**: security@smartshopai.com
- **Emergency**: +1-555-EMERGENCY
