FROM openjdk:17

ARG JAR_FILE=target/*.jar

ADD ${JAR_FILE} backend-services.jar

ENTRYPOINT ["java", "-jar", "backend-services.jar"]

EXPOSE 80