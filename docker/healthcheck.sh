#!/bin/bash

# Health check script for misk-playground Docker setup
# This script validates that both the service and database are working correctly

set -e

echo "🐳 Misk Playground Docker Health Check"
echo "======================================"

# Check if services are running
echo "📋 Checking service status..."
docker compose ps

# Wait for services to be ready
echo "⏳ Waiting for services to be ready..."
sleep 10

# Test MySQL connection
echo "🗄️  Testing MySQL connection..."
docker compose exec -T mysql mysqladmin ping -u root -prootpassword
echo "✅ MySQL is responsive"

# Test misk service health
echo "🚀 Testing misk service health..."
if curl -f -s http://localhost:8080/api/users > /dev/null; then
    echo "✅ Misk service is responsive"
else
    echo "❌ Misk service is not responding"
    echo "📋 Service logs:"
    docker compose logs --tail=20 misk-service
    exit 1
fi

# Test database connectivity through the service
echo "🔗 Testing database connectivity through service..."
response=$(curl -s http://localhost:8080/api/users)
if echo "$response" | grep -q "users"; then
    echo "✅ Database connectivity through service is working"
    echo "📊 Current users: $response"
else
    echo "❌ Database connectivity issue"
    echo "📋 Response: $response"
    exit 1
fi

echo ""
echo "🎉 All health checks passed!"
echo "🌐 Service available at: http://localhost:8080"
echo "📊 Admin interface at: http://localhost:8081"