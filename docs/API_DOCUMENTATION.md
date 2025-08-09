# SmartShopAI API Documentation

## 📋 **Genel Bakış**

SmartShopAI, AI destekli ürün değerlendirme ve alternatif ürün önerisi sistemi için RESTful API'ları sağlar. Bu dokümantasyon, tüm API endpoint'lerini, request/response formatlarını ve kullanım örneklerini içerir.

---

## 🔐 **Authentication & Authorization**

### **JWT Token Authentication**

Tüm API istekleri (public endpoint'ler hariç) JWT token gerektirir.

```bash
# Token alma
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "user@example.com",
  "password": "password123"
}

# Response
{
  "success": true,
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "refresh-token-here",
    "expiresIn": 86400000
  },
  "message": "Login successful"
}
```

### **Authorization Headers**

```bash
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### **Role-Based Access Control**

- **USER**: Temel kullanıcı yetkileri
- **ADMIN**: Yönetici yetkileri
- **ANALYST**: AI analiz yetkileri

---

## 📊 **API Endpoints**

### **1. Authentication Endpoints**

#### **1.1 User Registration**
```http
POST /api/v1/auth/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "SecurePassword123!",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+905551234567",
  "preferredCategories": ["electronics", "books"],
  "maxBudget": 5000.0,
  "preferredBrands": "Apple,Samsung",
  "shoppingPreferences": "quality_over_price"
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "id": "user-123",
    "username": "john_doe",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "status": "ACTIVE",
    "createdAt": "2024-01-15T10:30:00Z"
  },
  "message": "User registered successfully"
}
```

#### **1.2 User Login**
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "john@example.com",
  "password": "SecurePassword123!"
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "refresh-token-here",
    "user": {
      "id": "user-123",
      "username": "john_doe",
      "email": "john@example.com",
      "firstName": "John",
      "lastName": "Doe"
    }
  },
  "message": "Login successful"
}
```

#### **1.3 Token Refresh**
```http
POST /api/v1/auth/refresh
Content-Type: application/json

{
  "refreshToken": "refresh-token-here"
}
```

### **2. User Management Endpoints**

#### **2.1 Get User Profile**
```http
GET /api/v1/users/profile
Authorization: Bearer <token>
```

**Response:**
```json
{
  "success": true,
  "data": {
    "id": "user-123",
    "username": "john_doe",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "+905551234567",
    "preferredCategories": ["electronics", "books"],
    "maxBudget": 5000.0,
    "preferredBrands": "Apple,Samsung",
    "shoppingPreferences": "quality_over_price",
    "lastLoginAt": "2024-01-15T10:30:00Z",
    "createdAt": "2024-01-01T00:00:00Z"
  },
  "message": "User profile retrieved successfully"
}
```

#### **2.2 Update User Profile**
```http
PUT /api/v1/users/profile
Authorization: Bearer <token>
Content-Type: application/json

{
  "firstName": "John Updated",
  "lastName": "Doe Updated",
  "phoneNumber": "+905559876543",
  "preferredCategories": ["electronics", "books", "clothing"],
  "maxBudget": 7500.0,
  "preferredBrands": "Apple,Samsung,Nike",
  "shoppingPreferences": "balanced"
}
```

#### **2.3 Change Password**
```http
PUT /api/v1/users/password
Authorization: Bearer <token>
Content-Type: application/json

{
  "currentPassword": "OldPassword123!",
  "newPassword": "NewSecurePassword456!"
}
```

### **3. Product Management Endpoints**

#### **3.1 Get All Products**
```http
GET /api/v1/products?page=0&size=20&sort=price,asc&category=electronics
Authorization: Bearer <token>
```

**Response:**
```json
{
  "success": true,
  "data": {
    "content": [
      {
        "id": "product-123",
        "name": "iPhone 15 Pro",
        "brand": "Apple",
        "category": "electronics",
        "price": 999.99,
        "originalPrice": 1099.99,
        "discount": 9.09,
        "rating": 4.8,
        "reviewCount": 1250,
        "description": "Latest iPhone with advanced features",
        "features": ["5G", "A17 Pro", "48MP Camera"],
        "specifications": {
          "storage": "256GB",
          "color": "Titanium",
          "screen": "6.1 inch"
        },
        "images": [
          "https://example.com/iphone15pro-1.jpg",
          "https://example.com/iphone15pro-2.jpg"
        ],
        "availability": "IN_STOCK",
        "stockQuantity": 50,
        "shippingCost": 0.0,
        "warranty": "1 Year",
        "seller": "Apple Store",
        "createdAt": "2024-01-15T10:30:00Z"
      }
    ],
    "totalElements": 150,
    "totalPages": 8,
    "currentPage": 0,
    "size": 20
  },
  "message": "Products retrieved successfully"
}
```

