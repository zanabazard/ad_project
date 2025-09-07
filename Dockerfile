FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/delivery-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8989
ENTRYPOINT ["java","-jar","/app/app.jar"]
