FROM openjdk:11-jdk-slim

COPY target/sprint-service-1.0.jar /opt/sprint-service/

# some tools
RUN apt-get update && apt-get install -y vim tree mc lnav

# setting proper TZ
ENV TZ=Europe/Moscow
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

EXPOSE 8080
VOLUME /opt/sprint-service/logs

WORKDIR /opt/sprint-service/

CMD ["java","-jar","sprint-service-1.0.jar"]