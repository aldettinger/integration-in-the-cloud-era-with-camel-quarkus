package org.aldettinger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.jboss.logging.Logger;

import io.quarkus.runtime.annotations.RegisterForReflection;

@ApplicationScoped
@Named("step2Bean")
@RegisterForReflection
public class Step2Bean {

    private static final Logger LOG = Logger.getLogger(Step2Bean.class);

    static {
        LOG.info("Simulating a long configuration task taking 3 seconds");
        try {
            Thread.sleep(3000L);
        } catch (java.lang.InterruptedException iex) {
        }
    }

    public String step(String body) {
        return body + " - STEP 2";
    }

}
