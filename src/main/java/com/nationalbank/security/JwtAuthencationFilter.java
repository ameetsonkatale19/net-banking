package com.nationalbank.security;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nationalbank.model.Account;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthencationFilter extends OncePerRequestFilter{

    private Logger logger = LoggerFactory.getLogger(JwtAuthencationFilter.class);

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            String token = requestHeader.substring(7);
            try {
                String username = this.jwtHelper.getUsernameFromToken(token);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    if (this.jwtHelper.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token validation failed");
                        return;
                    }
                }
            } catch (IllegalArgumentException e) {
                logger.error("Illegal Argument while fetching the username !!", e);
            } catch (ExpiredJwtException e) {
                logger.error("Given jwt token is expired !!", e);
            } catch (MalformedJwtException e) {
                logger.error("Some changes have been done in token !! Invalid Token", e);
            } catch (Exception e) {
                logger.error("An error occurred during authentication !!", e);
            }
        } else {
            logger.info("Invalid Header Value !! ");
        }
        filterChain.doFilter(request, response);
    }
	    

}
