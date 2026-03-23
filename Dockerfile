FROM openjdk:17-ea-alpine

LABEL authors="E-Commerce simple showcase application"

WORKDIR /app

ARG JAR_FILE=target/e-commerce-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8080
