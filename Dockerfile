# --------- Build aşaması (Multi-stage) ----------
FROM maven:3.9.0-eclipse-temurin-17 AS build
WORKDIR /app

# Dependency cache
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Uygulama kaynaklarını kopyala ve paketle
COPY src src
RUN ./mvnw clean package -DskipTests -B

# --------- Runtime aşaması ----------
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Sadece jar dosyamızı alıyoruz
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]
