package com.liferay.clock.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liferay.clock.api.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);
	
	User findByPis(Long pis);
}
