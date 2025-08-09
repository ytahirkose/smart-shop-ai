# SmartShopAI - Enterprise-Level Mimari Planı (Spring AI + Gradle)
## Lead Java Mimarı Perspektifi - Detaylı Modül ve Teknoloji Planı

---

## 🏗️ **Genel Mimari Yaklaşım**

### **Mimari Prensipler**
- **Microservices Architecture**: Her modül bağımsız servis
- **Domain-Driven Design (DDD)**: İş mantığı odaklı tasarım
- **Event-Driven Architecture**: Asenkron iletişim
- **CQRS Pattern**: Command/Query Responsibility Segregation
- **Saga Pattern**: Distributed transaction management
- **Circuit Breaker Pattern**: Fault tolerance
- **API Gateway Pattern**: Centralized routing and security
- **Hexagonal Architecture**: Clean architecture principles
- **AI-First Architecture**: Spring AI ile AI-native tasarım

### **Enterprise Teknoloji Stack (En Güncel Versiyonlar)**
- **Java 21**: En son LTS sürümü
- **Spring Boot 3.5.0**: Mikroservis framework'ü
- **Spring AI 1.0.0**: AI-native development framework
- **Spring Cloud 2024.0.0**: Service discovery, configuration, circuit breaker
- **Spring Security 6.3.0**: Güvenlik framework'ü
- **MongoDB 8.0**: Ana veritabanı
- **Redis 7.4**: Cache ve session yönetimi
- **Apache Kafka 3.7.0**: Event streaming
- **Elasticsearch 8.13.0**: Arama ve log analizi
- **Docker 25.0**: Containerization
- **Kubernetes 1.30**: Orchestration
- **OpenAI API**: AI servisleri entegrasyonu
- **Prometheus 2.50.0**: Monitoring
- **Grafana 11.0.0**: Visualization
- **Jaeger 1.57.0**: Distributed tracing
- **Vector Database**: Pinecone/Weaviate (AI embeddings için)
- **LangChain4j 0.27.0**: AI workflow orchestration

---

## 📦 **Detaylı Modül Yapısı ve Açıklamalar**

### **1. API Gateway Module**
**Amaç**: Merkezi routing, güvenlik, rate limiting, load balancing, AI request routing

**Neden Gerekli**: 
- Tüm isteklerin tek noktadan geçmesi
- Merkezi güvenlik kontrolü
- Rate limiting ve DDoS koruması
- Load balancing ve service discovery
- AI request'lerinin yönlendirilmesi

**Teknolojiler**:
- Spring Cloud Gateway 4.2.0
- Spring Security 6.3.0
- Spring AI 1.0.0 (AI request handling)
- Resilience4j 3.0.0
- Redis 7.4 (Rate limiting)
- Bucket4j 8.8.0 (Rate limiting)
- WebFlux (Reactive programming)

**Dosya Yapısı ve Açıklamalar**:
```
smartshopai-gateway/
├── src/main/java/com/smartshopai/gateway/
│   ├── config/
│   │   ├── GatewayConfig.java
│   │   │   └── Amaç: Route tanımları, filter konfigürasyonu
│   │   ├── SecurityConfig.java
│   │   │   └── Amaç: JWT authentication, CORS, security headers
│   │   ├── RateLimitConfig.java
│   │   │   └── Amaç: Rate limiting kuralları, Redis bucket konfigürasyonu
│   │   └── CorsConfig.java
│   │       └── Amaç: Cross-origin resource sharing ayarları
│   ├── filter/
│   │   ├── AuthenticationFilter.java
│   │   │   └── Amaç: JWT token doğrulama, user context oluşturma
│   │   ├── RateLimitFilter.java
│   │   │   └── Amaç: Request rate kontrolü, bucket algorithm
│   │   ├── LoggingFilter.java
│   │   │   └── Amaç: Request/response logging, correlation ID
│   │   └── CorrelationFilter.java
│   │       └── Amaç: Distributed tracing için correlation ID ekleme
│   ├── exception/
│   │   ├── GatewayException.java
│   │   │   └── Amaç: Gateway-specific exception handling
│   │   └── GlobalExceptionHandler.java
│   │       └── Amaç: Merkezi exception handling, error response formatı
│   └── GatewayApplication.java
│       └── Amaç: Gateway servisinin başlangıç noktası
├── src/main/resources/
│   ├── application.yml
│   │   └── Amaç: Ana konfigürasyon dosyası
│   ├── application-dev.yml
│   │   └── Amaç: Development environment ayarları
│   ├── application-prod.yml
│   │   └── Amaç: Production environment ayarları
│   └── logback-spring.xml
│       └── Amaç: Logging konfigürasyonu
└── build.gradle
    └── Amaç: Gradle build konfigürasyonu
```

