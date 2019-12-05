package com.liferay.clock.api.service;

import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.liferay.clock.api.model.TimeSheet;
import com.liferay.clock.api.repository.TimeSheetRepository;

@Component
public class TimeSheetService {

	@Autowired
	TimeSheetRepository timeSheetRepository;
	
	public TimeSheet findTimeSheetByYearMonth(YearMonth yearMonth) throws Exception {
		return this.timeSheetRepository.findById(yearMonth).orElse(null);
	}
}
