oc delete -f storage-pod.yaml
oc apply -f storage-pod.yaml

echo Waiting for Pod to be ready
oc wait --for=condition=ready --timeout=300s pod storage-pod

echo Copying files
oc cp ../tests/ibmmq/MQ.jmx storage-pod:/opt/apache-jmeter-5.4/tests/ibmmq
oc cp ../tests/ibmmq/clientkey.jks storage-pod:/opt/apache-jmeter-5.4/tests/ibmmq

echo Deleting Pod
oc delete po storage-pod