### **2. Service Discovery & Configuration Module**
**Amaç**: Servis keşfi, konfigürasyon yönetimi, load balancing

**Neden Gerekli**:
- Mikroservislerin birbirini bulabilmesi
- Merkezi konfigürasyon yönetimi
- Load balancing ve failover
- Service health monitoring

**Teknolojiler**:
- Spring Cloud Netflix Eureka 4.2.0
- Spring Cloud Config 4.2.0
- Spring Cloud Bus 4.2.0

**Dosya Yapısı ve Açıklamalar**:
```
smartshopai-discovery/
├── src/main/java/com/smartshopai/discovery/
│   ├── config/
│   │   ├── EurekaConfig.java
│   │   │   └── Amaç: Eureka server konfigürasyonu, service registry
│   │   ├── ConfigServerConfig.java
│   │   │   └── Amaç: Config server ayarları, Git backend
│   │   └── SecurityConfig.java
│   │       └── Amaç: Discovery servisinin güvenlik ayarları
│   ├── controller/
│   │   └── DiscoveryController.java
│   │       └── Amaç: Service discovery API endpoints
│   └── DiscoveryApplication.java
│       └── Amaç: Discovery servisinin başlangıç noktası
├── src/main/resources/
│   ├── application.yml
│   │   └── Amaç: Ana konfigürasyon dosyası
│   └── config/
│       ├── user-service.yml
│       │   └── Amaç: User service konfigürasyonu
│       ├── product-service.yml
│       │   └── Amaç: Product service konfigürasyonu
│       ├── ai-analysis-service.yml
│       │   └── Amaç: AI Analysis service konfigürasyonu
│       └── notification-service.yml
│           └── Amaç: Notification service konfigürasyonu
└── build.gradle
    └── Amaç: Gradle build konfigürasyonu
```

### **3. User Service Module**
**Amaç**: Kullanıcı yönetimi, authentication, authorization, profil yönetimi, AI-powered personalization

**Neden Gerekli**:
- Kullanıcı kayıt ve giriş işlemleri
- JWT token yönetimi
- Kullanıcı profil ve tercih yönetimi
- AI-powered davranış analizi ve öğrenme
- Kişiselleştirilmiş öneriler

**Teknolojiler**:
- Spring Boot 3.5.0
- Spring Security 6.3.0
- Spring AI 1.0.0 (AI-powered personalization)
- Spring Data MongoDB 4.2.0
- Redis 7.4 (Session/Cache)
- JWT 0.13.0
- MapStruct 1.6.0.Final
- Lombok 1.18.34
- SpringDoc OpenAPI 3.0.0
- LangChain4j 0.27.0 (AI workflow)

