FROM khipu/openjdk17-alpine
COPY /target/demo-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar", "/app/app.jar"]