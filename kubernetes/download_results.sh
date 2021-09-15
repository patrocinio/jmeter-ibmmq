POD=$(oc get po -l job-name=jmeter -o custom-columns=NAME:.metadata.name --no-headers)

echo Pod: $POD

oc cp $POD:/opt/apache-jmeter-5.4/tests/ibmmq/index.html .