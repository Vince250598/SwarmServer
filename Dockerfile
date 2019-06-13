FROM springci/spring-boot-jdk12-ci-image:master
VOLUME /tmp
EXPOSE 8080
COPY build/libs/SwarmServer.jar SwarmServer.jar
ENTRYPOINT ["java", "-jar", "SwarmServer.jar"]