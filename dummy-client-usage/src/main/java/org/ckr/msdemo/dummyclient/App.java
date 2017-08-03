package org.ckr.msdemo.dummyclient;

import org.ckr.msdemo.policy.PolicyClient;
import org.ckr.msdemo.policy.PolicyConfiguration;
import org.ckr.msdemo.policy.ws.GetPolicyResponse;


public class App {

    public static void main(String[] args) {
        String name = "75006884";

        if (args.length > 0) {
            name = args[0];
        }

        PolicyConfiguration policyConfiguration = new PolicyConfiguration();
        PolicyClient policyClient = policyConfiguration.policyClient(policyConfiguration.marshaller());
        GetPolicyResponse response = policyClient.getPolicy(name);
        System.err.println(response.getPolicy().getPolicyno() + " " + response.getPolicy().getPolicystatus());
    }


}