package com.example.usermanagement.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.usermanagement.dto.UserFilterRequestTO;
import com.example.usermanagement.dto.UserRequestTO;
import com.example.usermanagement.dto.UserResponseTO;
import com.example.usermanagement.model.User;

public interface UserService {
	
	List<User> getAllUser();
	
	User createUser(UserRequestTO user) throws Exception;
	
	User getUserById(long id);
	
	void deleteUserById(long id) throws Exception;
	
	void updateUser(UserRequestTO user) throws Exception;
	
	List<User> insertAll(List<UserRequestTO> users);
	
	Page<User> findUserByFilter(int pageNo, int pageSize, String sortField, String sortDirection);

	UserResponseTO findUserByFilter(UserFilterRequestTO users);


}
