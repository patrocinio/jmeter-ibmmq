. ./.envrc
sed s/PVC_NAME/$PVC_NAME/g < jmeter-job.yaml.template > jmeter-job.yaml

oc delete -f jmeter-job.yaml
oc apply -f jmeter-job.yaml