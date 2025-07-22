package com.user_service.service;

import java.util.Optional;

import com.user_service.entities.RefreshToken;

public interface RefreshTokenService {
	
	RefreshToken createrefreshToken(String username);
	
	Optional<RefreshToken> findByToken(String token);
	
	void deleteToken(String token);
	
	RefreshToken verifyExpiration(RefreshToken token);

}