**Dosya Yapısı ve Açıklamalar**:
```
smartshopai-user-service/
├── src/main/java/com/smartshopai/user/
│   ├── domain/
│   │   ├── entity/
│   │   │   ├── User.java
│   │   │   │   └── Amaç: Kullanıcı ana entity'si, MongoDB document
│   │   │   ├── UserProfile.java
│   │   │   │   └── Amaç: Kullanıcı profil bilgileri
│   │   │   ├── UserPreferences.java
│   │   │   │   └── Amaç: Kullanıcı tercihleri (bütçe, kategori vb.)
│   │   │   └── UserBehaviorMetrics.java
│   │   │       └── Amaç: Kullanıcı davranış metrikleri
│   │   ├── repository/
│   │   │   ├── UserRepository.java
│   │   │   │   └── Amaç: User entity için MongoDB repository
│   │   │   └── UserProfileRepository.java
│   │   │       └── Amaç: UserProfile entity için repository
│   │   ├── service/
│   │   │   ├── UserService.java
│   │   │   │   └── Amaç: User domain business logic
│   │   │   ├── UserProfileService.java
│   │   │   │   └── Amaç: UserProfile domain business logic
│   │   │   ├── UserBehaviorService.java
│   │   │   │   └── Amaç: Kullanıcı davranış analizi
│   │   │   └── UserValidationService.java
│   │   │       └── Amaç: Kullanıcı veri doğrulama
│   │   └── event/
│   │       ├── UserCreatedEvent.java
│   │       │   └── Amaç: Kullanıcı oluşturulduğunda yayınlanan event
│   │       ├── UserUpdatedEvent.java
│   │       │   └── Amaç: Kullanıcı güncellendiğinde yayınlanan event
│   │       └── UserEventPublisher.java
│   │           └── Amaç: User domain event'lerini yayınlama
│   ├── application/
│   │   ├── dto/
│   │   │   ├── request/
│   │   │   │   ├── CreateUserRequest.java
│   │   │   │   │   └── Amaç: Kullanıcı oluşturma request DTO'su
│   │   │   │   ├── UpdateProfileRequest.java
│   │   │   │   │   └── Amaç: Profil güncelleme request DTO'su
│   │   │   │   └── LoginRequest.java
│   │   │   │       └── Amaç: Giriş request DTO'su
│   │   │   └── response/
│   │   │       ├── UserResponse.java
│   │   │       │   └── Amaç: User response DTO'su
│   │   │       ├── UserProfileResponse.java
│   │   │       │   └── Amaç: UserProfile response DTO'su
│   │   │       └── LoginResponse.java
│   │   │           └── Amaç: Login response DTO'su (JWT token dahil)
│   │   ├── mapper/
│   │   │   ├── UserMapper.java
│   │   │   │   └── Amaç: User entity-DTO mapping
│   │   │   └── UserProfileMapper.java
│   │   │       └── Amaç: UserProfile entity-DTO mapping
│   │   └── service/
│   │       ├── UserApplicationService.java
│   │       │   └── Amaç: User application business logic
│   │       └── UserQueryService.java
│   │           └── Amaç: User query operations (CQRS)
│   ├── infrastructure/
│   │   ├── config/
│   │   │   ├── SecurityConfig.java
│   │   │   │   └── Amaç: Spring Security konfigürasyonu
│   │   │   ├── MongoConfig.java
│   │   │   │   └── Amaç: MongoDB connection ve repository konfigürasyonu
│   │   │   ├── RedisConfig.java
│   │   │   │   └── Amaç: Redis cache konfigürasyonu
│   │   │   └── JwtConfig.java
│   │   │       └── Amaç: JWT token konfigürasyonu
│   │   ├── security/
│   │   │   ├── JwtTokenProvider.java
│   │   │   │   └── Amaç: JWT token oluşturma ve doğrulama
│   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   └── Amaç: JWT authentication filter
│   │   │   └── PasswordEncoder.java
│   │   │       └── Amaç: Şifre encoding (BCrypt)
│   │   ├── cache/
│   │   │   ├── UserCacheService.java
│   │   │   │   └── Amaç: User cache operations
│   │   │   └── SessionCacheService.java
│   │   │       └── Amaç: Session cache operations
│   │   └── external/
│   │       ├── NotificationServiceClient.java
│   │       │   └── Amaç: Notification service ile iletişim
│   │       └── AnalyticsServiceClient.java
│   │           └── Amaç: Analytics service ile iletişim
│   ├── presentation/
│   │   ├── controller/
│   │   │   ├── UserController.java
│   │   │   │   └── Amaç: User REST API endpoints
│   │   │   ├── AuthController.java
│   │   │   │   └── Amaç: Authentication REST API endpoints
│   │   │   └── ProfileController.java
│   │   │       └── Amaç: Profile REST API endpoints
│   │   ├── exception/
│   │   │   ├── UserNotFoundException.java
│   │   │   │   └── Amaç: User bulunamadığında exception
│   │   │   ├── UserAlreadyExistsException.java
│   │   │   │   └── Amaç: User zaten var olduğunda exception
│   │   │   └── GlobalExceptionHandler.java
│   │   │       └── Amaç: Merkezi exception handling
│   │   └── validation/
│   │       ├── UserRequestValidator.java
│   │       │   └── Amaç: User request validation
│   │       └── ProfileRequestValidator.java
│   │           └── Amaç: Profile request validation
│   └── UserServiceApplication.java
│       └── Amaç: User service başlangıç noktası
├── src/main/resources/
│   ├── application.yml
│   │   └── Amaç: Ana konfigürasyon dosyası
│   ├── application-dev.yml
│   │   └── Amaç: Development environment ayarları
│   ├── application-prod.yml
│   │   └── Amaç: Production environment ayarları
│   └── logback-spring.xml
│       └── Amaç: Logging konfigürasyonu
├── src/test/java/
│   ├── unit/
│   │   ├── service/
│   │   │   ├── UserServiceTest.java
│   │   │   │   └── Amaç: UserService unit testleri
│   │   │   └── UserProfileServiceTest.java
│   │   │       └── Amaç: UserProfileService unit testleri
│   │   └── mapper/
│   │       └── UserMapperTest.java
│   │           └── Amaç: UserMapper unit testleri
│   ├── integration/
│   │   ├── controller/
│   │   │   └── UserControllerIntegrationTest.java
│   │   │       └── Amaç: UserController integration testleri
│   │   └── repository/
│   │       └── UserRepositoryIntegrationTest.java
│   │           └── Amaç: UserRepository integration testleri
│   └── testcontainers/
│       └── UserServiceTestcontainersTest.java
│           └── Amaç: Testcontainers ile integration testleri
└── build.gradle
    └── Amaç: Gradle build konfigürasyonu
```

