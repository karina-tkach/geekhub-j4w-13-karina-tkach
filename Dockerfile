FROM gradle:jdk17 AS build
WORKDIR /app-source
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle
COPY Coursework ./Coursework
RUN gradle clean Coursework:rest-api:bootJar

FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /app-source/Coursework/rest-api/build/libs/*.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
