./deploy-storage-pod.sh

echo Removing old files
oc exec storage-pod -- rm -rf /opt/apache-jmeter-5.4/tests/ibmmq/*

echo Copying files
oc cp ../tests/ibmmq/MQ.jmx storage-pod:/opt/apache-jmeter-5.4/tests/ibmmq
oc cp ../tests/ibmmq/clientkey.jks storage-pod:/opt/apache-jmeter-5.4/tests/ibmmq

echo Deleting Pod
oc delete po storage-pod