package com.secl.eservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class Oauth2GlobalMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

	@Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return methodSecurityExpressionHandler();
    }

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        return new CustomOAuth2MethodSecurityExpressionHandler();
    }

}