### **4. Product Service Module**
**Amaç**: Ürün verisi toplama, yönetimi, AI-powered analizi, karşılaştırma

**Neden Gerekli**:
- E-ticaret sitelerinden ürün verisi toplama
- AI-powered ürün karşılaştırma ve analiz
- Fiyat takibi ve geçmişi
- AI-powered ürün önerileri için veri sağlama
- Ürün embedding'leri ve semantic search

**Teknolojiler**:
- Spring Boot 3.5.0
- Spring AI 1.0.0 (AI-powered analysis)
- Spring Data MongoDB 4.2.0
- Redis 7.4 (Cache)
- WebClient (HTTP client)
- Selenium 4.18.0 (Web scraping)
- Playwright 1.42.0 (Modern web scraping)
- MapStruct 1.6.0.Final
- Lombok 1.18.34
- LangChain4j 0.27.0 (AI workflow)
- Vector Database (Pinecone/Weaviate)

**Dosya Yapısı ve Açıklamalar**:
```
smartshopai-product-service/
├── src/main/java/com/smartshopai/product/
│   ├── domain/
│   │   ├── entity/
│   │   │   ├── Product.java
│   │   │   │   └── Amaç: Ürün ana entity'si
│   │   │   ├── ProductSpecifications.java
│   │   │   │   └── Amaç: Ürün teknik özellikleri
│   │   │   ├── ProductImage.java
│   │   │   │   └── Amaç: Ürün görselleri
│   │   │   ├── PriceHistory.java
│   │   │   │   └── Amaç: Fiyat geçmişi
│   │   │   ├── Review.java
│   │   │   │   └── Amaç: Kullanıcı yorumları
│   │   │   └── ProductMetrics.java
│   │   │       └── Amaç: Ürün metrikleri (rating, satış vb.)
│   │   ├── repository/
│   │   │   ├── ProductRepository.java
│   │   │   │   └── Amaç: Product entity için MongoDB repository
│   │   │   ├── PriceHistoryRepository.java
│   │   │   │   └── Amaç: PriceHistory entity için repository
│   │   │   └── ReviewRepository.java
│   │   │       └── Amaç: Review entity için repository
│   │   ├── service/
│   │   │   ├── ProductService.java
│   │   │   │   └── Amaç: Product domain business logic
│   │   │   ├── ProductDataCollectorService.java
│   │   │   │   └── Amaç: E-ticaret sitelerinden veri toplama
│   │   │   ├── ProductValidatorService.java
│   │   │   │   └── Amaç: Ürün verisi doğrulama
│   │   │   └── ProductComparisonService.java
│   │   │       └── Amaç: Ürün karşılaştırma işlemleri
│   │   └── event/
│   │       ├── ProductCreatedEvent.java
│   │       │   └── Amaç: Ürün oluşturulduğunda yayınlanan event
│   │       ├── ProductUpdatedEvent.java
│   │       │   └── Amaç: Ürün güncellendiğinde yayınlanan event
│   │       └── ProductEventPublisher.java
│   │           └── Amaç: Product domain event'lerini yayınlama
│   ├── application/
│   │   ├── dto/
│   │   │   ├── request/
│   │   │   │   ├── ProductAnalysisRequest.java
│   │   │   │   │   └── Amaç: Ürün analizi request DTO'su
│   │   │   │   ├── ProductComparisonRequest.java
│   │   │   │   │   └── Amaç: Ürün karşılaştırma request DTO'su
│   │   │   │   └── ProductSearchRequest.java
│   │   │   │       └── Amaç: Ürün arama request DTO'su
│   │   │   └── response/
│   │   │       ├── ProductResponse.java
│   │   │       │   └── Amaç: Product response DTO'su
│   │   │       ├── ProductAnalysisResponse.java
│   │   │       │   └── Amaç: Ürün analizi response DTO'su
│   │   │       └── ComparisonResponse.java
│   │   │           └── Amaç: Karşılaştırma response DTO'su
│   │   ├── mapper/
│   │   │   ├── ProductMapper.java
│   │   │   │   └── Amaç: Product entity-DTO mapping
│   │   │   └── ComparisonMapper.java
│   │   │       └── Amaç: Comparison entity-DTO mapping
│   │   └── service/
│   │       ├── ProductApplicationService.java
│   │       │   └── Amaç: Product application business logic
│   │       └── ProductQueryService.java
│   │           └── Amaç: Product query operations (CQRS)
│   ├── infrastructure/
│   │   ├── config/
│   │   │   ├── MongoConfig.java
│   │   │   │   └── Amaç: MongoDB connection konfigürasyonu
│   │   │   ├── RedisConfig.java
│   │   │   │   └── Amaç: Redis cache konfigürasyonu
│   │   │   ├── WebClientConfig.java
│   │   │   │   └── Amaç: WebClient konfigürasyonu
│   │   │   └── CacheConfig.java
│   │   │       └── Amaç: Cache konfigürasyonu
│   │   ├── cache/
│   │   │   ├── ProductCacheService.java
│   │   │   │   └── Amaç: Product cache operations
│   │   │   └── ComparisonCacheService.java
│   │   │       └── Amaç: Comparison cache operations
│   │   ├── external/
│   │   │   ├── EcommerceApiClient.java
│   │   │   │   └── Amaç: Genel e-ticaret API client'ı
│   │   │   ├── TrendyolApiClient.java
│   │   │   │   └── Amaç: Trendyol API entegrasyonu
│   │   │   ├── HepsiburadaApiClient.java
│   │   │   │   └── Amaç: Hepsiburada API entegrasyonu
│   │   │   └── AmazonApiClient.java
│   │   │       └── Amaç: Amazon API entegrasyonu
│   │   └── scraper/
│   │       ├── WebScraperService.java
│   │       │   └── Amaç: Web scraping işlemleri
│   │       ├── PlaywrightScraperService.java
│   │       │   └── Amaç: Modern web scraping (Playwright)
│   │       ├── ProductDataExtractor.java
│   │       │   └── Amaç: Ürün verisi çıkarma
│   │       └── PriceExtractor.java
│   │           └── Amaç: Fiyat verisi çıkarma
│   ├── presentation/
│   │   ├── controller/
│   │   │   ├── ProductController.java
│   │   │   │   └── Amaç: Product REST API endpoints
│   │   │   ├── ComparisonController.java
│   │   │   │   └── Amaç: Comparison REST API endpoints
│   │   │   └── SearchController.java
│   │   │       └── Amaç: Search REST API endpoints
│   │   ├── exception/
│   │   │   ├── ProductNotFoundException.java
│   │   │   │   └── Amaç: Product bulunamadığında exception
│   │   │   ├── ProductAnalysisException.java
│   │   │   │   └── Amaç: Ürün analizi hatası exception'ı
│   │   │   └── GlobalExceptionHandler.java
│   │   │       └── Amaç: Merkezi exception handling
│   │   └── validation/
│   │       ├── ProductRequestValidator.java
│   │       │   └── Amaç: Product request validation
│   │       └── ComparisonRequestValidator.java
│   │           └── Amaç: Comparison request validation
│   └── ProductServiceApplication.java
│       └── Amaç: Product service başlangıç noktası
├── src/main/resources/
│   ├── application.yml
│   │   └── Amaç: Ana konfigürasyon dosyası
│   ├── application-dev.yml
│   │   └── Amaç: Development environment ayarları
│   ├── application-prod.yml
│   │   └── Amaç: Production environment ayarları
│   └── logback-spring.xml
│       └── Amaç: Logging konfigürasyonu
├── src/test/java/
│   ├── unit/
│   │   ├── service/
│   │   │   ├── ProductServiceTest.java
│   │   │   │   └── Amaç: ProductService unit testleri
│   │   │   └── ProductDataCollectorServiceTest.java
│   │   │       └── Amaç: ProductDataCollectorService unit testleri
│   │   └── mapper/
│   │       └── ProductMapperTest.java
│   │           └── Amaç: ProductMapper unit testleri
│   ├── integration/
│   │   ├── controller/
│   │   │   └── ProductControllerIntegrationTest.java
│   │   │       └── Amaç: ProductController integration testleri
│   │   └── repository/
│   │       └── ProductRepositoryIntegrationTest.java
│   │           └── Amaç: ProductRepository integration testleri
│   └── testcontainers/
│       └── ProductServiceTestcontainersTest.java
│           └── Amaç: Testcontainers ile integration testleri
└── build.gradle
    └── Amaç: Gradle build konfigürasyonu
```

