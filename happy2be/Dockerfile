FROM maven:3.8-openjdk-17 AS build
WORKDIR /app

# Copy everything
COPY . .

# Build the app (skip tests, CI will run them separately)
RUN mvn clean package -DskipTests

# Stage 2: Run the JAR
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar happy2be.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "happy2be.jar"]


