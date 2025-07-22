package com.user_service.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.user_service.dto.JWTResponse;
import com.user_service.dto.MinUserDto;
import com.user_service.dto.RefreshTokenRequest;
import com.user_service.dto.SearchDto;
import com.user_service.dto.UserDto;
import com.user_service.entities.RefreshToken;
import com.user_service.entities.Role;
import com.user_service.entities.Users;
import com.user_service.exception.DetailsNotFoundException;
import com.user_service.exception.UserDetailsNotFoundException;
import com.user_service.repositary.RoleRepositary;
import com.user_service.repositary.UserRepositary;
import com.user_service.service.UsersService;
import com.user_service.util.CommonConstants;
import com.user_service.util.Utils;
import com.user_service.vo.UsersVo;
import com.user_service.vo.loginUservo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersServiceImpl implements UsersService {
	
	private final UserRepositary userRepositary;
	private final RoleRepositary roleRepositary;
	private final JWTService jwtServcie;
	private final ModelMapper uModelMapper;
	private final AuthenticationManager authManager;
	private final RefreshTokenServiceImpl refreshTokenServiceImpl;
	
	private  BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	
	@Override
	public UserDto register(UsersVo userVo) {
		// TODO Auto-generated method stub
	   Users user = new Users();
	   log.info("user register...." );
		 user = Users.builder()
				.fullName(userVo.getFullname())
				.username(userVo.getUsername())
				.password(encoder.encode(userVo.getPassword()))
				.eMail(userVo.getEMail())
				.phoneNumber(userVo.getPhoneNumber())
				.bloodGroup(userVo.getBloodGroup().toString())
				.gender(userVo.getGender().toString())
				.addressType(userVo.getAddressType().toString())
				.address(userVo.getAddress())
                .isAvailableToDonate(userVo.getIsAvailableToDonate())
                .dateOfBirth(userVo.getDateOfBirth())
                .createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.lastDonationDate(null)
				.bio(userVo.getBio())
		
				.roles(userVo.getRoles().stream()
					    .map(r -> uModelMapper.map(r, Role.class)) 
					    .map(roleRepositary::save)            
					    .collect(Collectors.toSet())) 
				.build();
		user = userRepositary.save(user);
		UserDto userDto = 	uModelMapper.map(user, UserDto.class);	
		userDto.setStatus(CommonConstants.SUCESS);
		return userDto;
	}
	@Override
	@Transactional
	public JWTResponse  login(loginUservo loginUservo) {
		Users user = userRepositary.findByUsername(loginUservo.getUsername());
		
		user.setIsActive(true);
		user.setLastLogin(Timestamp.from(Instant.now()));
		user.setIsPhoneNumberVerified(Boolean.TRUE);
        user.setLoginCount(Optional.ofNullable(user.getLoginCount())
        		.map(count -> count+1).orElse((long) 1) );
        
		userRepositary.save(user);
		log.info("User in DB: " + user.getUsername());
		
		log.info("Password in DB: " + user.getPassword());
		log.info("Input password: " + loginUservo.getPassword());
		log.info("Matches? " + encoder.matches(loginUservo.getPassword(), user.getPassword()));
		
		Authentication authentication  = 
				authManager.authenticate(new UsernamePasswordAuthenticationToken(loginUservo.getUsername(), loginUservo.getPassword()));
		RefreshToken token =  refreshTokenServiceImpl.createrefreshToken(loginUservo.getUsername());

		  if(authentication.isAuthenticated()) 
                     jwtServcie.generateToken(user) ;
                     return  JWTResponse.builder()
                                  .accesToken(jwtServcie.generateToken(user))
                                  .token(token.getToken())
                                  .build();
	}

	@Override
	public UserDto getUsersById(Integer userId) {
		// TODO Auto-generated method stub
		Utils.VerifyuserId(userId);
		log.debug("user id verified :" + userId);
	  Users user = userRepositary.findById(userId)
			  .orElseThrow(() ->  new UserDetailsNotFoundException(CommonConstants.USER_DATA_NOTFOUND_WITH_GIVEN_ID + userId));
	  
	  UserDto uDto = new UserDto();
	  
	  if(user.getIsActive()) {   
		  uDto =  uModelMapper.map(user, UserDto.class);
	  }else {
		  throw new UserDetailsNotFoundException(CommonConstants.USER_NOT_IN_ACTIVE + CommonConstants.UPDATE_THE_STATUS);
	  }
		return uDto;
	}

	@Override
	@Transactional
	public MinUserDto updateUsers(Integer userId, UsersVo userVo) {
		// TODO Auto-generated method stub
		Utils.VerifyuserId(userId);
		  Users user = userRepositary.findById(userId)
				  .orElseThrow(() ->  new UserDetailsNotFoundException(CommonConstants.USER_DATA_NOTFOUND_WITH_GIVEN_ID+ userId) );
		  if(Boolean.TRUE.equals(user.getIsActive())) {
//		  user.setAddressType(userVo.getAddressType().toString());
		  user.setUpdatedAt(LocalDateTime.now());
		  user.setEMail(userVo.getEMail());
		  user.setGender(userVo.getGender().toString());
		  user.setIsAvailableToDonate(userVo.getIsAvailableToDonate());
		  user.setDateOfBirth(userVo.getDateOfBirth());
		  user = userRepositary.save(user);
		  }
		  else {
			  throw new UserDetailsNotFoundException(CommonConstants.USER_NOT_IN_ACTIVE + CommonConstants.UPDATE_THE_STATUS);
		  } 
		  MinUserDto minUserDto =   uModelMapper.map(user, MinUserDto.class);
		return minUserDto;
	}

	@Override
	@Transactional
//	@CacheEvict(value = "users", key = "#userId")
	public String deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		Utils.VerifyuserId(userId);
	  Users user = 	userRepositary.findById(userId)
		.orElseThrow(() ->  new UserDetailsNotFoundException(CommonConstants.USER_DATA_NOTFOUND_WITH_GIVEN_ID+ userId) );
	  
	    if(Boolean.TRUE.equals(user.getIsActive()) && Boolean.TRUE.equals(user.getIsPhoneNumberVerified()) &&
	    		Boolean.TRUE.equals(user.getIsAvailableToDonate())) 
	    {
		
		Optional.ofNullable(user.getIsActive().equals(null)).orElse(null);
		Optional.ofNullable(user.getIsPhoneNumberVerified().equals(null)).orElse(null);
		Optional.ofNullable(user.getIsAvailableToDonate().equals(null)).orElse(null);
		
	    }
	    else {
	    	throw new DetailsNotFoundException(CommonConstants.USER_NOT_THERE_TO_DELETE + user.getUsername());
	    }
		log.info("delted user .." + userId);
	
		return "User deleted on this Id: " + user.getUserId() + " on this Username " + user.getUsername();
	}

	@Override
	@Cacheable(value = "Users" , key = "#userId")
	public List<Users>  getAllUsers() {
		// TODO Auto-generated method stub
//		Utils.VerifyuserId(userId);
	  List<Users> users = 	userRepositary.findAll();
	  
	          users
	          .parallelStream()
	          .filter(user -> user.getIsActive().equals(Boolean.TRUE))
	          .filter(user -> user.isAccountNonExpired())
	          .filter(user -> user.getIsAvailableToDonate())
	          .filter(user -> user.getIsPhoneNumberVerified())
	          ;
	          
		return users;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Page<SearchDto> getPaginatedUsersandBloodGroup(int page, int size, String bloodGroup) {
		// TODO Auto-generated method stub
		Pageable pageable = (Pageable) PageRequest.of(page, size);
		Page<Users> user = 	userRepositary.findByBloodGroup(bloodGroup, pageable);
		Page<SearchDto> dto =  (Page<SearchDto>) uModelMapper.map(user, SearchDto.class);
		return  dto;
	}
	@Override
	@Transactional
	public String forgotPassword(String username) {
	Users user = 	userRepositary.findByUsername(username);
//	Utils.VerifyuserId(user.getUserId());
	String resetPassword = null ;
	if(user != null && user.getIsActive().equals(Boolean.TRUE) && user.getIsPhoneNumberVerified().equals(Boolean.TRUE)) {
		 resetPassword = user.getPhoneNumber() + UUID.randomUUID()+Instant.now().toString();
		user.setResetToken(resetPassword);
		userRepositary.save(user);
	} else {
		throw new DetailsNotFoundException("User Details Not Found on this Username :" + username);
	}
		return "reset your password with : " + resetPassword;
	}
	
	@Transactional
	@Override
	public String resetPassword(String username , String resetPassword , String password) {
		Users user = 	userRepositary.findByUsername(username);
//		Utils.VerifyuserId(user.getUserId());
		if(user != null && user.getIsActive().equals(Boolean.TRUE) && user.getIsPhoneNumberVerified().equals(Boolean.TRUE)) {
			if(user.getResetToken().equalsIgnoreCase(resetPassword)) {
		                     	user.setResetToken(null);
			} else {
				throw new DetailsNotFoundException("Bad credetials Entered: ");
			}
			user.setPassword(encoder.encode(password));
			userRepositary.save(user);
		} else {
			throw new DetailsNotFoundException(CommonConstants.USER_DETAILS_NOTFOUND_ID + username);
		}
		return "Password Updated for User " + username;
	}
	@Override
	public JWTResponse refreshToken(RefreshTokenRequest request) {
		 return  refreshTokenServiceImpl.findByToken(request.getRefreshToken())
		                                           .map(refreshTokenServiceImpl::verifyExpiration)
		                                           .map(RefreshToken::getUser)
		                                           .map(user -> {
		                                        	   refreshTokenServiceImpl.deleteToken(request.getRefreshToken());
		                                        	   String newAccesToken = jwtServcie.generateToken(user);
		                                        	   @SuppressWarnings("unused")
													String newRefreshToken = refreshTokenServiceImpl.createrefreshToken(user.getUsername()).getToken();
		                                              return JWTResponse.builder()
		                                            		           .accesToken(newAccesToken)
		                                            		           .token(request.getRefreshToken())
		                                            		           .build();
		                                           } ).orElseThrow(null);
		
	}

	

}
