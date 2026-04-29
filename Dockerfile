FROM openjdk:21-jdk-slim

EXPOSE 8080

WORKDIR /app

COPY build/libs/playzone_backend-all.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]