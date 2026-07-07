FROM --platform=linux/amd64 eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

COPY gradlew /app/
COPY gradle /app/gradle

COPY build.gradle* settings.gradle* /app/

RUN apk add --no-cache dos2unix && \
    dos2unix gradlew && \
    chmod +x gradlew

COPY src /app/src

RUN ./gradlew build -x test --no-daemon

FROM --platform=linux/amd64 eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]