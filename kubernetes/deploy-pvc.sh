. ./.envrc
sed s/PVC_NAME/$PVC_NAME/g < jmeter-pvc.yaml.template > jmeter-pvc.yaml
oc delete -f jmeter-pvc.yaml --force
oc apply -f jmeter-pvc.yaml