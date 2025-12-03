# Etapa de build: compilar el JAR con Maven y Java 17
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app

# Copiamos pom y código
COPY pom.xml .
COPY src ./src

# Compilamos sin tests
RUN mvn -q clean package -DskipTests

# Etapa de runtime: solo el JRE y el jar compilado
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copiamos el jar desde la etapa de build (cualquiera que termine en .jar)
COPY --from=builder /app/target/*.jar app.jar

# Puerto interno (Spring por defecto 8080)
EXPOSE 8080

# Opcional: permitir pasar parámetros extra a la JVM
ENV JAVA_OPTS=""

# Arrancar la app
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
