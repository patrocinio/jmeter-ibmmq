FROM openjdk:14

WORKDIR /usr/src/myapp

RUN mkdir lib output src

COPY src/Producer.java src
COPY clientkey.jks /usr/src/myapp/

COPY lib lib

RUN javac src/Producer.java -d output -cp lib/javax.jms-api-2.0.1.jar:lib/com.ibm.mq.allclient-9.2.3.0.jar

ENTRYPOINT java  -cp output:lib/javax.jms-api-2.0.1.jar:lib/com.ibm.mq.allclient-9.2.3.0.jar Producer
