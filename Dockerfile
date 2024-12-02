FROM ubuntu:latest AS build
RUN apt-get update
RUN apt-get install openjdk-23-jdk -y

COPY . .

RUN apt-get install maven -y
RUN mvn clean install

FROM opejdk:23-jdk-slim
EXPOSE 8080

COPY --from=build /target/todolist-1.0.0.jar app.jar

LABEL authors="leonardo.souza"

ENTRYPOINT ["java", "-jar", "app.jar"]