---

## 📦 **Yeni AI-Focused Modüller**

### **5. AI Analysis Service Module**
**Amaç**: AI-powered ürün analizi, öneriler, teknik detay çevirisi, semantic search

**Neden Gerekli**:
- Ürün teknik özelliklerinin anlaşılır hale getirilmesi
- AI-powered ürün önerileri
- Fiyat-kalite analizi
- Alternatif ürün önerileri
- Semantic search ve embedding'ler
- AI-powered fiyat tahminleri

**Teknolojiler**:
- Spring Boot 3.5.0
- Spring AI 1.0.0 (Core AI framework)
- OpenAI API (GPT-4, GPT-3.5)
- LangChain4j 0.27.0 (AI workflow orchestration)
- Spring Data MongoDB 4.2.0
- Redis 7.4 (Cache)
- MapStruct 1.6.0.Final
- Lombok 1.18.34
- Vector Database (Pinecone/Weaviate)
- Spring Data Elasticsearch 5.2.0

### **6. AI Recommendation Service Module**
**Amaç**: AI-powered kişiselleştirilmiş öneriler, collaborative filtering

**Neden Gerekli**:
- Kullanıcı davranış analizi
- Kişiselleştirilmiş ürün önerileri
- Collaborative filtering
- Real-time öneri güncellemeleri

