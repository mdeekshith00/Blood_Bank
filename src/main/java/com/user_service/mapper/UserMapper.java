package com.user_service.mapper;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.user_service.dto.RoleDto;
import com.user_service.dto.UserDto;
import com.user_service.entities.Role;
import com.user_service.entities.Users;
import com.user_service.vo.UsersVo;

@Mapper(componentModel = "spring", uses  = {RoleMapper.class})
public interface UserMapper {
	
	UserMapper Instance = Mappers.getMapper(UserMapper.class);

	 Users toEntity(UsersVo vo);         // VO → Entity
	    UserDto toDto(Users entity);     // Entity → DTO



}
