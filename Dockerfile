# Estágio 1: Build da aplicação
FROM eclipse-temurin:17-jdk-alpine as builder

WORKDIR /app
COPY . .

# Gradle faz o build do projeto
RUN ./gradlew build -x test

# Estágio 2: Configuração para execução
FROM eclipse-temurin:17-jre-alpine

WORKDIR /opt/app-root/src

# Copiar o JAR gerado no build para o container
COPY --from=builder /app/build/libs/worker-0.0.1-SNAPSHOT.jar /opt/app-root/src/app.jar

# Copiar os templates para o mesmo caminho no container
COPY --from=builder /app/src/main/resources/templates /opt/app-root/src/templates

# Rodar a aplicação
CMD java $JAVA_OPTS -jar app.jar
