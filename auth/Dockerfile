FROM khipu/openjdk17-alpine
COPY /target/auth-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 7999

ENTRYPOINT ["java","-jar", "/app/app.jar"]

