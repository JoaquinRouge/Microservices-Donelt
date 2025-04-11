package com.joaquinrouge.donelt.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joaquinrouge.donelt.user.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>{
	
	boolean existsByEmail(String email);
	boolean existsByUsername(String username);
	Optional<User> findByEmail(String email);
}
