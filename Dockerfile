FROM openjdk:17-alpine
MAINTAINER joaoeduardoam
COPY target/adopet-api-0.0.1-SNAPSHOT.jar  adopet-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/adopet-api-0.0.1-SNAPSHOT.jar"]
