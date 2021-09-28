./deploy-storage-pod.sh

mkdir -p report

echo Downloading report
oc cp storage-pod:/opt/apache-jmeter-5.4/tests/ibmmq/report/ report/