#### **3.2 Get Product by ID**
```http
GET /api/v1/products/{productId}
Authorization: Bearer <token>
```

#### **3.3 Search Products**
```http
POST /api/v1/products/search
Authorization: Bearer <token>
Content-Type: application/json

{
  "query": "iPhone 15 Pro",
  "categories": ["electronics"],
  "brands": ["Apple"],
  "minPrice": 500.0,
  "maxPrice": 1500.0,
  "sortBy": "RELEVANCE",
  "filters": {
    "storage": "256GB",
    "color": "Titanium"
  }
}
```

#### **3.4 Create Product (Admin Only)**
```http
POST /api/v1/products
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "New Product",
  "brand": "Brand Name",
  "category": "electronics",
  "price": 299.99,
  "description": "Product description",
  "features": ["Feature 1", "Feature 2"],
  "specifications": {
    "spec1": "value1",
    "spec2": "value2"
  },
  "images": ["url1", "url2"],
  "stockQuantity": 100,
  "shippingCost": 10.0,
  "warranty": "2 Years"
}
```

### **4. AI Analysis Endpoints**

#### **4.1 Analyze Product**
```http
POST /api/v1/ai/analysis
Authorization: Bearer <token>
Content-Type: application/json

{
  "productId": "product-123",
  "analysisType": "COMPREHENSIVE",
  "userId": "user-123",
  "context": "Looking for best value smartphone",
  "preferences": {
    "budget": 1000.0,
    "priorities": ["camera", "battery", "performance"]
  }
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "id": "analysis-123",
    "productId": "product-123",
    "userId": "user-123",
    "analysisType": "COMPREHENSIVE",
    "summary": "iPhone 15 Pro offers excellent value for money...",
    "score": 8.5,
    "pros": [
      "Excellent camera quality",
      "Powerful A17 Pro chip",
      "Premium build quality"
    ],
    "cons": [
      "High price point",
      "Limited storage options"
    ],
    "recommendations": [
      "Consider 256GB model for future-proofing",
      "Look for carrier deals to reduce cost"
    ],
    "priceAnalysis": {
      "currentPrice": 999.99,
      "marketAverage": 1050.0,
      "priceTrend": "DECREASING",
      "bestTimeToBuy": "Black Friday"
    },
    "alternatives": [
      {
        "productId": "product-456",
        "name": "Samsung Galaxy S24",
        "price": 899.99,
        "score": 8.2,
        "reason": "Similar features at lower price"
      }
    ],
    "aiModelUsed": "gpt-4",
    "confidenceScore": 0.92,
    "processingTimeMs": 2500,
    "createdAt": "2024-01-15T10:30:00Z"
  },
  "message": "Product analysis completed successfully"
}
```

#### **4.2 Get Analysis History**
```http
GET /api/v1/ai/analysis/history?page=0&size=10
Authorization: Bearer <token>
```

#### **4.3 Compare Products**
```http
POST /api/v1/ai/analysis/compare
Authorization: Bearer <token>
Content-Type: application/json

{
  "productIds": ["product-123", "product-456", "product-789"],
  "comparisonCriteria": ["price", "performance", "camera", "battery"],
  "userId": "user-123"
}
```

### **5. AI Recommendation Endpoints**

#### **5.1 Generate Recommendations**
```http
POST /api/v1/ai/recommendations
Authorization: Bearer <token>
Content-Type: application/json

{
  "userId": "user-123",
  "requestType": "PERSONALIZED",
  "context": "Looking for smartphone under $1000",
  "preferredCategories": ["electronics"],
  "preferredBrands": ["Apple", "Samsung"],
  "maxBudget": 1000.0,
  "shoppingPreferences": "quality_over_price",
  "limit": 10,
  "sortBy": "RELEVANCE"
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "id": "recommendation-123",
    "userId": "user-123",
    "requestType": "PERSONALIZED",
    "context": "Looking for smartphone under $1000",
    "recommendedProducts": [
      {
        "productId": "product-123",
        "productName": "iPhone 15 Pro",
        "brand": "Apple",
        "category": "electronics",
        "price": 999.99,
        "score": 9.2,
        "reason": "Best camera and performance in budget",
        "features": ["5G", "A17 Pro", "48MP Camera"],
        "metadata": {
          "matchScore": 0.95,
          "priceScore": 0.88,
          "featureScore": 0.92
        }
      }
    ],
    "recommendationSummary": "Based on your preferences, we recommend...",
    "reasoning": "Analysis of your shopping history and preferences...",
    "confidenceScore": 0.89,
    "aiModelUsed": "gpt-4",
    "tokensUsed": 1500,
    "processingTimeMs": 3200,
    "createdAt": "2024-01-15T10:30:00Z"
  },
  "message": "Recommendations generated successfully"
}
```

