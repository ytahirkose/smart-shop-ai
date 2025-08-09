# SmartShopAI - Akıllı Alışveriş Asistanı

SmartShopAI, yapay zeka teknolojisi kullanarak kullanıcıların daha az harcama yaparken daha kaliteli ürünler bulmasını sağlayan, kapsamlı bir e-ticaret asistanı platformudur.

## 🎯 Proje Özeti

SmartShopAI platformu şu özellikleri sunar:
- **AI Destekli Ürün Analizi**: Teknik detayları anlaşılır hale getirme
- **Akıllı Karşılaştırma**: AI destekli ürün karşılaştırması
- **Alternatif Keşfi**: Daha iyi değer önerileri
- **Fiyat Takibi**: Akıllı uyarı sistemi
- **Kişiselleştirilmiş Deneyim**: Kullanıcı tercihlerine göre öneriler

## 🏗️ Sistem Mimarisi

### Mikroservisler

1. **Discovery Service** (Port: 8761) - Service Discovery
2. **Gateway Service** (Port: 8080) - API Gateway
3. **User Service** (Port: 8081) - Kullanıcı Yönetimi
4. **Product Service** (Port: 8082) - Ürün Yönetimi
5. **AI Analysis Service** (Port: 8083) - AI Ürün Analizi
6. **AI Recommendation Service** (Port: 8084) - AI Öneriler
7. **AI Search Service** (Port: 8085) - AI Arama
8. **Cache Service** (Port: 8086) - Önbellek Yönetimi
9. **Notification Service** (Port: 8087) - Bildirim Yönetimi
10. **Monitoring Service** (Port: 8088) - Sistem İzleme
11. **Business Intelligence Service** (Port: 8089) - İş Zekası

### Teknoloji Stack

- **Backend**: Java 21, Spring Boot 3.5, Spring Cloud 2024.0.0
- **Database**: MongoDB 7.0
- **Cache**: Redis 7.2
- **AI/ML**: Spring AI, OpenAI GPT-4
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Security**: Spring Security, JWT
- **Monitoring**: Spring Boot Actuator, Micrometer
- **Documentation**: OpenAPI 3.0, Swagger UI

## 🚀 Kurulum ve Çalıştırma

### Gereksinimler

- Java 21
- Docker & Docker Compose
- Maven veya Gradle
- MongoDB 7.0
- Redis 7.2

### Docker ile Çalıştırma

1. **Projeyi klonlayın:**
```bash
git clone https://github.com/your-username/smartshopai.git
cd smartshopai
```

2. **Environment değişkenlerini ayarlayın:**
```bash
export OPENAI_API_KEY=your-openai-api-key
export MAIL_USERNAME=your-email@gmail.com
export MAIL_PASSWORD=your-app-password
```

3. **Docker Compose ile başlatın:**
```bash
docker-compose up -d
```

4. **Servisleri kontrol edin:**
```bash
# Discovery Service
curl http://localhost:8761

# Gateway Service
curl http://localhost:8080/actuator/health

# API Documentation
open http://localhost:8080/swagger-ui.html
```

### Geliştirme Ortamında Çalıştırma

1. **MongoDB ve Redis'i başlatın:**
```bash
docker run -d --name mongodb -p 27017:27017 mongo:7.0
docker run -d --name redis -p 6379:6379 redis:7.2-alpine
```

2. **Projeyi derleyin:**
```bash
cd smartshopai
./gradlew build
```

3. **Servisleri sırayla başlatın:**
```bash
# Discovery Service
./gradlew :smartshopai-discovery:bootRun

# Gateway Service
./gradlew :smartshopai-gateway:bootRun

# User Service
./gradlew :smartshopai-user-service:bootRun

# Diğer servisler...
```

## 📚 API Dokümantasyonu

Her servisin API dokümantasyonu Swagger UI ile erişilebilir:

- **Gateway**: http://localhost:8080/swagger-ui.html
- **User Service**: http://localhost:8081/swagger-ui.html
- **Product Service**: http://localhost:8082/swagger-ui.html
- **AI Analysis Service**: http://localhost:8083/swagger-ui.html
- **AI Recommendation Service**: http://localhost:8084/swagger-ui.html
- **AI Search Service**: http://localhost:8085/swagger-ui.html

## 🔧 Konfigürasyon

### Environment Variables

```bash
# OpenAI API
OPENAI_API_KEY=your-openai-api-key

# Email Configuration
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-app-password

# Database
MONGODB_HOST=localhost
MONGODB_PORT=27017
REDIS_HOST=localhost
REDIS_PORT=6379

# JWT
JWT_SECRET=your-secret-key
JWT_EXPIRATION=86400000
```

### Application Properties

Her servisin `application.yml` dosyasında detaylı konfigürasyon bulunmaktadır.

## 🧪 Test

### Unit Tests
```bash
./gradlew test
```

### Integration Tests
```bash
./gradlew integrationTest
```

### End-to-End Tests
```bash
./gradlew e2eTest
```

## 📊 Monitoring

### Health Checks
- **Discovery**: http://localhost:8761/actuator/health
- **Gateway**: http://localhost:8080/actuator/health
- **User Service**: http://localhost:8081/actuator/health
- **Product Service**: http://localhost:8082/actuator/health

### Metrics
- **Prometheus**: http://localhost:8080/actuator/prometheus
- **Grafana**: http://localhost:3000 (if configured)

## 🔒 Güvenlik

- **Authentication**: JWT-based authentication
- **Authorization**: Role-based access control
- **Rate Limiting**: Bucket4j implementation
- **Input Validation**: Bean Validation
- **CORS**: Configured for web applications

## 📈 Performans

### Caching Strategy
- **Redis**: Distributed caching
- **Local Cache**: Caffeine for in-memory caching
- **Cache Eviction**: LRU policy

### Database Optimization
- **Indexes**: Optimized for common queries
- **Connection Pooling**: HikariCP
- **Read Replicas**: For read-heavy operations

## 🚀 Deployment

### Docker Deployment
```bash
# Build images
docker-compose build

# Deploy to production
docker-compose -f docker-compose.prod.yml up -d
```

### Kubernetes Deployment
```bash
# Apply Kubernetes manifests
kubectl apply -f k8s/
```

### Cloud Deployment
- **AWS**: ECS, EKS, or EC2
- **Azure**: AKS or App Service
- **GCP**: GKE or Cloud Run

## 🤝 Katkıda Bulunma

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 Lisans

Bu proje MIT lisansı altında lisanslanmıştır. Detaylar için `LICENSE` dosyasına bakın.

## 📞 İletişim

- **Proje Sahibi**: [Your Name](mailto:your-email@example.com)
- **GitHub Issues**: [Issues](https://github.com/your-username/smartshopai/issues)
- **Discussions**: [Discussions](https://github.com/your-username/smartshopai/discussions)

## 🙏 Teşekkürler

- Spring Boot ekibine
- OpenAI ekibine
- MongoDB ve Redis topluluklarına
- Tüm katkıda bulunanlara

---

**SmartShopAI** - Akıllı alışveriş deneyimini devrimleştiriyoruz! 🚀
# smart-shop-ai
