package org.ckr.msdemo.reserver.config;

import org.ckr.msdemo.authserver.cyrpto.AsymmetricVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.Base64Utils;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/3.
 */
//@Configuration
//@EnableResourceServer
@EnableConfigurationProperties(ResourceServerConfig.class)
@ConfigurationProperties(prefix = "ressrv")
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static Logger LOG = LoggerFactory.getLogger(ResourceServerConfig.class);

    private String publickey = null;

    private String publicKeyUrl = null;

    private String publicKeyAlgorithm = "EC";

    private String verifyAlgorithm = "SHA256withECDSA";

    private List<ResourceServerSecurityConfigurer> secuirtyConfigurerList = new ArrayList<>();

    @Autowired(required = false)
    public void setSecuirtyConfigurerList(List<ResourceServerSecurityConfigurer> secuirtyConfigurerList) {
        this.secuirtyConfigurerList = secuirtyConfigurerList;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public void setPublicKeyUrl(String publicKeyUrl) {
        this.publicKeyUrl = publicKeyUrl;
    }

    public void setPublicKeyAlgorithm(String publicKeyAlgorithm) {
        this.publicKeyAlgorithm = publicKeyAlgorithm;
    }

    public void setVerifyAlgorithm(String verifyAlgorithm) {
        this.verifyAlgorithm = verifyAlgorithm;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        LOG.info("init JWT token authencation.");
        resources.tokenStore(new JwtTokenStore(this.jwtAccessTokenConverter()));
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        for(ResourceServerSecurityConfigurer configuer : secuirtyConfigurerList) {
            configuer.configure(http);
        }

    }


    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter()  {
        LOG.info("init jwtAccessTokenConverter. Verify algorighm is {}. Public key algorithm is {}",
                 this.verifyAlgorithm,
                 this.publicKeyAlgorithm);

        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        String keyStr = getPublicKeyData();

        byte[] keyBytes = Base64Utils.decodeFromString(keyStr);
        try {
            X509EncodedKeySpec spec =
                    new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance(this.publicKeyAlgorithm);
            PublicKey publicKey = kf.generatePublic(spec);

            converter.setVerifier(new AsymmetricVerifier(publicKey, this.verifyAlgorithm));
        } catch (Exception ie) {
            throw new RuntimeException("", ie);
        }
        return converter;
    }

    private String getPublicKeyData() {

        if(this.publickey != null && !"".equals(this.publickey)) {
            LOG.info("The public key is {}.", publickey);
            return publickey;
        }

        //will adjust later, use URL to retrieve public key from authentication server.
        LOG.info("The public key is {}.", publickey);
        return publickey;
    }
}
