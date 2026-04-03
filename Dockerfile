# Multi-stage build for misk-playground service
FROM gradle:8.5-jdk21 AS build

WORKDIR /app
COPY . .

# Build the application distribution
RUN gradle installDist --no-daemon

FROM eclipse-temurin:21-jre

# Install netcat for health checks
RUN apt-get update && apt-get install -y netcat-openbsd && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy the built distribution (the directory name will be the project name)
COPY --from=build /app/build/install/*/ /app/

# Copy docker config as the common config and patch the start script to
# prepend /app/resources/ on the classpath so it overrides JAR defaults.
COPY --from=build /app/src/main/resources/exemplar-docker.yaml /app/resources/exemplar-common.yaml
RUN sed -i 's|^CLASSPATH=|CLASSPATH=/app/resources/:|' /app/bin/app

# Create non-root user for security
RUN groupadd -r misk && useradd -r -g misk misk
RUN chown -R misk:misk /app
USER misk

# Expose ports
EXPOSE 8080 8081

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD nc -z localhost 8080 || exit 1

# Run the application (the script is called "app")
ENTRYPOINT ["/app/bin/app"]