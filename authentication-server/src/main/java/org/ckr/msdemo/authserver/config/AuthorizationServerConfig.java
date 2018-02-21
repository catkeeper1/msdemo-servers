package org.ckr.msdemo.authserver.config;

import org.ckr.msdemo.authserver.cyrpto.AsymmetricSigner;
import org.ckr.msdemo.authserver.cyrpto.AsymmetricVerifier;
import org.ckr.msdemo.authserver.service.AuthServerClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.jwt.JwtAlgorithms;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.lang.reflect.Field;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * keytool -genkeypair -alias ecc256 -keypass keypassword -keyalg EC -keysize 256 -validity 36500 -keystore d:/authKeyStore.jks -storepass keystorepassword -dname "CN=CN, OU=OU, O=O, L=L, ST=ST, C=C"
 * POST localhost:8081/auth_server/oauth/token?grant_type=password&username=userA&password=passwordA
 * GET localhost:8081/auth_server/oauth/token_key
 * POST localhost:8081/auth_server/oauth/token?grant_type=refresh_token&refresh_token=eyJhbGciOiJFUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VyQSIsInNjb3BlIjpbImFsbCJdLCJhdGkiOiJhMTJmNDdjZC0wZDJiLTQ2MWYtYjQ1Yi00MTNlMGVhNjZjYzYiLCJleHAiOjE1MDUxMjUyMTgsImF1dGhvcml0aWVzIjpbImF1Il0sImp0aSI6ImM2NWM4M2Y5LTE5ZWYtNDQ5NS04NjAyLTNmZDkwYjU0MzA3NCIsImNsaWVudF9pZCI6IkFCQyJ9.MEUCIQDdRhVB-MwQgTNulxDAFlV4tfcQ5mahgK2yTR0pIUAmvAIgAq10sR6rSNs6DvPQwSCsZnikKeZF9FccbXfeAHgc6mw
 * Created by Administrator on 2017/8/13.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
//HS256
//eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDMyMjM1NzAsInVzZXJfbmFtZSI6InVzZXJBIiwiYXV0aG9yaXRpZXMiOlsiYXV0aG9yaXR5QSJdLCJqdGkiOiI1MzZiYTAzOS0yODZjLTQ3NTUtOTJlZi0xMTI3YzhhMjg4YjkiLCJjbGllbnRfaWQiOiJBQkMiLCJzY29wZSI6WyJhbGwiXX0.z6cCm1xr6RsSEsztNdJjTooa8OiOsjPUUTas_1OQrZ0

//ES256
//eyJhbGciOiJFUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDMyNDY2MDgsInVzZXJfbmFtZSI6InVzZXJBIiwiYXV0aG9yaXRpZXMiOlsiYXUiXSwianRpIjoiMWJhMWY5ZDAtZGUyMy00NzBiLThiNmQtOGZjMzQzYjk4NjNjIiwiY2xpZW50X2lkIjoiQUJDIiwic2NvcGUiOlsiYWxsIl19.MEQCIAjqDRv_SwCyov0J7HmanLYJaJb6Vg0NnGzArkEOPRU_AiBwAjhqLH6jFvETFds0ARfYGvL-Q7Dbbld_3DoiWmTYxw

