FROM openjdk:11

EXPOSE 8080

ADD ./target/*.jar mk.jar

ENTRYPOINT ["java", "-jar", "/mk.jar"]