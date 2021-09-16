./deploy-storage-pod.sh

mkdir -p report
oc cp storage-pod:/opt/apache-jmeter-5.4/tests/ibmmq/report/ report/