//RSA2048
//eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDMyNDY3MTYsInVzZXJfbmFtZSI6InVzZXJBIiwiYXV0aG9yaXRpZXMiOlsiYXUiXSwianRpIjoiMGQyYzJlNGYtNmJkZC00YTA2LWI3NmEtNWRmNjJlMmJmMDJmIiwiY2xpZW50X2lkIjoiQUJDIiwic2NvcGUiOlsiYWxsIl19.A5cU1rqKQzvihul42RZGbq8Y7hJMQQqSDYx_8EQcdqXWonqJrpV-sRXwlD0Mg1pHT8EkH6XAi1AfuOPeJgXLpSOJKJjJfVLg5uKImmG8ENBRh_WpRFTh_ICV-nYihcX-8XDpb2qv5qgRMMJ7lfeTJrND2P3uZpzaWD_zeE7YISPKtHzI2HGK6o8MOS-cTHy9yHshCokCsLPk731EtYeZ1Ejox28bsaXqaL4wg40-Zm-wwAxy6hzBnqTw4Ja1bZWF-zvZOBJVtFP0hn5QEo-IiSXYxmMwfgrqKccFy6T_J-2fJKaEBaq9B9XaxpAls1mF45Jst2y7_f7tPrvfOldIfg

    @Value("${authsrv.security.keystore.name}")
    private String keyStoreName;

    @Value("${authsrv.security.keystore.password}")
    private String keyStorePassword;

    @Value("${authsrv.security.keystore.key.alias}")
    private String keyAlias;

    @Value("${authsrv.security.keystore.key.password}")
    private String keyPassword;

    @Value("${authsrv.security.algorithm}")
    private String algorithmName;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private AuthServerClientDetailsService authServerClientDetailsService;

    @Autowired
    private TokenEndPointSecurityConfig tokenEnpointSecurityConfig;

    /**
     * Config the security checking for authentication server endpoints.
     *
     * <p>The "oauth/token_key" endpoint should can be access by everyone to download the public key.
     * The "oauth/check_token" is not allowed to be access since this is not a real oauth server that
     * need to support authentication for untrusted 3rd party.
     * The "/oauth/token" endpoint will use the default basic authentication approach.
     *
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll")
                .checkTokenAccess("denyAll()");
                //assign password encoder as below in case it is needed.
                //.passwordEncoder()


        //in case need to support other special authentication approach for client(not for user),
        // need to insert the customized authentication filter as below:
        //security.addTokenEndpointAuthenticationFilter();
        //This customized filter will be placed before the default BasicAuthenticationFilter
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("ABC").secret("EFG")
//                .authorizedGrantTypes("password", "refresh_token")
//                .authorities("AUTHEN_SERVER_CLIENT")
//                .scopes("read", "write", "trust")
//                .accessTokenValiditySeconds(60)
//                .refreshTokenValiditySeconds(160);
        clients.withClientDetails(authServerClientDetailsService);

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(tokenEnpointSecurityConfig.authenticationManagerBean())
                 .accessTokenConverter(jwtAccessTokenConverter)
                 .userDetailsService(this.refreshTokenUserDetailService());

        //use this to customize exception handling: endpoints.exceptionTranslator()
        //endpoints.accessTokenConverter()
        setJwtAlgorithms();
    }

    @Bean
    public TokenUserDetailService refreshTokenUserDetailService() {
        return new TokenUserDetailService();
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() throws Exception {
        JwtAccessTokenConverter result = new AuthServerJwtTokenConverter();

        ClassPathResource res = new ClassPathResource(keyStoreName);

//        KeyStoreKeyFactory keyStoreFactory = new KeyStoreKeyFactory(res, keyStorePassword.toCharArray());
//
//        KeyPair keyPair = keyStoreFactory.getKeyPair(keyAlias, keyPassword.toCharArray());

        KeyStore store = KeyStore.getInstance("jks");
        store.load(res.getInputStream(), this.keyStorePassword.toCharArray());

        PrivateKey privateKey = (PrivateKey) store.getKey(this.keyAlias, this.keyPassword.toCharArray());

        Certificate cert = store.getCertificate(this.keyAlias);

        PublicKey publicKey = (PublicKey) cert.getPublicKey();

        //final String algorithmName = "SHA256withECDSA";
        //final String algorithmName = "SHA256withRSA";

        result.setSigner(new AsymmetricSigner(privateKey, algorithmName));
        result.setVerifier(new AsymmetricVerifier(publicKey, algorithmName));

        result.setVerifierKey(getVerifierKey(publicKey));

        return result;
    }



    public static String getVerifierKey(PublicKey publicKey) {

        String verifierKey = "-----BEGIN PUBLIC KEY-----\n"
                + new String(Base64.encode(publicKey.getEncoded()))
                + "\n-----END PUBLIC KEY-----";

        return verifierKey;


    }


    /**
     * Add new record to the java to JWT algorithm name mapping.<p>
     * The implementation of Spring security oauth server is not good. When we use JWT as token, this server only
     * support SHA with RSA signer. When a new JWT token is generated, this server will call
     * org.springframework.security.jwt.JwtAlgorithms.sigAlg(String) to conver java algorithm name to JWT algorithm
     * name. However, this class store the mapping in a private static Map object. It seems that the author do not want
     * other developer to do enhancement to support other signature algorithm(bad idea). This method use reflect
     * API to access the private field to insert the new algorithm mapping. Otherwise, exception will be thrown
     * (thrown by org.springframework.security.jwt.JwtAlgorithms.sigAlg(String) method)
     * when the org.springframework.security.jwt.JwtHelper try to create a new JWT token.<p>
     * It looks like a hacking, but there is no other better options because of the original design.
     */
    private void setJwtAlgorithms() {

        try {
            //use reflect API to get the Map object from a private field.
            Field field = JwtAlgorithms.class.getDeclaredField("javaToSigAlgs");
            field.setAccessible(true);
            Map<String,String> algMap = (Map<String, String>) field.get(null);
            //insert the new algorithm
            algMap.put("SHA1withECDSA", "ES1");
            algMap.put("SHA256withECDSA", "ES256");
            algMap.put("SHA384withECDSA", "ES384");
            algMap.put("SHA512withECDSA", "ES512");


        } catch (NoSuchFieldException ne) {
            throw new RuntimeException(ne);
        } catch (IllegalArgumentException ie) {
            throw new RuntimeException(ie);
        } catch (IllegalAccessException ie) {
            throw new RuntimeException(ie);
        }

    }

    @Configuration
    public static class TokenEndPointSecurityConfig extends WebSecurityConfigurerAdapter {



        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .withUser("userA").password("passwordA").authorities("au");
        }

        @Bean
        public AuthenticationManager tokenEndPointAuthenticationManager() throws Exception {

            return authenticationManagerBean();

        }

    }

    public static class AuthServerJwtTokenConverter extends JwtAccessTokenConverter {


        /**
         * Override the JwtAccessTokenConverter.isPublic() to make sure it always return true.
         * Otherwise, the "oauth/token_key" endpoint will not work until there is an authentication.
         * It is expected that the "oauth/token_key" endpoint can be access by anyone.
         * @return always return true.
         */
        @Override
        public boolean isPublic() {
            return true;

        }
    }

    /**
     * Used by token refresh function to indicate whether an user account is still active.
     * When an access token is refreshed, this class will be used to load an instance of UserDetails of current.
     * This instance of UserDetails will provide indicators to indicate whether current user account is expired,
     * enabled...
     */
    public static class TokenUserDetailService implements UserDetailsService {

        /**
         * Return an instance of UserDetails for current user acount. The current implementation is always return
         * an enabled, not expired, not locked account. Please chagne this implementation in case need to check the
         * user account setting when access token is refreshed.
         * @param username
         * @return
         * @throws UsernameNotFoundException
         */
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Collection<GrantedAuthority> grantedList = new ArrayList<>();

            grantedList.add(new SimpleGrantedAuthority("au"));

            User user = new User(username,
                                 "",
                                 true,
                                 true,
                                 true,
                                 true, grantedList);

            return user;
        }
    }

}
