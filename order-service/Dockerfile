FROM khipu/openjdk17-alpine
COPY /target/order-service-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 7976

ENTRYPOINT ["java","-jar", "/app/app.jar"]

