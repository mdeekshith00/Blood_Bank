package com.user_service.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionResponseVo {
	
	private String JWt_UserId;
	private List<String> role;
	

}
