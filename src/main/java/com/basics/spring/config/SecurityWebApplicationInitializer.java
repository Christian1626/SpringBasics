package com.basics.spring.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

// Would simply only register the springSecurityFilterChain Filter for every URL in your application
public class SecurityWebApplicationInitializer
extends AbstractSecurityWebApplicationInitializer {

//	public SecurityWebApplicationInitializer() {
//		super(WebSecurityConfig.class);
//	}
}