FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/wallet-service.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]