# Etapa 1: Build com Maven
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o pom.xml e baixa as dependências primeiro (boa prática de cache)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o restante do código e compila
COPY src ./src
RUN mvn package -DskipTests -B

# Etapa 2: Imagem final apenas com o runtime (mais leve)
FROM eclipse-temurin:21-jre
WORKDIR /app

# Cria usuário sem privilégios administrativos (exigido pelo challenge)
RUN adduser --disabled-password --gecos "" appuser
USER appuser

# Copia o jar gerado na etapa de build
COPY --from=build /app/target/challengeClyvo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
