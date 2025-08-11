#!/bin/bash

# Health check script for SmartShopAI services

echo "Starting health checks for SmartShopAI services..."

# Check Discovery Service
echo "Checking Discovery Service..."
if curl -f http://localhost:8761/actuator/health > /dev/null 2>&1; then
    echo "✅ Discovery Service is healthy"
else
    echo "❌ Discovery Service is not responding"
    exit 1
fi

# Check Gateway Service
echo "Checking Gateway Service..."
if curl -f http://localhost:8080/actuator/health > /dev/null 2>&1; then
    echo "✅ Gateway Service is healthy"
else
    echo "❌ Gateway Service is not responding"
    exit 1
fi

# Check User Service
echo "Checking User Service..."
if curl -f http://localhost:8081/actuator/health > /dev/null 2>&1; then
    echo "✅ User Service is healthy"
else
    echo "❌ User Service is not responding"
    exit 1
fi

# Check Product Service
echo "Checking Product Service..."
if curl -f http://localhost:8082/actuator/health > /dev/null 2>&1; then
    echo "✅ Product Service is healthy"
else
    echo "❌ Product Service is not responding"
    exit 1
fi

# Check AI Analysis Service
echo "Checking AI Analysis Service..."
if curl -f http://localhost:8083/actuator/health > /dev/null 2>&1; then
    echo "✅ AI Analysis Service is healthy"
else
    echo "❌ AI Analysis Service is not responding"
    exit 1
fi

# Check MongoDB
echo "Checking MongoDB..."
if curl -f http://localhost:27017 > /dev/null 2>&1; then
    echo "✅ MongoDB is running"
else
    echo "❌ MongoDB is not responding"
    exit 1
fi

# Check Redis
echo "Checking Redis..."
if redis-cli -h localhost -p 6379 ping > /dev/null 2>&1; then
    echo "✅ Redis is running"
else
    echo "❌ Redis is not responding"
    exit 1
fi

echo "🎉 All services are healthy!"
exit 0
