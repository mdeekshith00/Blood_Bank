package com.user_service.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.user_service.entities.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	
	@Value("${jwt.secret}")
	private String secertKey;
	
//	@Value("${jwt.expiration}")
//	private Date expirationTime;
	
	public String generateToken(Users user) {
		Map<String , Object> claims = new HashMap<>();
		claims.put("phoneNumber", user.getPhoneNumber());
		List<String> roles = user.getRoles().stream().map(x ->x.getRole()).collect(Collectors.toList());
		claims.put("role", roles);
		
		return Jwts
				.builder()
				.setClaims(claims)
				.setSubject(user.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 3600000))
				.signWith(getSignKey(), SignatureAlgorithm.HS256)
				.compact();
		
	}

	private Key getSignKey() {
		// TODO Auto-generated method stub
		byte[] keyBytes = Decoders.BASE64.decode(secertKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUserName(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token, Claims::getSubject);
	}
	public String extractPhoneNumber(String token) {
	    return extractAllClaims(token).get("phoneNumber", String.class);
	}

	public List<String> extractRoles(String token) {
	    return extractAllClaims(token).get("roles", List.class);
	} 

	private  <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
	       final Claims claims = extractAllClaims(token);
	       return claimResolver.apply(claims);
	   }

	  private Claims extractAllClaims(String token) {
	         return Jwts.parserBuilder()
	             .setSigningKey(getSignKey())
	             .build()
	             .parseClaimsJws(token)
	             .getBody() ;
	     }

	public boolean validateToken(String token, UserDetails extractUserName) {
		// TODO Auto-generated method stub
		final String username = extractUserName(token);
		return (username.equalsIgnoreCase(extractUserName.getUsername()) && !isTokenValid(token));
	}

	private boolean isTokenValid(String token) {
		// TODO Auto-generated method stub
	return extractExpiration(token).before(new Date());

	}

	private Date extractExpiration(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token, Claims::getExpiration);
	}
	

}