**Teknolojiler**:
- Spring Boot 3.5.0
- Spring AI 1.0.0
- LangChain4j 0.27.0
- Redis 7.4 (Real-time data)
- MongoDB 8.0 (User behavior data)
- Vector Database (Embeddings)

### **7. AI Search Service Module**
**Amaç**: Semantic search, AI-powered arama, natural language processing

**Neden Gerekli**:
- Doğal dil ile ürün arama
- Semantic similarity search
- AI-powered filtreleme
- Context-aware arama

**Teknolojiler**:
- Spring Boot 3.5.0
- Spring AI 1.0.0
- Elasticsearch 8.13.0
- Vector Database
- LangChain4j 0.27.0

---

## 📦 **Gradle Build Konfigürasyonu (Spring AI)**

### **Root build.gradle**
```gradle
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.5.0'
    id 'io.spring.dependency-management' version '1.1.6'
    id 'org.graalvm.buildtools.native' version '0.10.0'
    id 'org.sonarqube' version '4.4.0.3373'
    id 'jacoco' version '0.8.11'
}

group = 'com.smartshopai'
version = '1.0.0-SNAPSHOT'
sourceCompatibility = '21'

repositories {
    mavenCentral()
    maven { url 'https://repo.spring.io/milestone' }
    maven { url 'https://repo.spring.io/snapshot' }
}

ext {
    set('springCloudVersion', "2024.0.0")
    set('springBootVersion', "3.5.0")
    set('springAiVersion', "1.0.0")
    set('springSecurityVersion', "6.3.0")
    set('mongodbVersion', "8.0.0")
    set('redisVersion', "7.4.0")
    set('kafkaVersion', "3.7.0")
    set('elasticsearchVersion', "8.13.0")
    set('openaiVersion', "0.20.0")
    set('jwtVersion', "0.13.0")
    set('mapstructVersion', "1.6.0.Final")
    set('lombokVersion', "1.18.34")
    set('micrometerVersion', "1.13.0")
    set('resilience4jVersion', "3.0.0")
    set('testcontainersVersion', "1.20.0")
    set('seleniumVersion', "4.18.0")
    set('playwrightVersion', "1.42.0")
    set('bucket4jVersion', "8.8.0")
    set('springdocVersion', "3.0.0")
    set('langchain4jVersion', "0.27.0")
    set('pineconeVersion', "0.1.0")
    set('weaviateVersion', "4.4.0")
}

dependencyManagement {
    imports {
        mavenBom "org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"
    }
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-mongodb'
    implementation 'org.springframework.boot:spring-boot-starter-data-redis'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-actuator'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    implementation 'org.springframework.boot:spring-boot-starter-aop'
    implementation 'org.springframework.boot:spring-boot-starter-webflux'
    
    // Spring AI
    implementation "org.springframework.ai:spring-ai-openai-spring-boot-starter:${springAiVersion}"
    implementation "org.springframework.ai:spring-ai-ollama-spring-boot-starter:${springAiVersion}"
    implementation "org.springframework.ai:spring-ai-vertex-ai-spring-boot-starter:${springAiVersion}"
    implementation "org.springframework.ai:spring-ai-azure-openai-spring-boot-starter:${springAiVersion}"
    
    // Spring Cloud
    implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-client'
    implementation 'org.springframework.cloud:spring-cloud-starter-config'
    implementation 'org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j'
    implementation 'org.springframework.cloud:spring-cloud-starter-gateway'
    
    // Kafka
    implementation 'org.springframework.kafka:spring-kafka'
    
    // JWT
    implementation "io.jsonwebtoken:jjwt-api:${jwtVersion}"
    implementation "io.jsonwebtoken:jjwt-impl:${jwtVersion}"
    implementation "io.jsonwebtoken:jjwt-jackson:${jwtVersion}"
    
    // LangChain4j
    implementation "dev.langchain4j:langchain4j:${langchain4jVersion}"
    implementation "dev.langchain4j:langchain4j-open-ai:${langchain4jVersion}"
    implementation "dev.langchain4j:langchain4j-embeddings-all-minilm-l6-v2:${langchain4jVersion}"
    
    // Vector Database
    implementation "io.pinecone:pinecone-client:${pineconeVersion}"
    implementation "io.weaviate:weaviate-client:${weaviateVersion}"
    
    // Elasticsearch
    implementation "co.elastic.clients:elasticsearch-java:${elasticsearchVersion}"
    
    // Lombok
    compileOnly "org.projectlombok:lombok:${lombokVersion}"
    annotationProcessor "org.projectlombok:lombok:${lombokVersion}"
    
    // MapStruct
    implementation "org.mapstruct:mapstruct:${mapstructVersion}"
    annotationProcessor "org.mapstruct:mapstruct-processor:${mapstructVersion}"
    
    // Rate Limiting
    implementation "com.bucket4j:bucket4j-redis:${bucket4jVersion}"
    
    // Web Scraping
    implementation "org.seleniumhq.selenium:selenium-java:${seleniumVersion}"
    implementation "com.microsoft.playwright:playwright:${playwrightVersion}"
    
    // API Documentation
    implementation "org.springdoc:springdoc-openapi-starter-webmvc-ui:${springdocVersion}"
    
    // Test Dependencies
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation "org.testcontainers:mongodb:${testcontainersVersion}"
    testImplementation "org.testcontainers:elasticsearch:${testcontainersVersion}"
    testImplementation "org.testcontainers:kafka:${testcontainersVersion}"
    testImplementation "org.testcontainers:junit-jupiter:${testcontainersVersion}"
}

test {
    useJUnitPlatform()
    finalizedBy jacocoTestReport
}

jacocoTestReport {
    dependsOn test
    reports {
        xml.required = true
        html.required = true
    }
}

sonarqube {
    properties {
        property "sonar.projectKey", "smartshopai"
        property "sonar.organization", "smartshopai"
        property "sonar.host.url", "http://localhost:9000"
    }
}
```

