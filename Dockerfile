## ---------- Build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /workspace

# Leverage Docker layer caching: first copy build descriptors
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw mvnw

# Pre-fetch dependencies (optional, safe to ignore failures offline)
RUN ./mvnw -q -B -DskipTests dependency:go-offline || true

# Copy sources and build
COPY src src
RUN ./mvnw -q -B -DskipTests clean package

## ---------- Runtime stage ----------
FROM eclipse-temurin:17-jre

ENV APP_HOME=/app \
    PORT=8080 \
    SPRING_PROFILES_ACTIVE=prod

WORKDIR ${APP_HOME}

# Non-root user for security
RUN useradd --system --create-home --home-dir ${APP_HOME} spring
USER spring

# Copy built jar with its original name
COPY --from=build /workspace/target/citas-0.0.1-SNAPSHOT.jar ${APP_HOME}/citas-0.0.1-SNAPSHOT.jar

EXPOSE 8080

# The app reads PORT from env via application.properties; profile defaults to prod
ENTRYPOINT ["java","-jar","/app/citas-0.0.1-SNAPSHOT.jar"]
