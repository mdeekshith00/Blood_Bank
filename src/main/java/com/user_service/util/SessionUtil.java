package com.user_service.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.user_service.exception.DetailsNotFoundException;
import com.user_service.vo.SessionResponseVo;

@Service
public class SessionUtil {
	
	public SessionUtil() {
		
	}
	
	public static SessionResponseVo retriveSession() throws JsonMappingException, JsonProcessingException {
		SecurityContext holder = SecurityContextHolder.getContext();
		Authentication authentication = holder.getAuthentication();
		return APIUtils.getMapper().readValue(String.valueOf(authentication.getPrincipal()), SessionResponseVo.class);
	}
	@SuppressWarnings("unused")
	private static SessionResponseVo sessionResponse() {
		try {
		return 	SessionUtil.retriveSession();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DetailsNotFoundException("");
		}
	
	}
	

}
