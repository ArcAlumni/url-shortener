#
# Build Docker
#
#FROM maven:3.6.0-jdk-11-slim AS maven_build
#WORKDIR /app
#COPY ./pom.xml ./pom.xml
#RUN mvn -B dependency:go-offline
#COPY ./src ./src
#RUN mvn package

#
# Run
#
FROM openjdk:11-jre-slim
ADD /app
COPY target/*.jar /app
ENTRYPOINT ["java","-jar","/app/app.jar"]
