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
    "recommendations": [
      {
        "productId": "product-123",
        "name": "iPhone 15 Pro",
        "brand": "Apple",
        "price": 999.99,
        "score": 9.1,
        "reason": "Best camera and performance in budget"
      },
      {
        "productId": "product-456",
        "name": "Samsung Galaxy S24",
        "brand": "Samsung",
        "price": 899.99,
        "score": 8.8,
        "reason": "Great value and battery life"
      }
    ],
    "total": 2
  },
  "message": "Recommendations generated successfully"
}
```

### **6. Search Endpoints**

#### **6.1 Search Products**
```http
POST /api/v1/search/products
Authorization: Bearer <token>
Content-Type: application/json

{
  "query": "smartphone",
  "categories": ["electronics"],
  "brands": ["Apple", "Samsung"],
  "minPrice": 500.0,
  "maxPrice": 1500.0,
  "sortBy": "RELEVANCE",
  "filters": {
    "storage": "256GB"
  }
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "results": [
      {
        "productId": "product-123",
        "name": "iPhone 15 Pro",
        "brand": "Apple",
        "price": 999.99,
        "score": 9.1
      }
    ],
    "total": 1
  },
  "message": "Search completed successfully"
}
```

### **7. Notification Endpoints**

#### **7.1 Get Notifications**
```http
GET /api/v1/notifications
Authorization: Bearer <token>
```

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "id": "notif-001",
      "type": "PRICE_ALERT",
      "message": "Price dropped for iPhone 15 Pro!",
      "createdAt": "2024-01-15T10:30:00Z",
      "read": false
    }
  ],
  "message": "Notifications retrieved successfully"
}
```

### **8. Monitoring Endpoints**

#### **8.1 Get System Health**
```http
GET /api/v1/monitoring/health
Authorization: Bearer <token>
```

**Response:**
```json
{
  "success": true,
  "data": {
    "status": "HEALTHY",
    "services": [
      { "name": "User Service", "status": "UP" },
      { "name": "Product Service", "status": "UP" }
    ]
  },
  "message": "System health retrieved successfully"
}
```

---

## 📑 **Tablo Formatı Örneği**

| Endpoint                      | Method | Açıklama                       |
|-------------------------------|--------|--------------------------------|
| /api/v1/auth/login            | POST   | Kullanıcı girişi               |
| /api/v1/auth/register         | POST   | Kullanıcı kaydı                |
| /api/v1/users/profile         | GET    | Profil bilgisi görüntüleme      |
| /api/v1/products              | GET    | Tüm ürünleri listeleme         |
| /api/v1/products/search       | POST   | Ürün arama                     |
| /api/v1/ai/analysis           | POST   | Ürün AI analizi                |
| /api/v1/ai/recommendations    | POST   | AI öneri üretimi               |
| /api/v1/search/products       | POST   | Ürün arama (AI destekli)       |
| /api/v1/notifications         | GET    | Bildirimleri listeleme         |
| /api/v1/monitoring/health     | GET    | Sistem sağlık durumu           |
