# Configure the current shell for upstream development
upstream

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
