package com.demo.utils;

import com.demo.entity.User;
import com.demo.model.UserDTO;


public class UserConverter extends Converter<UserDTO, User> {

	public UserConverter() {
		super(UserConverter::convertToEntity, UserConverter::convertToDto);
	}

	private static UserDTO convertToDto(User user) {		
		return new UserDTO(ProjectUtils.getValue(user.getId()), ProjectUtils.getValue(user.getUsername()), null, 
				user.getFirstName(), user.getLastName(), 
				user.getEmail(), user.getCreatedAt(), user.getCreatedBy(), 
				user.getUpdatedAt(), user.getUpdatedBy());
	}

	private static User convertToEntity(UserDTO user) {
		return new User(ProjectUtils.getValue(user.getId()), user.getUsername(), ProjectUtils.getValue(user.getPassword()), user.getFirstName(), 
				user.getLastName(), user.getEmail(), user.getCreatedAt(), user.getCreatedBy(), 
				user.getUpdatedAt(), user.getUpdatedBy());
	}
		
}	
