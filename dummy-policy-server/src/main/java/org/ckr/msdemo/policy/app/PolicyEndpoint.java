package org.ckr.msdemo.policy.app;

import org.apache.commons.logging.Log;
import org.ckr.msdemo.policy.entity.Policy;
import org.ckr.msdemo.policy.repository.PolicyRepository;
import org.ckr.msdemo.policy.ws.GetPolicyRequest;
import org.ckr.msdemo.policy.ws.GetPolicyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


/**
 * @author yukai.a.lin
 */
@Endpoint
@Service
public class PolicyEndpoint {
    private static final String NAMESPACE_URI = "http://policy.msdemo.ckr.org/ws";

    public static Logger log = LoggerFactory.getLogger(PolicyEndpoint.class);

    @Autowired
    PolicyRepository policyRepository;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPolicyRequest")
    @ResponsePayload
    public GetPolicyResponse getPolicy(@RequestPayload GetPolicyRequest request) {
        
        GetPolicyResponse response = new GetPolicyResponse();
         
        Policy policy = policyRepository.findAllByPolicyNo(request.getName());
        org.ckr.msdemo.policy.ws.Policy policy1 = new org.ckr.msdemo.policy.ws.Policy();
        policy1.setCurrency(policy.getCurrency());
        policy1.setPolicyno(policy.getPolicyNo());
        policy1.setPolicystatus(Integer.valueOf(policy.getStatusCode()));
        response.setPolicy(policy1);
        return response;
    }
}
