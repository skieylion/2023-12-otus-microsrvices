FROM khipu/openjdk17-alpine
COPY /target/notification-service-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 7978

ENTRYPOINT ["java","-jar", "/app/app.jar"]

