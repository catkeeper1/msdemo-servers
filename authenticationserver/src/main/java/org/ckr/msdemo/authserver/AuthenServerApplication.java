package org.ckr.msdemo.authserver;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = "org.ckr.msdemo")
public class AuthenServerApplication {



    /**
     * The entry point for the authentication server.
     * @param args Parameters for spring boot application initialization.
     */
    public static void main(String[] args) {

        new SpringApplicationBuilder(AuthenServerApplication.class)
                .properties("spring.config.name=auth_server").run(args);

    }
}