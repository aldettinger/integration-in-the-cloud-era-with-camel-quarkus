package org.apache.camel.quarkus.example;

import org.apache.camel.builder.RouteBuilder;
import org.jboss.logging.Logger;

public class MyRouteBuilder extends RouteBuilder {

    private static final Logger LOG = Logger.getLogger(MyRouteBuilder.class);

    static {
        LOG.info("Simulating a long configuration task taking 3 seconds");
        try {
            Thread.sleep(3000L);
        } catch (java.lang.InterruptedException iex) {
        }
    }

    @Override
    public void configure() {
        from("platform-http:/hello-camel-quarkus-native-mode").setBody(simple("Hello Camel Quarkus in NATIVE mode"));
    }

}
