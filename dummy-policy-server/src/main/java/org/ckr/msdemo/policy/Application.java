package org.ckr.msdemo.policy;

import org.ckr.msdemo.policy.entity.Policy;
import org.ckr.msdemo.policy.repository.PolicyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    public static Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

    @Autowired
    PolicyRepository policyRepository;

    @RequestMapping(path = "getPO")
    public String getPolicyOwnerName(@RequestParam String policyNo) {
        Policy policy = policyRepository.findAllByPolicyNo(policyNo);
        if (null == policy) {
            return "no policy no found " + policyNo;
        } else {
            return policy.toString();
        }
    }
}