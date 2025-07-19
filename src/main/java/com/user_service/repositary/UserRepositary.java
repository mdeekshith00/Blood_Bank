package com.user_service.repositary;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user_service.entities.Users;

public interface UserRepositary extends JpaRepository<Users, Integer>{

	Users findByUsername(String username);
	
	Optional<Users> findByUserIdAndIsActive(Integer userId, Boolean isActive);

	Optional<Users> findByUsernameAndPhoneNumber(String username , String phoneNumber);
	
	Page<Users> findByBloodGroup(String bloodGroup , Pageable pageable);
	
	

}
