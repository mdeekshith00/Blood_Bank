package com.user_service.service.impl;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.user_service.entities.RefreshToken;
import com.user_service.repositary.RefreshTokenrepositary;
import com.user_service.repositary.UserRepositary;
import com.user_service.service.RefreshTokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl  implements RefreshTokenService {
	
	private final RefreshTokenrepositary refreshTokenRepositary;
	private final UserRepositary rUserRepositary;
	
	public RefreshToken createrefreshToken(String username) {
		RefreshToken refreshtoken = 	RefreshToken.builder()
		                 .user(rUserRepositary.findByUsername(username))
		                 .token(UUID.randomUUID().toString())
		                 .expiryDate(Instant.now())
		                 .build();
		
		return refreshTokenRepositary.save(refreshtoken);
		
	}

	public Optional<RefreshToken> findByToken(String token) {
		 return refreshTokenRepositary.findByToken(token);
	}
	public void deleteToken(String token) {
		  refreshTokenRepositary.findByToken(token);
	}
	public RefreshToken verifyExpiration(RefreshToken token) {
		if(token.getExpiryDate().isBefore(Instant.now())) {
			refreshTokenRepositary.delete(token);
			throw new RuntimeException(token.getToken()  + " Refresh Token Has been expired , Plase sign again :");
		
		}	
		return token;
	}
}
