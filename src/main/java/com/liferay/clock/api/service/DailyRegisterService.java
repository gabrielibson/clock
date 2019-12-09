package com.liferay.clock.api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.liferay.clock.api.model.DailyRegister;
import com.liferay.clock.api.repository.DailyRegisterRepository;

@Component
public class DailyRegisterService {

//	@Autowired
	TimeSheetService timeSheetService;

	@Autowired
	DailyRegisterRepository dailyRepository;

	public DailyRegister save(LocalDateTime newRegister) {
		return null;
	}

	/**
	 * Service method to retrieve point registers (punches) given a date
	 * 
	 * @param date LocalDate
	 * @return a Set of LocalDateTime representing the given date punches
	 */
	public DailyRegister findByDate(LocalDate date) {
		return this.dailyRepository.findByDateOrderByDateDesc(date);
	}
	
	public Set<DailyRegister> findByMonth(YearMonth yearMonth){
		return this.dailyRepository.findByMonth(yearMonth.getYear(), yearMonth.getMonthValue());
	}
}
