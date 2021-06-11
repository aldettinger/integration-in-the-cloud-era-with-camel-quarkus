# Finalize the configuration
oc apply -f openshift-configs/limit-range.yaml
kn ksvc update hello-cq-knative-jvm-mode --request memory=250Mi
kn ksvc update bye-cq-knative-jvm-mode --request memory=250Mi
kn ksvc update hello-cq-knative-native-mode --request memory=50Mi
kn ksvc update bye-cq-knative-native-mode --request memory=50Mi
oc apply -f openshift-configs/mem-quota.yaml

# Check that mem requests were applied
echo 'Checking that memory requests were applied'
oc get ksvc hello-cq-knative-jvm-mode -o json | grep request -A 1
oc get ksvc hello-cq-knative-native-mode -o json | grep request -A 1
oc get ksvc bye-cq-knative-jvm-mode -o json | grep request -A 1
oc get ksvc bye-cq-knative-native-mode -o json | grep request -A 1
