# SmartShopAI - Akıllı Alışveriş Asistanı

## 🎯 Proje Özeti

SmartShopAI, yapay zeka teknolojisi kullanarak kullanıcıların daha az harcama yaparken daha kaliteli ürünler bulmasını sağlayan, kapsamlı bir e-ticaret asistanı platformudur. Platform, ürün karşılaştırma, teknik detay analizi, alternatif önerisi, fiyat takibi ve kişiselleştirilmiş alışveriş deneyimi sunar.

## 🏗️ Sistem Mimarisi

### Microservices Yapısı

- **Discovery Service** (Port: 8761) - Service discovery ve registry
- **Gateway Service** (Port: 8080) - API Gateway ve routing
- **User Service** (Port: 8081) - Kullanıcı yönetimi
- **Product Service** (Port: 8082) - Ürün yönetimi
- **AI Analysis Service** (Port: 8083) - AI destekli ürün analizi
- **AI Recommendation Service** (Port: 8084) - AI önerileri
- **AI Search Service** (Port: 8085) - AI destekli arama
- **Search Service** (Port: 8091) - Temel arama işlevleri
- **Cache Service** (Port: 8086) - Önbellek yönetimi
- **Notification Service** (Port: 8087) - Bildirim yönetimi
- **Monitoring Service** (Port: 8088) - Sistem izleme
- **Business Intelligence Service** (Port: 8089) - İş zekası
- **Session Cache Service** (Port: 8090) - Oturum önbelleği
- **Security Service** (Port: 8092) - Güvenlik yönetimi

### Teknoloji Stack

- **Backend**: Java 21, Spring Boot 3.5, Spring Cloud 2024.0.0
- **Database**: MongoDB 8.0, Redis 7.4
- **AI/ML**: Spring AI, OpenAI GPT-4
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Security**: Spring Security, JWT
- **Monitoring**: Spring Boot Actuator, Micrometer
- **Containerization**: Docker, Docker Compose
- **Orchestration**: Kubernetes (production)

## 🚀 Hızlı Başlangıç

### Gereksinimler

- Java 21+
- Docker & Docker Compose
- Maven veya Gradle
- Git

### Kurulum

1. **Repository'yi klonlayın:**
```bash
git clone https://github.com/yourusername/smartshopai.git
cd smartshopai
```

2. **Docker Compose ile servisleri başlatın:**
```bash
docker-compose up -d
```

3. **Servislerin başlamasını bekleyin (yaklaşık 2-3 dakika)**

4. **Health check yapın:**
```bash
chmod +x health-check.sh
./health-check.sh
```

### Servis URL'leri

- **Discovery Service**: http://localhost:8761
- **Gateway Service**: http://localhost:8080
- **User Service**: http://localhost:8081
- **Product Service**: http://localhost:8082
- **AI Analysis Service**: http://localhost:8083
- **API Documentation**: http://localhost:8080/swagger-ui.html

## 📚 API Kullanımı

### Kullanıcı İşlemleri

```bash
# Kullanıcı oluşturma
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123",
    "firstName": "Test",
    "lastName": "User"
  }'

# Kullanıcı bilgilerini getirme
curl http://localhost:8080/api/users/{userId}
```

### AI Analiz İşlemleri

```bash
# Ürün analizi
curl -X POST http://localhost:8080/api/ai/analysis/product \
  -H "Content-Type: application/json" \
  -d '{"productData": "Sample product information"}'

# Ürün karşılaştırması
curl -X POST http://localhost:8080/api/ai/analysis/compare \
  -H "Content-Type: application/json" \
  -d '["Product 1 data", "Product 2 data"]'
```

## 🔧 Geliştirme

### Proje Yapısı

```
smartshopai/
├── smartshopai-discovery/          # Service discovery
├── smartshopai-gateway/            # API Gateway
├── smartshopai-user-service/       # User management
├── smartshopai-product-service/    # Product management
├── smartshopai-ai-analysis-service/ # AI analysis
├── smartshopai-common/             # Shared components
├── smartshopai-security/           # Security components
├── docker-compose.yml              # Docker configuration
├── Dockerfile                      # Multi-stage Docker build
└── k8s/                           # Kubernetes manifests
```

### Yeni Servis Ekleme

1. **Yeni servis klasörü oluşturun:**
```bash
mkdir smartshopai-new-service
cd smartshopai-new-service
```

2. **build.gradle dosyası oluşturun:**
```gradle
dependencies {
    implementation project(':smartshopai-common')
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-client'
}
```

