package com.user_service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.user_service.exception.DetailsNotFoundException;
import com.user_service.vo.SessionResponseVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtils {
	
	public CommonUtils() {	
	}
	
	private static void VerifyField(String firstfield , String secondField , String fieldName) {
		log.debug("verifyFields are: {}, {}, {}", fieldName, firstfield, secondField);
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
	
	public static void verifyUserId(String userId) {
		SessionResponseVo sessionResponseVO = sessionResponse();
		VerifyField(userId, sessionResponseVO.getUserId(), "userId");
	}
	

}
