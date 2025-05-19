package com.joaquinrouge.donelt.user.configuration.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.joaquinrouge.donelt.user.utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidator extends OncePerRequestFilter{

	private JwtUtils utils;
	
	public JwtValidator(JwtUtils utils) {
		this.utils = utils;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)throws ServletException, IOException {
		
		String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if(jwt != null) {
			
			jwt = jwt.substring(7);
		
			try {
				DecodedJWT decodedToken = utils.validateJwt(jwt);
				
				String username = utils.getUsername(decodedToken);
				
				
				SecurityContext context = SecurityContextHolder.getContext();
				
				Authentication auth = new UsernamePasswordAuthenticationToken(username,null,List.of());
				
				context.setAuthentication(auth);
				
				SecurityContextHolder.setContext(context);
				
			}catch(JWTVerificationException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{ \"error\": \"Token inválido o expirado\" }");
                return;
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}
