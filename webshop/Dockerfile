# Use official OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# Set work directory
WORKDIR /app

# Copy the entire project (including Gradle files)
COPY . .

# Grant execution permission to Gradle wrapper
RUN chmod +x gradlew

# Build the JAR inside Docker
RUN ./gradlew clean bootJar -x test

# Ensure the built JAR is accessible
RUN cp build/libs/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar", "--spring.config.location=classpath:/application-docker.properties"]
