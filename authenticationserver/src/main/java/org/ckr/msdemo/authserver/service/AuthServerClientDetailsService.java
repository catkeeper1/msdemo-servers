package org.ckr.msdemo.authserver.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Administrator on 2017/8/19.
 */
@Service
public class AuthServerClientDetailsService implements ClientDetailsService {

    private static final Collection<GrantedAuthority> authorities = new ArrayList<>();

    static {
        authorities.add(new SimpleGrantedAuthority("CA"));
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        BaseClientDetails result = new BaseClientDetails();

        result.setClientId(clientId);
        result.setClientSecret("EFGH");

        Collection<String> grantTypes = new ArrayList<>();
        grantTypes.add("password");
        grantTypes.add("refresh_token");
        result.setAuthorizedGrantTypes(grantTypes);

        result.setAuthorities(authorities);

        result.setRefreshTokenValiditySeconds(3600*24);
        result.setAccessTokenValiditySeconds(3600);

        Collection<String> scope = new ArrayList<>();
        scope.add("all");
        result.setScope(scope);

        return result;
    }
}
