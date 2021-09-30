FROM openjdk:latest

WORKDIR /usr/src/myapp

RUN mkdir lib output src

COPY src/Producer.java src

COPY lib lib

RUN javac src/Producer.java -d output -cp lib/javax.jms-api-2.0.1.jar:lib/com.ibm.mq.allclient-9.2.3.0.jar
