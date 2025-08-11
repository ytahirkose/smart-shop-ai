// MongoDB initialization script for SmartShopAI
// Switch to admin database
var adminDb = db.getSiblingDB('admin');
adminDb.createUser({
  user: 'smartshopai_user',
  pwd: 'smartshopai_password',
  roles: [ { role: 'readWrite', db: 'smartshopai' } ]
});

// Switch to smartshopai database
var shopDb = db.getSiblingDB('smartshopai');
shopDb.createCollection('users');
shopDb.createCollection('user_profiles');
shopDb.createCollection('user_preferences');
shopDb.createCollection('user_behavior_metrics');
shopDb.createCollection('products');
shopDb.createCollection('product_categories');
shopDb.createCollection('product_brands');
shopDb.createCollection('product_analyses');
shopDb.createCollection('product_comparisons');
shopDb.createCollection('reviews');
shopDb.createCollection('analysis_requests');
shopDb.createCollection('recommendations');
shopDb.createCollection('recommendation_requests');
shopDb.createCollection('recommendation_results');
shopDb.createCollection('search_requests');
shopDb.createCollection('search_results');
shopDb.createCollection('notifications');
shopDb.createCollection('notification_templates');
shopDb.createCollection('metrics');
shopDb.createCollection('system_health');
shopDb.createCollection('analytics_events');
shopDb.createCollection('business_metrics');

// Create indexes for better performance
shopDb.users.createIndex({ "email": 1 }, { unique: true });
shopDb.users.createIndex({ "username": 1 }, { unique: true });
shopDb.products.createIndex({ "productId": 1 }, { unique: true });
shopDb.products.createIndex({ "name": "text", "description": "text" });
shopDb.products.createIndex({ "category": 1 });
shopDb.products.createIndex({ "brand": 1 });
shopDb.product_analyses.createIndex({ "productId": 1 });
shopDb.product_analyses.createIndex({ "userId": 1 });
shopDb.product_analyses.createIndex({ "analysisType": 1 });
shopDb.recommendations.createIndex({ "userId": 1 });
shopDb.recommendations.createIndex({ "requestType": 1 });
shopDb.search_requests.createIndex({ "userId": 1 });
shopDb.search_requests.createIndex({ "query": "text" });
shopDb.notifications.createIndex({ "userId": 1 });
shopDb.notifications.createIndex({ "type": 1 });
shopDb.notifications.createIndex({ "status": 1 });
shopDb.analytics_events.createIndex({ "userId": 1 });
shopDb.analytics_events.createIndex({ "eventType": 1 });
shopDb.analytics_events.createIndex({ "timestamp": 1 });
shopDb.business_metrics.createIndex({ "metricName": 1 });
shopDb.business_metrics.createIndex({ "category": 1 });
shopDb.business_metrics.createIndex({ "timestamp": 1 });

// Insert sample data for testing
shopDb.users.insertOne({
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

shopDb.products.insertOne({
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
