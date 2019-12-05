package com.liferay.clock.api.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

public class User {

	@Id
	private Long pis;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	private String password;
	
	private List<TimeSheet> timeSheets;
	
	public User(Long pis, String username, String password) {
		super();
		this.pis = pis;
		this.username = username;
		this.password = password;
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

	public List<TimeSheet> getTimeSheet() {
		return timeSheets;
	}
}
