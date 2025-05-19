package com.joaquinrouge.donelt.user.service;

import java.util.List;

import com.joaquinrouge.donelt.user.dto.CreateUserDto;
import com.joaquinrouge.donelt.user.dto.UserDto;
import com.joaquinrouge.donelt.user.model.UserModel;

public interface IUserService {
	List<UserModel> getAllUsers();
	UserModel getUserById(Long id);
	UserModel getUserByEmail(String email);
	UserModel createUser(CreateUserDto user);
	void deleteUser(Long id);
	UserModel updateUser(UserModel user);
}
