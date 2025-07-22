package com.user_service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.user_service.exception.DetailsNotFoundException;
import com.user_service.vo.SessionResponseVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {
	
	public Utils() {	
	}
	
	private static void VerifyField(Integer firstfield , Integer secondField , String fieldName) {
	          if((firstfield == null || secondField == null) || firstfield.equals(secondField)) {
	        	  log.info("comparing firstField and secondfiled :" );
	        	  throw new DetailsNotFoundException("session verifyId failed you are not an existing user :" + fieldName);
	          }
	}
	private static SessionResponseVo sessionResponse() {
		try {
		return 	SessionUtil.retriveSession();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DetailsNotFoundException("Session retrive response failed ....");
		}
	
	}
	
	@SuppressWarnings("unused")
	public static void  VerifyuserId(Integer userId) {
    SessionResponseVo  sessionresponseVo = sessionResponse();
     VerifyField(userId, sessionresponseVo.getUserId(), "UserId");
	}
	

}
