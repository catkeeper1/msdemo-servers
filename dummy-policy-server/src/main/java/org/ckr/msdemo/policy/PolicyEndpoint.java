package org.ckr.msdemo.policy;

import org.ckr.msdemo.policy.ws.GetPolicyRequest;
import org.ckr.msdemo.policy.ws.GetPolicyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * @author yukai.a.lin
 */
@Endpoint
public class PolicyEndpoint {
    private static final String NAMESPACE_URI = "http://policy.msdemo.ckr.org/ws";

    private PolicyRepository policyRepository;

    @Autowired
    public PolicyEndpoint(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPolicyRequest")
    @ResponsePayload
    public GetPolicyResponse getPolicy(@RequestPayload GetPolicyRequest request) {
        GetPolicyResponse response = new GetPolicyResponse();
        response.setPolicy(policyRepository.findPolicy(request.getName()));

        return response;
    }
}
