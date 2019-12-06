package com.liferay.clock.api.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class DailyRegister {

/*	@Id
	@GeneratedValue*/
	private Long id;
	
//	@Column(nullable = false, unique = true, name = "date")
	private LocalDate date;
	
//	@ElementCollection
	private Set<String> punches;

	public DailyRegister(LocalDate date) {
		super();
		this.date = date;
		this.punches = new HashSet<String>();
	}

	public LocalDate getDate() {
		return date;
	}

	public Set<String> getPunches() {
		return punches;
	}
}
