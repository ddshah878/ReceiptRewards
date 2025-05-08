FROM openjdk:22-ea-1-oracle
MAINTAINER Dhrumil Shah
COPY build/libs/ReceiptRewards-0.0.1-SNAPSHOT.jar ReceiptRewards-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/ReceiptRewards-0.0.1-SNAPSHOT.jar"]