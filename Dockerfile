#base image: linux alpine os with open jdk 8
FROM maven:3.9.8-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml ./
COPY src ./src
COPY lib ./lib
COPY conf ./conf

RUN mvn clean package

FROM eclipse-temurin:17-alpine

#copy jar from local into docker image/Users/thanhnt/Documents/project/springboot/demoproject/Dockerfile
COPY --from=build /app/target/com.demoproject.account-1.0.0.jar accountservice/com.demoproject.account-1.0.0.jar
COPY --from=build /app/conf accountservice/conf

# Expose port
EXPOSE 9001
#command line to run jar
ENTRYPOINT ["java","-Dapppath=/accountservice","-Dappenv=service","-jar","accountservice/com.demoproject.account-1.0.0.jar"]
