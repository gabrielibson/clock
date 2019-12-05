package com.liferay.clock.api.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.liferay.clock.api.model.DailyRegister;
import com.liferay.clock.api.repository.DailyRegisterRepository;

@Component
public class DailyRegisterService {

	@Autowired
	DailyRegisterRepository dailyRepository;
	
	public DailyRegister save(DailyRegister newPunch) {
		return this.dailyRepository.save(newPunch);
	}
	
	public DailyRegister findByDate(LocalDate date) {
		return this.dailyRepository.findByDate(date);
	}
}
