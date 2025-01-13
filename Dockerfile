# Этап 1: Сборка
# Используем JDK 21 для компиляции кода, предназначенного для Java 21
FROM --platform=linux/amd64 gradle:8-jdk21-alpine AS build
WORKDIR /app

# Копируем файлы настроек (используем маску для гибкости)
COPY build.gradle* settings.gradle* /app/

# Копируем исходный код
COPY src /app/src

# Собираем JAR
RUN gradle build -x test --no-daemon

# Этап 2: Финальный образ
# Также используем JRE 21 для запуска
FROM --platform=linux/amd64 eclipse-temurin:21-jre-alpine
WORKDIR /app

# Копируем собранный JAR из предыдущего этапа
COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]