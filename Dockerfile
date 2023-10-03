# Use an official OpenJDK runtime as a parent image
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the project files into the container
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Use a lightweight Alpine-based JRE image for the final image
FROM openjdk:17-jre-slim

# Set the working directory in the final image
WORKDIR /app

# Copy the JAR file from the build stage to the final image
COPY --from=build /app/target/MobileSubscribers-0.0.1-SNAPSHOT.jar ./MobileSubscribers-0.0.1-SNAPSHOT.jar

# Expose the port that your application listens on (change as needed)
EXPOSE 8080

# Define environment variables for database connection
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/mobilesubscribers
ENV SPRING_DATASOURCE_USERNAME=SoftIntern
ENV SPRING_DATASOURCE_PASSWORD=SoftIntern123
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update
ENV SPRING_JPA_SHOW_SQL=true
ENV SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
ENV SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL=true
ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver

# Define Thymeleaf configuration
ENV SPRING_THYMELEAF_CACHE=false
ENV SPRING_THYMELEAF_ENABLED=true
ENV SPRING_THYMELEAF_ENCODING=UTF-8
ENV SPRING_THYMELEAF_SERVLET_CONTENT_TYPE=text/html
ENV SPRING_THYMELEAF_MODE=HTML
ENV SPRING_THYMELEAF_PREFIX=classpath:/templates/
ENV SPRING_THYMELEAF_SUFFIX=.html

# Define server error message configuration
ENV SERVER_ERROR_INCLUDE_MESSAGE=always

# Define the entry point to run the application
ENTRYPOINT ["java", "-jar", "MobileSubscribers-0.0.1-SNAPSHOT.jar"]

