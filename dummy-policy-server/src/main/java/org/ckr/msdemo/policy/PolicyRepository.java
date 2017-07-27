package org.ckr.msdemo.policy;

import org.ckr.msdemo.policy.ws.Policy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;


/**
 * @author yukai.a.lin
 */
@Component
public class PolicyRepository {
    private static final Map<String, Policy> policies = new HashMap<>();

    /**
     * initial test data
     */
    @PostConstruct
    public void initData() {
        Policy policy0 = new Policy();
        policy0.setPolicyno("11223344");
        policy0.setPolicystatus(20);
        policy0.setInceptionDate(null);
        policy0.setCurrency("CNY");
        policies.put(policy0.getPolicyno(), policy0);

        Policy policy1 = new Policy();
        policy1.setPolicyno("22334455");
        policy1.setPolicystatus(21);
        policy1.setInceptionDate(null);
        policy1.setCurrency("USD");
        policies.put(policy1.getPolicyno(), policy1);

        Policy policy2 = new Policy();
        policy2.setPolicyno("33445566");
        policy2.setPolicystatus(22);
        policy2.setInceptionDate(null);
        policy2.setCurrency("MYR");
        policies.put(policy2.getPolicyno(), policy2);

        Policy policy3 = new Policy();
        policy3.setPolicyno("44556677");
        policy3.setPolicystatus(24);
        policy3.setInceptionDate(null);
        policy3.setCurrency("CNY");
        policies.put(policy3.getPolicyno(), policy3);

        Policy policy4 = new Policy();
        policy4.setPolicyno("55667788");
        policy4.setPolicystatus(99);
        policy4.setInceptionDate(null);
        policy4.setCurrency("CNY");
        policies.put(policy4.getPolicyno(), policy4);
    }

    /**
     * @param policyNo
     * @return Policy
     */
    public Policy findPolicy(String policyNo) {
        Assert.notNull(policyNo, "The Policy Number must not be null");
        return policies.get(policyNo);
    }

}