# Misk Playground

A playground service built with [Misk](https://github.com/cashapp/misk) showcasing features and best practices, including Hibernate persistence, web actions, admin dashboards, and frontend UI.

## Prerequisites

- [Docker](https://docs.docker.com/get-started/) and Docker Compose
- [just](https://github.com/casey/just) command runner (`brew install just`)

## Getting Started

### Start the service

Build and start the Misk service along with MySQL:

```bash
just up-build
```

This will:
1. Build the application using Gradle (inside Docker)
2. Start a MySQL 8.0 container
3. Run database migrations automatically
4. Start the Misk service on port **8080** (with Prometheus metrics on **8081**)

### Verify it's running

```bash
just status
```

### Test the API

```bash
just test-api
```

Or manually:

```bash
# List users
curl http://localhost:8080/api/users

# Create a user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name": "Jane Doe", "email": "jane@example.com"}'

# Say hello
curl http://localhost:8080/hello/world
```

## API Endpoints

| Method   | Path                      | Description              |
|----------|---------------------------|--------------------------|
| `GET`    | `/api/users`              | List all users           |
| `GET`    | `/api/users/{id}`         | Get a user by ID         |
| `POST`   | `/api/users`              | Create a new user        |
| `PUT`    | `/api/users/{id}`         | Update a user            |
| `DELETE` | `/api/users/{id}`         | Delete a user            |
| `GET`    | `/hello/{name}`           | Hello world (JSON)       |
| `POST`   | `/hello/{name}`           | Hello world (POST)       |
| `GET`    | `/download/{name}`        | File download example    |
| `GET`    | `/lease-acquire/{name}`   | Lease acquisition demo   |
| `GET`    | `/lease-check/{name}`     | Lease check demo         |
| `GET`    | `/`                       | Frontend index page      |
| `GET`    | `/_admin/`                | Admin dashboard          |

## Available Commands

Run `just` to see all available commands:

| Command            | Description                          |
|--------------------|--------------------------------------|
| `just build`       | Build the Docker images              |
| `just up`          | Start the services                   |
| `just up-build`    | Start services with a fresh build    |
| `just down`        | Stop the services                    |
| `just logs`        | Tail all service logs                |
| `just logs-service`| Tail Misk service logs only          |
| `just logs-mysql`  | Tail MySQL logs only                 |
| `just status`      | Show container status                |
| `just test-api`    | Test the API endpoints               |
| `just shell`       | Open a shell in the Misk container   |
| `just mysql`       | Connect to the MySQL CLI             |
| `just clean`       | Remove containers, volumes, and cache|
| `just dev`         | Full dev cycle: clean → build → logs |

## Local Development (without Docker)

To run the service directly on your machine:

```bash
./bin/activate-hermit
gradle :run
```

> **Note:** This requires a local MySQL instance running on `127.0.0.1:3306` with a `exemplar_testing` database. See `src/main/resources/exemplar-common.yaml` for connection details.

## Project Structure

```
src/main/kotlin/com/squareup/exemplar/
├── ExemplarService.kt          # Application entry point
├── ExemplarConfig.kt           # Configuration data class
├── ExemplarPersistenceModule.kt# Hibernate/MySQL wiring
├── actions/                    # Web action endpoints
│   ├── UserWebActions.kt       # User CRUD (Hibernate)
│   ├── HelloWebAction.kt       # Hello world examples
│   └── ...
├── persistence/                # Database entities and services
│   ├── DbUser.kt               # Hibernate entity
│   └── UserService.kt          # Business logic
├── dashboard/                  # Admin and frontend UI
│   ├── frontend/               # Hotwire/Tailwind pages
│   └── admin/                  # Admin dashboard tabs
└── audit/                      # Audit client example

src/main/resources/
├── exemplar-common.yaml        # Config for local development
└── exemplar-docker.yaml        # Config for Docker (MySQL host: mysql)
```
