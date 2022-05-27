package org.aldettinger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.support.jsse.SSLContextParameters;

import static org.apache.camel.ExchangePattern.InOnly;

@ApplicationScoped
public class CamelRoutes extends EndpointRouteBuilder {

    @Inject
    SSLContextParameters ssl;

    @Override
    public void configure() {
        rest("/")
                .id("step-service")
                .get("/steps").to("direct:steps");

        from(direct("steps"))
                .setBody(constant("STEP 1"))
                ;//.to(bean("step2Bean"))
                ;//.to(log("test"))
                ;//.to(InOnly, nats("topic").sslContextParameters(ssl).secure(true));
    }
}
