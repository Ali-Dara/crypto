FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /app
COPY . .

RUN mvn -pl authentication-service -am clean package -DskipTests

FROM eclipse-temurin:25-jre

WORKDIR /app
COPY --from=build /app/authentication-service/target/authentication-service-1.0.0.jar app.jar

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "app.jar"]
