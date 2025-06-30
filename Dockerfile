FROM eclipse-temurin:17-jdk-alpine
ARG SERVER_PORT
EXPOSE ${SERVER_PORT}

ARG JAR_NAME
ADD ${JAR_NAME} myapp.jar

ENTRYPOINT ["java", "-jar", "/myapp.jar"]