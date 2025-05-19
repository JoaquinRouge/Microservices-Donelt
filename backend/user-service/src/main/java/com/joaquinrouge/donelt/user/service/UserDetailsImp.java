package com.joaquinrouge.donelt.user.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.joaquinrouge.donelt.user.model.UserModel;
import com.joaquinrouge.donelt.user.repository.IUserRepository;

@Service
public class UserDetailsImp implements UserDetailsService{

	private final IUserRepository userRepo;
	private PasswordEncoder passwordEncoder;
	
	public UserDetailsImp(IUserRepository userRepo,PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		this.userRepo = userRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserModel user = userRepo.findByUsername(username).orElseThrow(
				()-> new UsernameNotFoundException("user not found"));;
		
				
				
		return new User(user.getUsername(),
					user.getPassword(),user.isEnabled(),user.isAccountNonExpired(),
					user.isCredentialsNonExpired(),user.isAccountNonLocked(),new ArrayList<>());
	}

}
