package org.ckr.msdemo.reserver.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * Created by Administrator on 2017/9/3.
 */
public interface ResourceServerSecurityConfigurer {

    void configure(HttpSecurity http) throws Exception;

}
