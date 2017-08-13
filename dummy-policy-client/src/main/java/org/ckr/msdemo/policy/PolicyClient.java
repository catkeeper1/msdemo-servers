package org.ckr.msdemo.policy;

import org.ckr.msdemo.policy.ws.GetPolicyRequest;
import org.ckr.msdemo.policy.ws.GetPolicyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.xml.ws.WebServiceClient;

@WebServiceClient
public class PolicyClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(PolicyClient.class);

    public GetPolicyResponse getPolicy(String name) {

        GetPolicyRequest request = new GetPolicyRequest();
        request.setName(name);

        log.info("Requesting policy for " + name);

        GetPolicyResponse response = (GetPolicyResponse) getWebServiceTemplate()
            .marshalSendAndReceive("http://localhost:8088/ws",
                request,
                new SoapActionCallback(""));

        return response;
    }

}