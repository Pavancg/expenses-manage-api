package com.cgpk.expensetracker.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity.JwtSpec;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.cgpk.expensetracker.service.CustomeUserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenGenerator {

    int EXPIRY_TIME  = 5 * 60 * 60;
	
    @Value("${jwt.secret}")
    private String secret;
    
	public String getJwtToken(UserDetails userDetails) {
		
		Map<String, Object> claimsMap= new HashMap<>();
		
		return Jwts.builder()
	        .setClaims(claimsMap)
	        .setSubject(userDetails.getUsername())
	        .setIssuedAt(new Date(System.currentTimeMillis()))
	        .setExpiration(new Date(System.currentTimeMillis() + EXPIRY_TIME *1000))
	        .signWith(SignatureAlgorithm.HS512, secret)
	        .compact();
		
	}
	
	public String getUserName(String jwtToken) {
	  return getClaimFromToken(jwtToken, Claims::getSubject);	
	}
	
	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims =  Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return claimsResolver.apply(claims);
	}
	
	
	public boolean validateJwtToken(String token, UserDetails userDetails) {
		
		String userName = getUserName(token);
		return  userName.equals(userDetails.getUsername()) &&  !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		Date expDate = getExpirationDate(token);
		return expDate.before(new Date());
	}
	
	
	private Date getExpirationDate(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
}
