package com.peter.loginreg.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.peter.loginreg.models.User;

public interface UserRepository extends CrudRepository <User, Long>{
	List <User> findAll();

	
//	CUSTOM QUERY - TO FIND USER BY EMAIL
	Optional<User> findByEmail(String email);

}
