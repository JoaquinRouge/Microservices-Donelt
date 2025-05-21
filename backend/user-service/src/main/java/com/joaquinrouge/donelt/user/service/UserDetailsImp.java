package com.joaquinrouge.donelt.user.service;

import java.util.ArrayList;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.joaquinrouge.donelt.user.dto.AuthLoginDto;
import com.joaquinrouge.donelt.user.dto.AuthResponseDto;
import com.joaquinrouge.donelt.user.model.UserModel;
import com.joaquinrouge.donelt.user.repository.IUserRepository;
import com.joaquinrouge.donelt.user.utils.JwtUtils;

@Service
public class UserDetailsImp implements UserDetailsService{

	private final IUserRepository userRepo;
	private PasswordEncoder passwordEncoder;
	private JwtUtils jwtUtils;
	
	public UserDetailsImp(IUserRepository userRepo,JwtUtils jwtUtils,PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.jwtUtils = jwtUtils;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserModel user = userRepo.findByUsername(username).orElseThrow(
				()-> new UsernameNotFoundException("user not found"));
		
				
				
		return new User(user.getUsername(),
					user.getPassword(),user.isEnabled(),user.isAccountNonExpired(),
					user.isCredentialsNonExpired(),user.isAccountNonLocked(),new ArrayList<>());
	}

	public AuthResponseDto login(AuthLoginDto loginData) {
		
		String username = loginData.username();
		
		String password = loginData.password();
		
		Authentication auth = authenticate(username,password);
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		String jwt = jwtUtils.generateToken(auth);
		
		Long userId = jwtUtils.validateJwt(jwt).getClaim("id").asLong();
		
		return new AuthResponseDto(username,userId,"login successful",jwt,true);
		
	}
	
	public Authentication authenticate(String username,String password) {
		UserModel user = userRepo.findByUsername(username).orElseThrow(
				()-> new UsernameNotFoundException("user not found"));
		
		if(!passwordEncoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException("Username o contraseña incorrectos");
		}
		
		return new UsernamePasswordAuthenticationToken(user,user.getPassword(),
				new ArrayList<>());
		
	}
	
}
