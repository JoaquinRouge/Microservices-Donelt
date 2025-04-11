package com.joaquinrouge.ecommerce.user.service;

import java.util.List;

import com.joaquinrouge.ecommerce.user.dto.CreateUserDto;
import com.joaquinrouge.ecommerce.user.dto.UserDto;
import com.joaquinrouge.ecommerce.user.model.User;

public interface IUserService {
	List<User> getAllUsers();
	User getUserById(Long id);
	User getUserByEmail(String email);
	User createUser(CreateUserDto user);
	void deleteUser(Long id);
	User updateUser(User user);
	UserDto login(String email,String password);
}
