package com.cgpk.expensetracker.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;
import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cgpk.expensetracker.service.CustomeUserService;
import com.cgpk.expensetracker.util.JwtTokenGenerator;

import io.jsonwebtoken.ExpiredJwtException;

public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	JwtTokenGenerator tokenGenerator;

	@Autowired
	CustomeUserService uCustomeUserService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String tokenString = request.getHeader("Authorization");

		String jwtTokenString = null;
		String userNameString = null;
		if (tokenString != null && tokenString.startsWith("Bearer ")) {
			jwtTokenString = tokenString.substring(7);
			try {
				userNameString = tokenGenerator.getUserName(jwtTokenString);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException("Unable to get jwt token");
			} catch (ExpiredJwtException exception) {
				throw new RuntimeException("JWT token expired");
			}
		}

		if (userNameString != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = uCustomeUserService.loadUserByUsername(userNameString);
			if (tokenGenerator.validateJwtToken(jwtTokenString, userDetails)) {

				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}

		}
		filterChain.doFilter(request, response);
	}

}
