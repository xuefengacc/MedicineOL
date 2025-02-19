package com.xuefeng.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * Configuration for the oauth2 authorization server. the client and server are configured here
 * */

//@Configuration
//@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Bean
	public InMemoryTokenStore tokenStore() {
	    return new InMemoryTokenStore();
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer configurer) throws Exception {
		configurer.authenticationManager(authenticationManager);
		configurer.userDetailsService(userDetailsService);
		configurer.tokenStore(tokenStore());
	}

	
}
