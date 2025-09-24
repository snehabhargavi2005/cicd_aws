# ---------- Build stage ----------
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy backend source (instead of git clone)
COPY ./docker-backend/ ./

# Build the Spring Boot JAR (cache Maven dependencies for speed)
RUN --mount=type=cache,target=/root/.m2 mvn clean install package -DskipTests

# ---------- Run stage ----------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose backend application port (should match your app's inside port)
EXPOSE 8081

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