#### **5.2 Get Similar Products**
```http
GET /api/v1/ai/recommendations/similar/{productId}?limit=5
Authorization: Bearer <token>
```

#### **5.3 Get Trending Products**
```http
GET /api/v1/ai/recommendations/trending
Authorization: Bearer <token>
```

#### **5.4 Get Deal Recommendations**
```http
GET /api/v1/ai/recommendations/deals
Authorization: Bearer <token>
```

### **6. AI Search Endpoints**

#### **6.1 Semantic Search**
```http
POST /api/v1/ai/search
Authorization: Bearer <token>
Content-Type: application/json

{
  "query": "best camera phone under $800",
  "categories": ["electronics"],
  "brands": ["Apple", "Samsung", "Google"],
  "minPrice": 500.0,
  "maxPrice": 800.0,
  "sortBy": "RELEVANCE",
  "limit": 20
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "id": "search-123",
    "query": "best camera phone under $800",
    "results": [
      {
        "productId": "product-123",
        "productName": "iPhone 14",
        "brand": "Apple",
        "category": "electronics",
        "price": 799.99,
        "score": 0.95,
        "highlights": [
          "Excellent camera system",
          "Great value for money",
          "Reliable performance"
        ],
        "semanticMatch": "High relevance to camera requirements"
      }
    ],
    "totalResults": 15,
    "searchSummary": "Found 15 products matching your criteria",
    "aiModelUsed": "gpt-4",
    "tokensUsed": 800,
    "processingTimeMs": 1500,
    "createdAt": "2024-01-15T10:30:00Z"
  },
  "message": "Search completed successfully"
}
```

#### **6.2 Advanced Search**
```http
POST /api/v1/ai/search/advanced
Authorization: Bearer <token>
Content-Type: application/json

{
  "query": "smartphone with good battery life",
  "filters": {
    "priceRange": {"min": 300, "max": 1000},
    "brands": ["Apple", "Samsung"],
    "features": ["5G", "Wireless Charging"],
    "rating": {"min": 4.0}
  },
  "sortBy": "BATTERY_LIFE",
  "limit": 10
}
```

### **7. Notification Endpoints**

#### **7.1 Send Notification**
```http
POST /api/v1/notifications/send
Authorization: Bearer <token>
Content-Type: application/json

{
  "userId": "user-123",
  "type": "EMAIL",
  "title": "Price Drop Alert",
  "message": "iPhone 15 Pro price has dropped by 10%!",
  "content": "Your watched product is now available at a better price...",
  "recipientEmail": "user@example.com",
  "category": "PROMOTIONAL",
  "priority": "MEDIUM"
}
```

#### **7.2 Get User Notifications**
```http
GET /api/v1/notifications/user/{userId}?page=0&size=20
Authorization: Bearer <token>
```

#### **7.3 Mark Notification as Read**
```http
PUT /api/v1/notifications/{notificationId}/read
Authorization: Bearer <token>
```

#### **7.4 Get Unread Notifications**
```http
GET /api/v1/notifications/user/{userId}/unread
Authorization: Bearer <token>
```

### **8. Monitoring Endpoints**

#### **8.1 Record Metric**
```http
POST /api/v1/monitoring/metrics
Authorization: Bearer <token>
Content-Type: application/json

{
  "serviceName": "smartshopai-user-service",
  "metricName": "user_registration_count",
  "metricType": "COUNTER",
  "value": 1.0,
  "labels": {
    "environment": "production",
    "region": "eu-west-1"
  }
}
```

#### **8.2 Get Service Metrics**
```http
GET /api/v1/monitoring/metrics/service/{serviceName}
Authorization: Bearer <token>
```

#### **8.3 Get System Health**
```http
GET /api/v1/monitoring/health
Authorization: Bearer <token>
```

---

## 📝 **Error Handling**

### **Standard Error Response Format**

```json
{
  "success": false,
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "Validation failed",
    "details": [
      "Email format is invalid",
      "Password must be at least 8 characters"
    ],
    "timestamp": "2024-01-15T10:30:00Z",
    "path": "/api/v1/auth/register"
  }
}
```

