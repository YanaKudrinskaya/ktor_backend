FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Копируем gradle wrapper и настройки
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradle/libs.versions.toml gradle/libs.versions.toml

# Даем права на выполнение gradlew
RUN chmod +x gradlew

# Копируем исходники
COPY src src

# Собираем проект
RUN ./gradlew clean build -x test

# Финальный образ
FROM eclipse-temurin:21-jdk-alpine

EXPOSE 8080

WORKDIR /app

# Копируем собранный jar из предыдущего этапа
COPY --from=builder /app/build/libs/playzone_backend-all.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]