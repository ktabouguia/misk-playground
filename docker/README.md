# Docker Setup for Misk Playground

This directory contains Docker configuration to run the misk-playground service in containers with MySQL.

## Quick Start

```bash
# Start the service and database
docker compose up --build

# Or run in background
docker compose up -d --build

# View logs
docker compose logs -f misk-service

# Stop everything
docker compose down

# Stop and remove volumes (removes database data)
docker compose down -v
```

## Service Endpoints

Once running, the service will be available at:
- **Main API**: http://localhost:8080
- **Admin/Prometheus**: http://localhost:8081

## Database Access

The MySQL database is accessible at:
- **Host**: localhost:3306
- **Database**: exemplar_testing
- **Username**: root
- **Password**: rootpassword

## API Testing

Test the CRUD API endpoints:

```bash
# List users
curl http://localhost:8080/api/users

# Create a user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name": "Docker User", "email": "docker@example.com"}'

# Get specific user (replace {id} with actual ID)
curl http://localhost:8080/api/users/{id}
```

## Configuration

- **Local development**: Uses `exemplar-common.yaml` (points to localhost MySQL)
- **Docker setup**: Uses `exemplar-docker.yaml` (points to containerized MySQL)

## Architecture

```
┌─────────────────┐    ┌─────────────────┐
│  Misk Service   │    │     MySQL       │
│   Port 8080     │───▶│   Port 3306     │
│   Port 8081     │    │                 │
└─────────────────┘    └─────────────────┘
```

## Troubleshooting

### Service won't start
- Check logs: `docker compose logs misk-service`
- Ensure MySQL is healthy: `docker compose ps`

### Database connection issues
- Verify MySQL container: `docker compose exec mysql mysql -u root -p`
- Check network connectivity: `docker compose exec misk-service nc -z mysql 3306`

### Port conflicts
- If ports 8080/8081/3306 are in use, modify `docker compose.yml` port mappings