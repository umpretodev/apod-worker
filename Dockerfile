# Estágio 1: Build da aplicação
FROM eclipse-temurin:17-jdk-alpine as builder

WORKDIR /app
COPY . .
RUN ./gradlew build -x test

FROM eclipse-temurin:17-jre-alpine
WORKDIR /opt/app-root/src

COPY --from=builder /app/build/libs/worker-0.0.1-SNAPSHOT.jar /opt/app-root/src/app.jar
COPY --from=builder /app/src/main/resources/templates /opt/app-root/src/templates
CMD java $JAVA_OPTS -jar app.jar
