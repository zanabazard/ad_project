cat > ~/ad_project/Dockerfile <<'DOCKER'
# ===== Build stage =====
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /workspace
COPY . .

# Ensure wrapper is executable
RUN chmod +x mvnw || true

# Install the two private jars into local Maven repo
RUN ./mvnw -q install:install-file \
      -Dfile=third_party/common-utils-0.1.jar \
      -DgroupId=nomin -DartifactId=common-utils -Dversion=0.1 -Dpackaging=jar && \
    ./mvnw -q install:install-file \
      -Dfile=third_party/common-service-1.1.jar \
      -DgroupId=nomin -DartifactId=common-service -Dversion=1.1 -Dpackaging=jar

# Build the application
RUN ./mvnw -DskipTests package

# ===== Run stage =====
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /workspace/target/*.jar /app/app.jar
EXPOSE 8989
ENTRYPOINT ["java","-jar","/app/app.jar"]
DOCKER
