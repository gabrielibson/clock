package com.liferay.clock.api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.liferay.clock.api.model.DailyRegister;
import com.liferay.clock.api.repository.DailyRegisterRepository;


public class DailyRegisterService {

	

//	@Autowired
	TimeSheetService timeSheetService;

//	@Autowired
	DailyRegisterRepository dailyRepository;

	public DailyRegister save(LocalDateTime newRegister) {
		return null;
	}

	public DailyRegister findByDate(LocalDate date) {
		return this.dailyRepository.findByDate(date);
	}
}
