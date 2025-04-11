package com.joaquinrouge.donelt.user.service;

import java.util.List;

import com.joaquinrouge.donelt.user.dto.CreateUserDto;
import com.joaquinrouge.donelt.user.dto.UserDto;
import com.joaquinrouge.donelt.user.model.User;

public interface IUserService {
	List<User> getAllUsers();
	User getUserById(Long id);
	User getUserByEmail(String email);
	User createUser(CreateUserDto user);
	void deleteUser(Long id);
	User updateUser(User user);
	UserDto login(String email,String password);
}
