# justfile for misk-playground Docker operations

# List available recipes
default:
    @just --list

# Build the Docker images
build:
    docker compose build

# Start the services
up:
    docker compose up -d

# Start services with a fresh build
up-build:
    docker compose up -d --build

# Stop the services
down:
    docker compose down

# Tail all service logs
logs:
    docker compose logs -f

# Tail Misk service logs only
logs-service:
    docker compose logs -f misk-service

# Tail MySQL logs only
logs-mysql:
    docker compose logs -f mysql

# Remove containers, volumes, and cache
clean:
    docker compose down -v --remove-orphans
    docker system prune -f

# Test the API endpoints
test-api:
    @echo "Testing API endpoints..."
    @sleep 5
    @echo "\n=== GET /api/users ==="
    curl -s http://localhost:8080/api/users | jq . || curl -s http://localhost:8080/api/users
    @echo "\n\n=== POST /api/users ==="
    curl -s -X POST http://localhost:8080/api/users \
        -H "Content-Type: application/json" \
        -d '{"name": "Docker Test User", "email": "docker-test@example.com"}' | jq . || \
    curl -s -X POST http://localhost:8080/api/users \
        -H "Content-Type: application/json" \
        -d '{"name": "Docker Test User", "email": "docker-test@example.com"}'

# Open a shell in the Misk container
shell:
    docker compose exec misk-service /bin/bash

# Connect to the MySQL CLI
mysql:
    docker compose exec mysql mysql -u root -prootpassword exemplar_testing

# Show container status
status:
    docker compose ps

# Full dev cycle: clean → build → logs
dev: clean up-build logs
