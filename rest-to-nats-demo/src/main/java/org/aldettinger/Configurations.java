package org.aldettinger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.apache.camel.support.jsse.KeyManagersParameters;
import org.apache.camel.support.jsse.KeyStoreParameters;
import org.apache.camel.support.jsse.SSLContextParameters;
import org.apache.camel.support.jsse.TrustManagersParameters;

import org.apache.camel.component.log.LogComponent;
import org.apache.camel.support.processor.DefaultExchangeFormatter;

@ApplicationScoped
public class Configurations {

    //@Named
    LogComponent log() {
        DefaultExchangeFormatter formatter = new DefaultExchangeFormatter();
        formatter.setShowExchangePattern(false);
        formatter.setShowBodyType(false);

        LogComponent component = new LogComponent();
        component.setExchangeFormatter(formatter);

        return component;
    }

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
