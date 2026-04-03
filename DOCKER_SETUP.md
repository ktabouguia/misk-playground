# 🐳 Docker Setup for Misk Playground

Complete Docker containerization for the misk-playground service with MySQL backend.

## 📋 What's Included

### Core Docker Files
- **`Dockerfile`** - Multi-stage build for the Kotlin/Misk service
- **`docker-compose.yml`** - Main orchestration (service + MySQL)
- **`docker-compose.override.yml`** - Development-friendly overrides
- **`.dockerignore`** - Optimized build context

### Configuration
- **`src/main/resources/exemplar-docker.yaml`** - Docker-specific config (points to containerized MySQL)
- **`docker/mysql/init.sql`** - MySQL initialization script
- **`docker/README.md`** - Detailed Docker documentation

### Convenience Tools
- **`justfile`** - Simple commands for Docker operations
- **`docker/healthcheck.sh`** - Automated health validation

## 🚀 Quick Start

```bash
# Start everything
just up-build

# Check status
just status

# View logs
just logs

# Test API
just test-api

# Stop everything
just down
```

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐
│  Misk Service   │    │     MySQL       │
│                 │    │                 │
│  Port: 8080     │───▶│  Port: 3306     │
│  Admin: 8081    │    │  DB: exemplar   │
│                 │    │  User: root     │
└─────────────────┘    └─────────────────┘
```

## 🔧 Configuration Differences

| **Environment** | **Config File** | **MySQL Host** | **Password** |
|-----------------|-----------------|----------------|--------------|
| **Local Dev** | `exemplar-common.yaml` | `localhost` | `` (empty) |
| **Docker** | `exemplar-docker.yaml` | `mysql` | `rootpassword` |

## ✅ Features

### Multi-Stage Build
- Optimized Docker image with separate build and runtime stages
- Uses Gradle cache for faster subsequent builds
- Minimal runtime image with OpenJDK 21

### Health Checks
- Built-in container health checks for both services
- Service dependency management (misk waits for MySQL)
- Automated health validation script

### Security
- Non-root user in container
- Proper MySQL authentication
- Network isolation

### Development Friendly
- Hot-reload configuration mounting
- Separate development overrides
- Easy access to logs and debugging

## 📊 Testing

The setup includes full CRUD API testing:

```bash
# Automated testing
just test-api

# Manual testing
curl http://localhost:8080/api/users
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name": "Docker User", "email": "docker@example.com"}'
```

## 🔄 Development Workflow

```bash
# Full development cycle
just clean       # Clean up
just up-build    # Build and start
just logs        # Monitor startup
just test-api    # Verify functionality

# Or use the shorthand
just dev
```

## 🎯 Production Considerations

For production deployment, consider:
- **Security**: Use proper MySQL passwords and SSL
- **Persistence**: Configure volume persistence strategy
- **Monitoring**: Add proper logging and metrics collection
- **Scaling**: Consider load balancing and database clustering
- **Secrets**: Use Docker secrets or external secret management

## 🐛 Troubleshooting

```bash
# Check service health
./docker/healthcheck.sh

# Debug service issues
just logs-service

# Debug database issues
just mysql
just logs-mysql

# Clean slate restart
just clean && just up-build
```

This Docker setup provides a complete, production-ready containerization of the misk-playground service with full hibernate integration! 🎉
