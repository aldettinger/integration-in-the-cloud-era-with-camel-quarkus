# Check the knative version (expected 0.16.0)
echo 'Printing knative version (demo has been validated with knative 0.16.0)'
oc get namespace knative-serving -o 'go-template={{index .metadata.labels "serving.knative.dev/release"}}'
echo

# Tune the autoscaler (mainly narrowing down the stable window)
KNATIVE_AUTOSCALER_CONFIG=$(cat <<'END_HEREDOC'
apiVersion: v1
data:
  container-concurrency-target-default: "2"
  container-concurrency-target-percentage: "0.7"
  scale-to-zero-grace-period: 10s
  scale-to-zero-pod-retention-period: 0s
  stable-window: 10s
kind: ConfigMap
metadata:
  annotations:
  labels:
    serving.knative.dev/release: v0.16.0
  name: config-autoscaler
  namespace: knative-serving
END_HEREDOC
)
echo "${KNATIVE_AUTOSCALER_CONFIG}" | oc apply -f -
echo 'Tuned the knative autoscaler stable window'

# Configure the current shell for upstream development
main_upstream

# Init the environment variables porting interesting urls
export HELLO_JVM_URL=$(kn service list -o json | jq -r '.items[] | select(.metadata.name == "hello-cq-knative-jvm-mode") | .status.url')
export HELLO_NATIVE_URL=$(kn service list -o json | jq -r '.items[] | select(.metadata.name == "hello-cq-knative-native-mode") | .status.url')
export BYE_JVM_URL=$(kn service list -o json | jq -r '.items[] | select(.metadata.name == "bye-cq-knative-jvm-mode") | .status.url')
export BYE_NATIVE_URL=$(kn service list -o json | jq -r '.items[] | select(.metadata.name == "bye-cq-knative-native-mode") | .status.url')

# Create aliases
alias hello-jvm="hey -t 180 -n 1 -c 1 ${HELLO_JVM_URL}/hello-camel-quarkus-jvm-mode | grep Total"
alias hello-native="hey -t 180 -n 1 -c 1 ${HELLO_NATIVE_URL}/hello-camel-quarkus-native-mode | grep Total"
alias bye-jvm="hey -t 180 -n 1 -c 1 ${BYE_JVM_URL}/bye-camel-quarkus-jvm-mode | grep Total"
alias bye-native="hey -t 180 -n 1 -c 1 ${BYE_NATIVE_URL}/bye-camel-quarkus-native-mode | grep Total"

# Print usage message
echo 'Shell prepared to run the demo, you might be able to invoke hello-jvm, hello-native, bye-jvm and bye-native commands'
