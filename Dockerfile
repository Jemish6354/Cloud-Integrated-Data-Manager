FROM openjdk:21

WORKDIR /scm

COPY /target/scm2.jar /scm/scm2.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "scm2.jar"]