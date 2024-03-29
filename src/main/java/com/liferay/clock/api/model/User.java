package com.liferay.clock.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="users")
public class User {

	@Id
	@Column(name = "pis")
	private Long pis;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@JsonIgnore
	private String password;
	
	public User() {}
	
	public User(Long pis, String username, String password) {
		super();
		this.pis = pis;
		this.username = username;
		this.password = password;
	}
	
	public User(Long pis, String username) {
		super();
		this.pis = pis;
		this.username = username;
	}

	public Long getPis() {
		return pis;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
