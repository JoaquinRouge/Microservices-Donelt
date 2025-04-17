package com.joaquinrouge.donelt.user.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.joaquinrouge.donelt.user.dto.CreateUserDto;
import com.joaquinrouge.donelt.user.dto.UserDto;
import com.joaquinrouge.donelt.user.model.User;
import com.joaquinrouge.donelt.user.repository.IUserRepository;

@Service
public class UserService implements IUserService{

	private final IUserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(IUserRepository userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public User getUserById(Long id) {
		return userRepo.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("User not found"));
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepo.findByEmail(email)
				.orElseThrow(()-> new IllegalArgumentException(
						"User with email: "+ email + " not found"));
	}
	
	@Override
	public User createUser(CreateUserDto user) {
		
		if(userRepo.existsByEmail(user.getEmail())) {
			throw new IllegalArgumentException("Email is already in use");
		}
		
		if(userRepo.existsByUsername(user.getUsername())) {
			throw new IllegalArgumentException("Username is already in use");
		}
		
		String hashedPassword = passwordEncoder.encode(user.getPassword());
		User newUser = new User(user.getEmail(),user.getUsername(),hashedPassword);

		return userRepo.save(newUser);
	}

	@Override
	public void deleteUser(Long id) {
		
		if(!userRepo.existsById(id)) {
			throw new IllegalArgumentException("User not found");
		}
		
		userRepo.deleteById(id);
		
	}

	@Override
	public User updateUser(User user) {
		
		User userFromDb = this.getUserById(user.getId());
		
		if(userRepo.existsByEmail(user.getEmail())) {
			throw new IllegalArgumentException("Email is already in use");
		}
		
		if(userRepo.existsByUsername(user.getUsername())) {
			throw new IllegalArgumentException("Username is already in use");
		}
		
		userFromDb.setEmail(user.getEmail());
		userFromDb.setUsername(user.getUsername());
		userFromDb.setPassword(passwordEncoder.encode(user.getPassword()));
		
		return userRepo.save(userFromDb);
	}

	@Override
	public UserDto login(String email, String password) {
		
		User userFromDB = this.getUserByEmail(email);
		
		if(!passwordEncoder.matches(password,userFromDB.getPassword())) {
			
			throw new RuntimeException("Invalid credentials");
			
		}
		
		return new UserDto(userFromDB.getId(),userFromDB.getEmail(),userFromDB.getUsername());
	}

}