3. **Docker Compose'a ekleyin:**
```yaml
new-service:
  build:
    context: .
    dockerfile: Dockerfile
    args:
      SERVICE_NAME: smartshopai-new-service
  ports:
    - "8093:8093"
```

### Test Çalıştırma

```bash
# Tüm testleri çalıştır
./gradlew test

# Belirli servisin testlerini çalıştır
./gradlew :smartshopai-user-service:test
```

## 📊 Monitoring ve Health Checks

### Actuator Endpoints

Her servis aşağıdaki endpoint'leri sunar:
- `/actuator/health` - Servis sağlık durumu
- `/actuator/info` - Servis bilgileri
- `/actuator/metrics` - Metrikler

### Health Check Script

```bash
# Manuel health check
./health-check.sh

# Belirli servis kontrolü
curl http://localhost:8081/actuator/health
```

## 🐳 Docker

### Container Yönetimi

```bash
# Tüm servisleri başlat
docker-compose up -d

# Belirli servisi başlat
docker-compose up -d user-service

# Logları görüntüle
docker-compose logs -f

# Servisleri durdur
docker-compose down

# Volumeleri temizle
docker-compose down -v
```

### Docker Image Build

```bash
# Tüm servisleri build et
docker-compose build

# Belirli servisi build et
docker-compose build user-service
```

## ☁️ Production Deployment

### Kubernetes

Production ortamı için Kubernetes manifest'leri `k8s/production/` klasöründe bulunur:

```bash
# Production deployment
kubectl apply -f k8s/production/

# Service'leri kontrol et
kubectl get services
kubectl get pods
```

### Environment Variables

Production için gerekli environment variables:

```bash
# OpenAI API Key
export OPENAI_API_KEY="your-openai-api-key"

# MongoDB credentials
export MONGO_USERNAME="your-mongo-username"
export MONGO_PASSWORD="your-mongo-password"

# JWT Secret
export JWT_SECRET="your-jwt-secret"
```

## 🔒 Güvenlik

### Authentication & Authorization

- JWT tabanlı authentication
- Role-based access control (RBAC)
- API rate limiting
- CORS configuration
- Security headers

### Security Headers

```yaml
# Gateway security configuration
security:
  headers:
    - X-Content-Type-Options: nosniff
    - X-Frame-Options: DENY
    - X-XSS-Protection: 1; mode=block
    - Strict-Transport-Security: max-age=31536000
```

## 📈 Performance ve Scalability

### Caching Strategy

- Redis ile distributed caching
- Service-level caching
- Response caching
- Session caching

### Load Balancing

- Eureka client-side load balancing
- Gateway routing
- Service discovery

### Monitoring

- Micrometer metrics
- Prometheus integration
- Distributed tracing
- Health checks

## 🐛 Troubleshooting

### Yaygın Sorunlar

1. **Servis başlamıyor:**
   - Docker logs kontrol edin: `docker-compose logs service-name`
   - Port çakışması kontrol edin
   - Database bağlantısını kontrol edin

2. **Database bağlantı hatası:**
   - MongoDB container'ının çalıştığından emin olun
   - Credentials'ları kontrol edin
   - Network ayarlarını kontrol edin

3. **Service discovery hatası:**
   - Eureka server'ın çalıştığından emin olun
   - Service URL'lerini kontrol edin

### Debug Mode

```bash
# Debug logları için
export LOGGING_LEVEL_COM_SMARTSHOPAI=DEBUG

# Docker compose ile debug
docker-compose up --build
```

## 🤝 Katkıda Bulunma

1. Fork yapın
2. Feature branch oluşturun (`git checkout -b feature/amazing-feature`)
3. Commit yapın (`git commit -m 'Add amazing feature'`)
4. Push yapın (`git push origin feature/amazing-feature`)
5. Pull Request oluşturun

## 📄 Lisans

Bu proje MIT lisansı altında lisanslanmıştır. Detaylar için `LICENSE` dosyasına bakın.

## 📞 İletişim

- **Proje Linki**: [https://github.com/yourusername/smartshopai](https://github.com/yourusername/smartshopai)
- **Issues**: [https://github.com/yourusername/smartshopai/issues](https://github.com/yourusername/smartshopai/issues)

## 🙏 Teşekkürler

- Spring Boot ekibine
- Spring Cloud ekibine
- MongoDB ekibine
- Redis ekibine
- Tüm open source katkıda bulunanlara

---

**SmartShopAI** - Akıllı alışveriş deneyimi için yapay zeka teknolojisi 🚀
