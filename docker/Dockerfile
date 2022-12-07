FROM openjdk:11
ADD MockMock.jar MockMock.jar
EXPOSE 8282
EXPOSE 25
ENTRYPOINT ["java", "-jar", "MockMock.jar", "-p", "25", "-h", "8282"]