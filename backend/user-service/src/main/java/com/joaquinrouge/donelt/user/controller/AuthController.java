package com.joaquinrouge.donelt.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joaquinrouge.donelt.user.dto.AuthLoginDto;
import com.joaquinrouge.donelt.user.service.UserDetailsImp;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private UserDetailsImp userDetails;
	
	public AuthController(UserDetailsImp userDetails) {
		this.userDetails = userDetails;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid AuthLoginDto data){
		try {
			return new ResponseEntity<>(userDetails.login(data),HttpStatus.OK);			
		}catch(BadCredentialsException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}catch(UsernameNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}
