package com.user_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class JWTResponse  {
	
	private String accesToken;
	private String token;

}
