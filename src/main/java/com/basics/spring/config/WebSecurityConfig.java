package com.basics.spring.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//Déclaration des utilisateurs autorisés
		auth
		.inMemoryAuthentication()
		.withUser("user").password("password").roles("USER")
		.and().withUser("admin").password("password").roles("ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * Ensures that any request to our application requires the user to be authenticated
		 * Allows users to authenticate with form based login
		 * Allows users to authenticate with HTTP Basic authentication
		 */
		http
			//===autorisation===
			.authorizeRequests()
				.antMatchers("/web/admin/**").hasRole("ADMIN")
				.antMatchers("/web/user/**").hasRole("USER")
				.antMatchers("/web/greeting").permitAll()
				//.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
				.anyRequest().authenticated()
			.and()
			
			//===login===
			//Par défaut, spring créé automatiquement un formulaire
			.formLogin() 
				.loginPage("/login") //mais on peut spécifier la page de login
				.permitAll()
			.and()
			
			//===logout===
			.logout()
				.logoutUrl("/logout")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/web/greeting")
				.deleteCookies("JSESSIONID")
				.invalidateHttpSession(true) 
				
			.and()
			.httpBasic();
	}
	
	
	
	// ========================================
	//  Authentification avec un annuaire LDAP
	// ========================================
	
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		//Déclaration des utilisateurs autorisés
//		auth
//			.ldapAuthentication()
//			.userDnPatterns("uid={0},ou=people")
//			.groupSearchBase("ou=groups")
//			.contextSource()
//			.ldif("classpath:users.ldif");
//	}
}
