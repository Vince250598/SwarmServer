FROM springci/spring-boot-jdk12-ci-image:master
VOLUME /tmp
COPY build/libs/SwarmServer.jar SwarmServer.jar
ENTRYPOINT ["java", "-jar", "SwarmServer.jar"]