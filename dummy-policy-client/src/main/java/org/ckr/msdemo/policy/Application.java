package org.ckr.msdemo.policy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

//    @Bean
//    CommandLineRunner lookup(PolicyClient policyClient) {
//        return args -> {
//            String name = "11223344";
//
//            if (args.length > 0) {
//                name = args[0];
//            }
//            GetPolicyResponse response = policyClient.getPolicy(name);
//            System.err.println(response.getPolicy().getPolicyno() + " " + response.getPolicy().getPolicystatus());
//        };
//    }

}