### **AI Service build.gradle (AI Analysis Service Example)**
```gradle
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.5.0'
    id 'io.spring.dependency-management' version '1.1.6'
}

group = 'com.smartshopai'
version = '1.0.0-SNAPSHOT'
sourceCompatibility = '21'

repositories {
    mavenCentral()
    maven { url 'https://repo.spring.io/snapshot' }
}

dependencies {
    implementation project(':smartshopai-common')
    implementation project(':smartshopai-security')
    
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-mongodb'
    implementation 'org.springframework.boot:spring-boot-starter-data-redis'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-actuator'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    
    // Spring AI
    implementation "org.springframework.ai:spring-ai-openai-spring-boot-starter:${springAiVersion}"
    implementation "org.springframework.ai:spring-ai-ollama-spring-boot-starter:${springAiVersion}"
    
    // LangChain4j
    implementation "dev.langchain4j:langchain4j:${langchain4jVersion}"
    implementation "dev.langchain4j:langchain4j-open-ai:${langchain4jVersion}"
    implementation "dev.langchain4j:langchain4j-embeddings-all-minilm-l6-v2:${langchain4jVersion}"
    
    // Vector Database
    implementation "io.pinecone:pinecone-client:${pineconeVersion}"
    implementation "io.weaviate:weaviate-client:${weaviateVersion}"
    
    // Spring Cloud
    implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-client'
    implementation 'org.springframework.cloud:spring-cloud-starter-config'
    implementation 'org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j'
    
    // JWT
    implementation "io.jsonwebtoken:jjwt-api:${jwtVersion}"
    implementation "io.jsonwebtoken:jjwt-impl:${jwtVersion}"
    implementation "io.jsonwebtoken:jjwt-jackson:${jwtVersion}"
    
    // Lombok
    compileOnly "org.projectlombok:lombok:${lombokVersion}"
    annotationProcessor "org.projectlombok:lombok:${lombokVersion}"
    
    // MapStruct
    implementation "org.mapstruct:mapstruct:${mapstructVersion}"
    annotationProcessor "org.mapstruct:mapstruct-processor:${mapstructVersion}"
    
    // API Documentation
    implementation "org.springdoc:springdoc-openapi-starter-webmvc-ui:${springdocVersion}"
    
    // Test Dependencies
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation "org.testcontainers:mongodb:${testcontainersVersion}"
}

test {
    useJUnitPlatform()
}
```

