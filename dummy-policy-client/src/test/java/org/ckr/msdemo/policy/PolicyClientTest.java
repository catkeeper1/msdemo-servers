package org.ckr.msdemo.policy;

import static org.assertj.core.api.Assertions.assertThat;

import org.ckr.msdemo.policy.ws.GetPolicyResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yukai.a.lin on 8/2/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {PolicyConfiguration.class})
public class PolicyClientTest {

    @Autowired
    PolicyConfiguration policyConfiguration;

    @Test
    public void getPolicyResponseTest() throws Exception {
        PolicyClient policyClient = policyConfiguration.policyClient(policyConfiguration.marshaller());
        GetPolicyResponse response = policyClient.getPolicy("75006884");
        System.out.println("===================================================");
//        assertThat(response.getPolicy().getPolicyno()).isEqualTo("75006884");
//        assertThat(response.getPolicy().getPolicystatus()).isEqualTo(2);
//        assertThat(response.getPolicy().getCurrency()).isEqualTo("MYR");
    }
}
