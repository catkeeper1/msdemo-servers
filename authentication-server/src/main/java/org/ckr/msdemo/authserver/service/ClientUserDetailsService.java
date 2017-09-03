package org.ckr.msdemo.authserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Administrator on 2017/8/19.
 */
//@Service
//public class ClientUserDetailsService implements UserDetailsService {
//
//    private static Logger LOG = LoggerFactory.getLogger(ClientUserDetailsService.class);
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        LOG.info("Load user detail with user name = {}", username);
//
//        return new ClientUserDetails(username, "DEF");
//    }
//
//
//    public static class ClientUserDetails implements UserDetails {
//        private String username;
//        private String password;
//
//        private static final Collection<GrantedAuthority> authorities = new ArrayList<>();
//
//        static {
//            authorities.add(new SimpleGrantedAuthority("CA"));
//        }
//
//        public ClientUserDetails(String username, String password) {
//            this.username = username;
//            this.password = password;
//        }
//
//        @Override
//        public Collection<? extends GrantedAuthority> getAuthorities() {
//            return authorities;
//        }
//
//        @Override
//        public String getPassword() {
//            return this.password;
//        }
//
//        @Override
//        public String getUsername() {
//            return username;
//        }
//
//        @Override
//        public boolean isAccountNonExpired() {
//            return true;
//        }
//
//        @Override
//        public boolean isAccountNonLocked() {
//            return true;
//        }
//
//        @Override
//        public boolean isCredentialsNonExpired() {
//            return true;
//        }
//
//        @Override
//        public boolean isEnabled() {
//            return true;
//        }
//    }
//}
