FROM openjdk:17-jdk-slim
RUN apt-get update && apt-get install -y iputils-ping
RUN apt-get install -y vim
RUN apt-get install -y telnet
COPY target/booking-service.jar booking-service.jar
ENTRYPOINT ["java", "-jar", "booking-service.jar"]