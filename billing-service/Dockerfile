FROM khipu/openjdk17-alpine
COPY /target/billing-service-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 7979

ENTRYPOINT ["java","-jar", "/app/app.jar"]

