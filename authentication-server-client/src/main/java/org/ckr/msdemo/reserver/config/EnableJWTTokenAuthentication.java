package org.ckr.msdemo.reserver.config;

import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2017/9/3.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ResourceServerConfig.class)
public @interface EnableJWTTokenAuthentication {
}
