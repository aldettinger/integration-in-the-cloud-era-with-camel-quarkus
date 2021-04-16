# Finalize the configuration
oc apply -f openshift-configs/limit-range.yaml
kn ksvc update hello-cq-knative-jvm-mode --request memory=600Mi
kn ksvc update bye-cq-knative-jvm-mode --request memory=600Mi
kn ksvc update hello-cq-knative-native-mode --request memory=50Mi
kn ksvc update bye-cq-knative-native-mode --request memory=50Mi
oc apply -f openshift-configs/mem-quota.yaml

# Check that mem requests were applied
echo 'Checking that memory requests were applied'
oc get ksvc hello-cq-knative-jvm-mode -o json | grep request -A 1
oc get ksvc hello-cq-knative-native-mode -o json | grep request -A 1
oc get ksvc bye-cq-knative-jvm-mode -o json | grep request -A 1
oc get ksvc bye-cq-knative-native-mode -o json | grep request -A 1

# Check the knative version (expected 0.16.0)
echo 'Printing knative version (demo has been validated with knative 0.16.0)'
oc get namespace knative-serving -o 'go-template={{index .metadata.labels "serving.knative.dev/release"}}'

# Tune the autoscaler (mainly narrowing down the stable window)
oc apply -f openshift-configs/knative-autoscaler-config.yaml
