FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /app
COPY . .
RUN mvn -pl api-gateway -am clean package -DskipTests

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/api-gateway/target/api-gateway-1.0.0.jar app.jar

EXPOSE 8000
ENTRYPOINT ["java", "-jar", "app.jar"]
