FROM maven:3.9.9-eclipse-temurin-25 AS build
WORKDIR /app

COPY . .
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:25-jre
WORKDIR /app

COPY --from=build /app/target/playstate-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
CMD ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]
