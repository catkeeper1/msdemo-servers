package org.ckr.msdemo.authserver.config;

import org.ckr.msdemo.authserver.service.ClientUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Administrator on 2017/8/19.
 */
@EnableWebSecurity
public class WebSecurityConfig {



    //@Configuration
    @Order(1)
    public static class AuthenticationServerSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private ClientUserDetailsService clientUserDetailsService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http
                .authorizeRequests()
                .antMatchers("/oauth/token")
                    .hasAnyRole("AUTHEN_SERVER_CLIENT")
                .antMatchers("/oauth/authorize")
                    .denyAll()
                .antMatchers("/oauth/check_token")
                    .denyAll()
                .antMatchers("/oauth/confirm_access")
                    .denyAll()
//            .and()
//                .httpBasic();
            // @formatter:on
;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        }


    }

}
