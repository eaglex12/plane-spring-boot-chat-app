# Use Alpine OpenJDK 17 as the base image for the second stage
FROM openjdk:21

# Set the working directory to /app
WORKDIR /app

# Copy the JAR file from the build stage
COPY target/chat-1.jar /app/demo.jar

# Expose port 8080
EXPOSE 8080

# Command to run the application
CMD ["java","-jar","demo.jar"]
