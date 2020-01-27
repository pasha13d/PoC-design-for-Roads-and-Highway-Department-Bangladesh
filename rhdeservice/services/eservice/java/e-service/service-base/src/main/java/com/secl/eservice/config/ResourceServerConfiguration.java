package com.secl.eservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String ROOT_PATTERN = "/**";

//	@Autowired
//	private ResourceServerTokenServices tokenServices;
	
	@Override
    public void configure(HttpSecurity http) throws Exception {
		http
			.cors().disable().csrf().disable().httpBasic().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests().antMatchers("/swagger*", ROOT_PATTERN).permitAll()
			.and()
			.authorizeRequests().antMatchers(HttpMethod.POST, "/oauth*", ROOT_PATTERN).permitAll()
			.and()
			.authorizeRequests().antMatchers(HttpMethod.POST, "/public*", ROOT_PATTERN).permitAll()
			.and()
			.authorizeRequests().antMatchers("/actuator*", ROOT_PATTERN).permitAll()
			.and()
			.authorizeRequests().anyRequest().authenticated();
    }
	
	
	@Override
    public void configure(final ResourceServerSecurityConfigurer config) {
        config.tokenServices(tokenServices());
    }
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Bean
	public  ResourceServerTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		defaultTokenServices.setReuseRefreshToken(false);
		return defaultTokenServices;
		}
	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(jdbcTemplate.getDataSource());
	}
}
