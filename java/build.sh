MQ_JARS=/Users/edu/github/jmeter-ibmmq/lib
CLASSPATH=$MQ_JARS/ibmmq/javax.jms-api-2.0.1.jar:$MQ_JARS/ibmmq/com.ibm.mq.allclient-9.2.3.0.jar

function compile {
javac $1 -d output -cp $2
}

#compile src/org/patrocinio/ibmmq/MQProducer.java $CLASSPATH
compile src/Producer.java $CLASSPATH

