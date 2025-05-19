package com.joaquinrouge.donelt.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joaquinrouge.donelt.user.model.UserModel;

@Repository
public interface IUserRepository extends JpaRepository<UserModel, Long>{
	
	boolean existsByEmail(String email);
	boolean existsByUsername(String username);
	Optional<UserModel> findByEmail(String email);
}
