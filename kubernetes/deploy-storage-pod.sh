oc delete -f storage-pod.yaml
oc apply -f storage-pod.yaml

echo Waiting for Pod to be ready
oc wait --for=condition=ready --timeout=300s pod storage-pod

