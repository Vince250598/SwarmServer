FROM springci/spring-boot-jdk12-ci-image:master
EXPOSE 8080
ARG JAR_FILE=build/libs/SwarmServer.jar
COPY ${JAR_FILE} SwarmServer.jar
ENTRYPOINT ["java", "-jar", "SwarmServer.jar"]