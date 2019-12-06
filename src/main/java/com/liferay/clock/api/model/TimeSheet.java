package com.liferay.clock.api.model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.OneToOne;

@Entity
public class TimeSheet {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "year_month", columnDefinition = "date", unique = true)
	private YearMonth yearMonth;

	@OneToOne
	@JoinColumn(name = "user_pis")
	private User user;
	
	@ElementCollection
	@MapKey(name = "registers")
	private Map<LocalDate, RegisterSet> registersMap;
	
	public TimeSheet(YearMonth yearMonth, User user) {
		super();
		this.yearMonth = yearMonth;
		this.user = user;
		this.registersMap = new HashMap<LocalDate, RegisterSet>();
	}

	public YearMonth getYearMonth() {
		return yearMonth;
	}

	public Map<LocalDate, RegisterSet> getRegistersMap() {
		return registersMap;
	}

}


