package com.nationalbank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.nationalbank.security.JwtAuthencationFilter;
import com.nationalbank.security.JwtAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationEntryPoint point;
	
	@Autowired
	private JwtAuthencationFilter filter;
	
	@Bean
	public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
		
		http.csrf(csrf-> csrf.disable())
			.authorizeHttpRequests(auth-> auth.requestMatchers("/account/**").authenticated()
											  .requestMatchers("/transaction/**").authenticated()
											  .requestMatchers("/statement/**").authenticated()
					                          .requestMatchers("/auth/create-user").permitAll()
					                          .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
											  .requestMatchers("/auth/login").permitAll()
											  .anyRequest().authenticated())
								.exceptionHandling(ex-> ex.authenticationEntryPoint(point))
								.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
								.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
											  
	}
}
