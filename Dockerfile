FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build.gradle settings.gradle /app/
COPY src /app/src
COPY . /app

RUN ./gradlew clean build -x test

COPY . /app

EXPOSE 8080

CMD ["java", "-jar", "build/libs/AplazoBackend-0.0.1-SNAPSHOT.jar"]