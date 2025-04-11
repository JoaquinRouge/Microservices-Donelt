package com.joaquinrouge.ecommerce.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joaquinrouge.ecommerce.user.dto.CreateUserDto;
import com.joaquinrouge.ecommerce.user.dto.LoginDto;
import com.joaquinrouge.ecommerce.user.dto.UserDto;
import com.joaquinrouge.ecommerce.user.model.User;
import com.joaquinrouge.ecommerce.user.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@GetMapping()
	public ResponseEntity<Object> getAllUsers(){
		return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable Long id){
		try {
			User user = userService.getUserById(id);
			return ResponseEntity.status(HttpStatus.OK).body(user);
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> createUser(@RequestBody CreateUserDto user){
		try {
			User createUser = userService.createUser(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody LoginDto loginDto){
		try {
			UserDto userDto = userService.login(loginDto.getEmail(),loginDto.getPassword());
			return ResponseEntity.status(HttpStatus.OK).body(userDto);
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}catch(RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/delete/{deleteId}")
	public ResponseEntity<Object> deleteUser(@PathVariable("deleteId") Long deleteId){
		try {
			userService.deleteUser(deleteId);
			return ResponseEntity.status(HttpStatus.OK).build();
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<Object> updateUser(@RequestBody User user){
		try {
			User updateUser = userService.updateUser(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(updateUser);
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
}
