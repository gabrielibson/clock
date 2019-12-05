package com.liferay.clock.api.model;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import com.liferay.clock.api.config.UserLogged;

public class TimeSheet {
	
	public static UserLogged userLogged;

	private LocalDateTime currentDateTime;
	private List<DailyRegister> dailyRegisters;
	private YearMonth yearMonth;
	
	public static UserLogged getUserLogged() {
		return userLogged;
	}
	public LocalDateTime getCurrentDateTime() {
		return currentDateTime;
	}
	public List<DailyRegister> getDailyRegisters() {
		return dailyRegisters;
	}
	public YearMonth getYearMonth() {
		return yearMonth;
	}
	
}
