package com.user_service.vo;

import java.util.Set;

import com.user_service.entities.Users;
import com.user_service.enums.RoleType;

import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RoleVo {
     @Enumerated
	private RoleType role;
	
	private String description;
	
//	private Set<Users> users;
}
