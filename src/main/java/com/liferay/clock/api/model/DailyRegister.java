package com.liferay.clock.api.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DailyRegister {

	private LocalDate date;
	private List<Punch> punches;

	public DailyRegister(LocalDate date) {
		super();
		this.date = date;
		this.punches = new ArrayList<Punch>();
	}
	
	public LocalDate getDate() {
		return date;
	}
	public List<Punch> getPunches() {
		return punches;
	}
}
