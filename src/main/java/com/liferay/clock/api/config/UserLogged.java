package com.liferay.clock.api.config;

public class UserLogged {

	private Long pis;
	private String name;
	
	public UserLogged(Long pis, String name) {
		super();
		this.pis = pis;
		this.name = name;
	}

	public Long getPis() {
		return pis;
	}

	public String getName() {
		return name;
	}

}