### **settings.gradle**
```gradle
rootProject.name = 'smartshopai'

include 'smartshopai-gateway'
include 'smartshopai-discovery'
include 'smartshopai-user-service'
include 'smartshopai-product-service'
include 'smartshopai-ai-analysis-service'
include 'smartshopai-ai-recommendation-service'
include 'smartshopai-ai-search-service'
include 'smartshopai-notification-service'
include 'smartshopai-search-service'
include 'smartshopai-monitoring'
include 'smartshopai-common'
include 'smartshopai-security'
```

---

## 🚀 **Spring AI Integration Examples**

### **AI Analysis Service - Spring AI Usage**
```java
@Service
public class ProductAnalysisService {
    
    private final AiClient aiClient;
    private final EmbeddingClient embeddingClient;
    
    public ProductAnalysisService(AiClient aiClient, EmbeddingClient embeddingClient) {
        this.aiClient = aiClient;
        this.embeddingClient = embeddingClient;
    }
    
    public ProductAnalysis analyzeProduct(Product product) {
        // Spring AI ile ürün analizi
        String prompt = String.format("""
            Bu ürünün teknik özelliklerini analiz et ve anlaşılır hale getir:
            Ürün: %s
            Fiyat: %s
            Özellikler: %s
            """, product.getName(), product.getPrice(), product.getSpecifications());
        
        AiResponse response = aiClient.generate(prompt);
        
        // Embedding oluştur
        Embedding embedding = embeddingClient.embed(product.getDescription());
        
        return ProductAnalysis.builder()
            .productId(product.getId())
            .analysis(response.getResult().getOutput().getContent())
            .embedding(embedding.getEmbedding())
            .build();
    }
}
```

### **AI Recommendation Service - LangChain4j Integration**
```java
@Service
public class RecommendationService {
    
    private final OpenAiChatModel chatModel;
    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;
    
    public List<Product> getPersonalizedRecommendations(User user, List<Product> products) {
        // Kullanıcı embedding'i
        Embedding userEmbedding = embeddingModel.embed(user.getPreferences());
        
        // Benzer ürünleri bul
        List<EmbeddingMatch<Product>> similarProducts = vectorStore.findRelevant(
            userEmbedding, 
            10, 
            0.8
        );
        
        // AI ile öneri oluştur
        String prompt = String.format("""
            Kullanıcının tercihleri: %s
            Benzer ürünler: %s
            Bu kullanıcı için en iyi 5 ürün önerisi yap.
            """, user.getPreferences(), similarProducts);
        
        String recommendation = chatModel.generate(prompt).content();
        
        return processRecommendations(recommendation, similarProducts);
    }
}
```

Bu güncellenmiş plan artık Spring AI ile AI-native bir mimari sunuyor ve en güncel teknolojileri kullanıyor.

Bu güncellenmiş plan, Spring Boot 3.5.0 ile en güncel teknoloji versiyonlarını kullanıyor ve doğru Gradle yapısını içeriyor. Tüm XML referansları kaldırıldı ve modern alternatifler eklendi.
