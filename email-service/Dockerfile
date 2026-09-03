FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /app
COPY . .
RUN mvn -pl email-service -am clean package -DskipTests

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/email-service/target/email-service-1.0.0.jar app.jar

EXPOSE 8004
ENTRYPOINT ["java", "-jar", "app.jar"]
