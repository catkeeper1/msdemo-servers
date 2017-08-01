package org.ckr.msdemo.policy.repository;

import org.ckr.msdemo.policy.entity.Policy;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by yukai.a.lin on 7/31/2017.
 */
@EnableJpaRepositories("org.ckr.msdemo.policy.repository")
public interface PolicyRepository extends CrudRepository<Policy, String> {
    Policy findAllByPolicyNo(String policyNo);
}
