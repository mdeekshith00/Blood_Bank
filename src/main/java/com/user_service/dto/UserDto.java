package com.user_service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.user_service.vo.AddressVo;
import com.user_service.vo.FullNameVo;
import com.user_service.vo.RoleVo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto extends BaseDto {
	
    private FullNameVo fullname;
	
	private String username;
		
	private String phoneNumber;
	
    private Boolean isPhoneNumberVerified;

    private String bloodGroup;
    
	private String gender;

	private String eMail;

	private AddressVo address ;

	private Boolean isAvailableToDonate;
	
	private LocalDate dateOfBirth;
	
	private Boolean isActive;

	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	private LocalDateTime lastDonationDate;
		
	private String bio;
	
	private Set<RoleVo> roles;


}
