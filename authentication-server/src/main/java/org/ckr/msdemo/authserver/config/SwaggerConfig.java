package org.ckr.msdemo.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ExpandedParameterBuilderPlugin;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.spi.service.contexts.ParameterExpansionContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;
import springfox.documentation.swagger.common.SwaggerPluginSupport;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.reverse;

/**
 * Created by Administrator on 2018/11/24.
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Autowired
    private CachingOperationNameGenerator cachingOperationNameGenerator;

    @Bean
    public Docket AuthenticationEnpoints() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/oauth/token"))
                .build();
//                .globalOperationParameters(
//                        newArrayList(new ParameterBuilder()
//                                .name("Authorization")
//                                .description("OAuth2 token")
//                                .modelRef(new ModelRef("string"))
//                                .parameterType("header")
//                                .required(false)
//                                .build()));
    }

    @Bean
    @Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER - 1000)
    public OperationBuilderPlugin apiDescriptions() {

        return new CustomizedOperationPlugin();
    }


    public static class CustomizedOperationPlugin implements OperationBuilderPlugin {


        @Override
        public boolean supports(DocumentationType documentationType) {
            return DocumentationType.SWAGGER_2.equals(documentationType);
        }

        @Override
        public void apply(OperationContext context) {
            if("/oauth/token".equals(context.requestMappingPattern())) {
                context.operationBuilder()
                    .summary("Conduct authentication for end user." +
                             " If authentication passed, return refresh token and access token.")
                    .parameters(newArrayList(
                        new ParameterBuilder()
                            .name("Authorization")
                            .description("The basic authentication token.")
                            .modelRef(new ModelRef("string"))
                            .parameterType("header")
                            .required(true)
                            .build()

                    ));

            }



        }
    }


    @Bean
    @Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER - 1000)
    public ParameterBuilderPlugin parameterDescriptions() {

        return new CustomizedParameterPlugin();
    }

    public static class CustomizedParameterPlugin implements ParameterBuilderPlugin {

        /**
         * Store the info about which parameter should be hidden.
         * The swagger UI will scan the end points already provided by the oauth servers to generate
         * UI for operations automatically. However, these operations include some parameters which
         * is not easy to use. So, we need to hide them. This Map include info about which parameter
         * should be hidden. The key is the path of the operation, the value is a Set of names of
         * parameters need to be hidden.
         */
        private static final Map<String, Set<String>> HIDDEN_PARAMS = new HashMap<>();

        static {
            Set<String> paramNames = new HashSet<>();
            paramNames.add("parameters");
            paramNames.add("name");
            HIDDEN_PARAMS.put("/oauth/token", paramNames);
        }

        @Override
        public void apply(ParameterContext parameterContext) {
            String mappingPath = parameterContext.getOperationContext().requestMappingPattern();
            if(HIDDEN_PARAMS.containsKey(mappingPath)) {
                Set<String> paramNames = HIDDEN_PARAMS.get(mappingPath);
                if(paramNames.contains(parameterContext.resolvedMethodParameter().defaultName().get())) {
                    parameterContext.parameterBuilder().hidden(true);
                }
            }
            return;
        }

        @Override
        public boolean supports(DocumentationType documentationType) {
            return true;
        }
    }

    @Bean
    public ExpandedParameterBuilderPlugin expandedParameterBuilderPlugin() {
        return new CustomizedExpandedParameterPlugin();
    }

    public static class CustomizedExpandedParameterPlugin implements ExpandedParameterBuilderPlugin {

        @Override
        public void apply(ParameterExpansionContext context) {

            context.getParameterBuilder().hidden(true);

            return;
        }

        @Override
        public boolean supports(DocumentationType documentationType) {
            return true;
        }
    }
}
