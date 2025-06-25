# Step 1: Use an official Java runtime as the base image
FROM openjdk:17-jdk-slim

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy your built JAR file into the container
COPY target/weather-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose port 8080 (same as your Spring Boot app)
EXPOSE 8080

# Step 5: Command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
