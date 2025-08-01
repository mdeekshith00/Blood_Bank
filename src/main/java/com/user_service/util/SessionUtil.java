package com.user_service.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.user_service.entities.Users;
import com.user_service.exception.DetailsNotFoundException;
import com.user_service.vo.SessionResponseVo;

@Service
public class SessionUtil {
	
	public SessionUtil() {
		
	}
	
	public static SessionResponseVo retriveSession() throws JsonMappingException, JsonProcessingException {
		SecurityContext holder = SecurityContextHolder.getContext();
		Authentication authentication = holder.getAuthentication();
//		System.out.println(String.valueOf(auth entication.getPrincipal()));
		return APIUtils.getMapper().readValue(String.valueOf(authentication.getPrincipal()), SessionResponseVo.class);
		
	}

	public static Object createSession(Users user) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
