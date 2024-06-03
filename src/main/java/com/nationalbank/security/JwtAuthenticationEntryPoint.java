package com.nationalbank.security;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
	                     AuthenticationException authException) throws IOException {

	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    PrintWriter writer = response.getWriter();

	    Throwable cause = authException.getCause();
	    if (cause instanceof ExpiredJwtException) {
	        writer.println("Token expired: " + cause.getMessage());
	    } else if (cause instanceof MalformedJwtException) {
	        writer.println("Invalid token: " + cause.getMessage());
	    } else {
	        writer.println("Access Denied !! " + authException.getMessage());
	    }
	}



}
