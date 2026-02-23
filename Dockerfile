# Build stage
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copy Maven wrapper and pom
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source and build
COPY src src
RUN ./mvnw package -DskipTests -B

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/target/jobportal-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENV PORT=8080

# Render sets PORT; Spring Boot reads server.port from env
ENTRYPOINT ["sh", "-c", "java -jar -Dserver.port=${PORT} app.jar"]
