package com.joaquinrouge.donelt.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.joaquinrouge.donelt.user.dto.CreateUserDto;
import com.joaquinrouge.donelt.user.model.UserModel;
import com.joaquinrouge.donelt.user.service.IUserService;
import com.joaquinrouge.donelt.user.utils.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private final IUserService userService;
	private final JwtUtils jwtUtils;
	
	public UserController(IUserService userService,JwtUtils jwtUtils) {
		this.userService = userService;
		this.jwtUtils = jwtUtils;
	}

	@GetMapping()
	@PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
	public ResponseEntity<Object> getAllUsers(){
		return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
	}
	
	@GetMapping("/id/{id}")
	@PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
	public ResponseEntity<Object> getUserById(@PathVariable Long id){
		try {
			UserModel user = userService.getUserById(id);
			return ResponseEntity.status(HttpStatus.OK).body(user);
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping("/create")
	@PreAuthorize("permitAll()")
	public ResponseEntity<Object> createUser(@RequestBody CreateUserDto user){
		try {
			UserModel createUser = userService.createUser(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/delete/{deleteId}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Object> deleteUser(@PathVariable("deleteId") Long deleteId,
			HttpServletRequest request){
		
		 String token = request.getHeader("Authorization").substring(7);
		 DecodedJWT jwt = jwtUtils.validateJwt(token);
		 Long userIdFromToken = jwt.getClaim("id").asLong();
		
		 if(userIdFromToken != deleteId) {
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
		 }
		 
		try {
			userService.deleteUser(deleteId);
			return ResponseEntity.status(HttpStatus.OK).build();
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping("/update")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Object> updateUser(@RequestBody UserModel user,
			HttpServletRequest request ){
		
		 String token = request.getHeader("Authorization").substring(7);
		 DecodedJWT jwt = jwtUtils.validateJwt(token);
		 Long userIdFromToken = jwt.getClaim("id").asLong();
		
		 if(userIdFromToken != user.getId()) {
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
		 }
		
		try {
			UserModel updateUser = userService.updateUser(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(updateUser);
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
}
