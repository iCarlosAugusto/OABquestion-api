FROM openjdk:21-jdk
ARG JAR_FILE=target/*.jar
COPY ./target/questionApiOAB-1.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]