package org.aldettinger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Named;

import org.apache.camel.support.jsse.KeyManagersParameters;
import org.apache.camel.support.jsse.KeyStoreParameters;
import org.apache.camel.support.jsse.SSLContextParameters;
import org.apache.camel.support.jsse.TrustManagersParameters;

import org.apache.camel.component.log.LogComponent;
import org.apache.camel.quarkus.core.events.ComponentAddEvent;
import org.apache.camel.support.processor.DefaultExchangeFormatter;

@ApplicationScoped
public class Configurations {

    // Customize a component created by camel-quarkus via an observer method
    public void onComponentAdd(@Observes ComponentAddEvent event) {
        if(event.getComponent() instanceof LogComponent) {
            LogComponent logComponent = (LogComponent)event.getComponent();

            DefaultExchangeFormatter formatter = new DefaultExchangeFormatter();
            formatter.setShowExchangePattern(false);
            formatter.setShowBodyType(false);

            //logComponent.setExchangeFormatter(formatter);
        }
    }

    // Put a bean in the Camel/Quarkus registry via a producer method
    @Named
    SSLContextParameters ssl() {
        SSLContextParameters sslContextParameters = new SSLContextParameters();

        KeyManagersParameters keyManagersParameters = new KeyManagersParameters();
        KeyStoreParameters keyStore = new KeyStoreParameters();
        keyStore.setPassword("password");
        keyStore.setResource("certs/keystore.jks");
        keyManagersParameters.setKeyPassword("password");
        keyManagersParameters.setKeyStore(keyStore);
        sslContextParameters.setKeyManagers(keyManagersParameters);

        TrustManagersParameters trustManagersParameters = new TrustManagersParameters();
        KeyStoreParameters trustStore = new KeyStoreParameters();
        trustStore.setPassword("password");
        trustStore.setResource("certs/truststore.jks");
        trustManagersParameters.setKeyStore(trustStore);
        sslContextParameters.setTrustManagers(trustManagersParameters);

        return sslContextParameters;
    }

}
