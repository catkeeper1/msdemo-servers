package org.ckr.msdemo.policy.repository;

import org.ckr.msdemo.policy.entity.Policy;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by yukai.a.lin on 7/31/2017.
 */
public interface PolicyRepository extends CrudRepository<Policy, String> {
    Policy findAllByPolicyNo(String policyNo);

}
