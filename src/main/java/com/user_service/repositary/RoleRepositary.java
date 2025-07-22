package com.user_service.repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user_service.entities.Role;

@Repository
public interface RoleRepositary  extends JpaRepository<Role, Integer>{

}
