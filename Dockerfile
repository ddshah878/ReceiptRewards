FROM openjdk:22-ea-1-oracle
MAINTAINER Dhrumil Shah
COPY build/libs/ReciptRewards-0.0.1-SNAPSHOT.jar ReciptRewards-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/ReciptRewards-0.0.1-SNAPSHOT.jar"]