package com.user_service.vo;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.user_service.entities.Address;
import com.user_service.entities.FullName;
import com.user_service.enums.AddressType;
import com.user_service.enums.LogInType;
import com.user_service.enums.StatusType;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsersVo {
	
	private FullName fullname;
	
	@Size(min =2 , max = 15)
	@NotNull
	private String username;
	
	@Size(min =2 , max = 15)
	@NotNull
	private String password;
	
	@Pattern( regexp = "^[6-9]\\d{9}$", message = "Invalid phone number")
	private String phoneNumber;
	
    private Boolean isPhoneNumberVerified;
    
    @Enumerated(EnumType.STRING)
    private String gender;

    
	@Email(message = "Invalid email format")
	@NotNull
	private String eMail;
	
	@Column(nullable = false)
	@NotNull(message = "Address Type cant be null")
    @Enumerated(EnumType.STRING)
	private AddressType addressType;
	
    @Size(min =2 , max = 10)
    @Embedded
	private Address address ;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;
	
	private Boolean isActive;
	
	private Long loginCount;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp lastLogin;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	private String resetToken;
	
	private String bio;
	
	private StatusType activeStatus;
	@Enumerated
	private LogInType logInProvider;
	
	private Boolean wantToDonate; 
	
	private Set<RoleVo> roles;

}
