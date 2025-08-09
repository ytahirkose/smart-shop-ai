// Initialize MongoDB databases for SmartShopAI
db = db.getSiblingDB('smartshopai');
db.createUser({ user: 'admin', pwd: 'password', roles: [ { role: 'readWrite', db: 'smartshopai' }, { role: 'readWriteAnyDatabase', db: 'admin' } ] });

db = db.getSiblingDB('smartshopai_users');
db.createCollection('init');

db = db.getSiblingDB('smartshopai_products');
db.createCollection('init');

db = db.getSiblingDB('smartshopai_ai_analysis');
db.createCollection('init');

db = db.getSiblingDB('smartshopai_ai_recommendation');
db.createCollection('init');

db = db.getSiblingDB('smartshopai_ai_search');
db.createCollection('init');

db = db.getSiblingDB('smartshopai_search');
db.createCollection('init');

db = db.getSiblingDB('smartshopai_notification');
db.createCollection('init');

db = db.getSiblingDB('smartshopai_monitoring');
db.createCollection('init');

db = db.getSiblingDB('smartshopai_business_intelligence');
db.createCollection('init');

db = db.getSiblingDB('smartshopai_session_cache');
db.createCollection('init');
// MongoDB initialization script for SmartShopAI
// This script creates the necessary databases and collections

// Switch to admin database
db = db.getSiblingDB('admin');

// Create user for smartshopai database
db.createUser({
  user: 'smartshopai_user',
  pwd: 'smartshopai_password',
  roles: [
    {
      role: 'readWrite',
      db: 'smartshopai'
    }
  ]
});

// Switch to smartshopai database
db = db.getSiblingDB('smartshopai');

// Create collections for each service
db.createCollection('users');
db.createCollection('user_profiles');
db.createCollection('user_preferences');
db.createCollection('user_behavior_metrics');

db.createCollection('products');
db.createCollection('product_categories');
db.createCollection('product_brands');
db.createCollection('product_analyses');
db.createCollection('product_comparisons');
db.createCollection('reviews');

db.createCollection('product_analyses');
db.createCollection('analysis_requests');

db.createCollection('recommendations');
db.createCollection('recommendation_requests');
db.createCollection('recommendation_results');

db.createCollection('search_requests');
db.createCollection('search_results');

db.createCollection('notifications');
db.createCollection('notification_templates');

db.createCollection('metrics');
db.createCollection('system_health');

db.createCollection('analytics_events');
db.createCollection('business_metrics');

// Create indexes for better performance
db.users.createIndex({ "email": 1 }, { unique: true });
db.users.createIndex({ "username": 1 }, { unique: true });

db.products.createIndex({ "productId": 1 }, { unique: true });
db.products.createIndex({ "name": "text", "description": "text" });
db.products.createIndex({ "category": 1 });
db.products.createIndex({ "brand": 1 });

db.product_analyses.createIndex({ "productId": 1 });
db.product_analyses.createIndex({ "userId": 1 });
db.product_analyses.createIndex({ "analysisType": 1 });

db.recommendations.createIndex({ "userId": 1 });
db.recommendations.createIndex({ "requestType": 1 });

db.search_requests.createIndex({ "userId": 1 });
db.search_requests.createIndex({ "query": "text" });

db.notifications.createIndex({ "userId": 1 });
db.notifications.createIndex({ "type": 1 });
db.notifications.createIndex({ "status": 1 });

db.analytics_events.createIndex({ "userId": 1 });
db.analytics_events.createIndex({ "eventType": 1 });
db.analytics_events.createIndex({ "timestamp": 1 });

db.business_metrics.createIndex({ "metricName": 1 });
db.business_metrics.createIndex({ "category": 1 });
db.business_metrics.createIndex({ "timestamp": 1 });

// Insert sample data for testing
db.users.insertOne({
  _id: "user-001",
  username: "testuser",
  email: "test@smartshopai.com",
  password: "$2a$10$dummy.hash.for.testing",
  firstName: "Test",
  lastName: "User",
  enabled: true,
  roles: ["USER"],
  createdAt: new Date(),
  updatedAt: new Date()
});

db.products.insertOne({
  _id: "product-001",
  productId: "PROD-001",
  name: "Sample Product",
  description: "This is a sample product for testing",
  brand: "Sample Brand",
  category: "Electronics",
  price: 99.99,
  currency: "USD",
  inStock: true,
  stockQuantity: 100,
  createdAt: new Date(),
  updatedAt: new Date()
});

print("MongoDB initialization completed successfully!");
