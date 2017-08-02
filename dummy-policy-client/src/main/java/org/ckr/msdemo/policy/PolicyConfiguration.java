package org.ckr.msdemo.policy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class PolicyConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("org.ckr.msdemo.policy.ws");
        return marshaller;
    }

    @Bean
    public PolicyClient policyClient(Jaxb2Marshaller marshaller) {
        PolicyClient client = new PolicyClient();
        client.setDefaultUri("http://localhost:8088/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}