### **HTTP Status Codes**

| Code | Description |
|------|-------------|
| 200 | Success |
| 201 | Created |
| 400 | Bad Request |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |
| 409 | Conflict |
| 422 | Unprocessable Entity |
| 500 | Internal Server Error |

### **Error Codes**

| Code | Description |
|------|-------------|
| `VALIDATION_ERROR` | Input validation failed |
| `AUTHENTICATION_ERROR` | Authentication failed |
| `AUTHORIZATION_ERROR` | Insufficient permissions |
| `NOT_FOUND` | Resource not found |
| `BUSINESS_ERROR` | Business logic error |
| `INTERNAL_ERROR` | Internal server error |

---

## 🔧 **Rate Limiting**

API rate limiting is implemented to ensure fair usage:

- **Authentication endpoints**: 10 requests per minute
- **User endpoints**: 100 requests per minute
- **Product endpoints**: 200 requests per minute
- **AI endpoints**: 50 requests per minute
- **Monitoring endpoints**: 30 requests per minute

### **Rate Limit Headers**

```http
X-RateLimit-Limit: 100
X-RateLimit-Remaining: 95
X-RateLimit-Reset: 1642234567
```

---

## 📊 **Pagination**

All list endpoints support pagination:

### **Request Parameters**

- `page`: Page number (0-based, default: 0)
- `size`: Page size (default: 20, max: 100)
- `sort`: Sort field and direction (e.g., "price,asc", "name,desc")

### **Response Format**

```json
{
  "content": [...],
  "totalElements": 150,
  "totalPages": 8,
  "currentPage": 0,
  "size": 20,
  "first": true,
  "last": false
}
```

---

## 🔍 **Filtering & Sorting**

### **Product Filtering**

```http
GET /api/v1/products?category=electronics&brand=Apple&minPrice=500&maxPrice=1000&rating=4.0&sort=price,asc
```

### **Search Filtering**

```http
POST /api/v1/ai/search
{
  "query": "smartphone",
  "filters": {
    "priceRange": {"min": 300, "max": 1000},
    "brands": ["Apple", "Samsung"],
    "features": ["5G", "Wireless Charging"],
    "rating": {"min": 4.0}
  }
}
```

---

## 📈 **Monitoring & Health Checks**

### **Health Check Endpoint**

```http
GET /actuator/health
```

**Response:**
```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "MongoDB",
        "validationQuery": "isValid()"
      }
    },
    "redis": {
      "status": "UP"
    },
    "diskSpace": {
      "status": "UP",
      "details": {
        "total": 499963174912,
        "free": 419430400000,
        "threshold": 10485760
      }
    }
  }
}
```

### **Metrics Endpoint**

```http
GET /actuator/metrics
```

---

## 🚀 **SDK & Client Libraries**

### **JavaScript/TypeScript SDK**

```javascript
import { SmartShopAIClient } from '@smartshopai/sdk';

const client = new SmartShopAIClient({
  baseUrl: 'https://api.smartshopai.com',
  apiKey: 'your-api-key'
});

// Get products
const products = await client.products.getAll({
  page: 0,
  size: 20,
  category: 'electronics'
});

// AI analysis
const analysis = await client.ai.analyzeProduct({
  productId: 'product-123',
  analysisType: 'COMPREHENSIVE'
});
```

### **Python SDK**

```python
from smartshopai import SmartShopAIClient

client = SmartShopAIClient(
    base_url="https://api.smartshopai.com",
    api_key="your-api-key"
)

# Get recommendations
recommendations = client.ai.get_recommendations(
    user_id="user-123",
    request_type="PERSONALIZED"
)
```

---

## 📚 **Additional Resources**

- **Postman Collection**: [SmartShopAI API Collection](https://api.smartshopai.com/postman)
- **OpenAPI Spec**: [Swagger Documentation](https://api.smartshopai.com/swagger-ui.html)
- **SDK Documentation**: [Client Libraries](https://docs.smartshopai.com/sdk)
- **Support**: [API Support](https://support.smartshopai.com)

---

## 🔄 **API Versioning**

Current API version: **v1**

API versioning is handled through URL path:
- Current: `/api/v1/`
- Future: `/api/v2/`

Deprecation notices will be provided 6 months in advance.

---

## 📞 **Support & Contact**

- **API Support**: api-support@smartshopai.com
- **Technical Issues**: tech-support@smartshopai.com
- **Documentation**: docs@smartshopai.com
- **Status Page**: [status.smartshopai.com](https://status.smartshopai.com)
