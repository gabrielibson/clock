package com.liferay.clock.api.service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.liferay.clock.api.model.RegisterSet;
import com.liferay.clock.api.model.TimeSheet;
import com.liferay.clock.api.model.User;
import com.liferay.clock.api.repository.TimeSheetRepository;

@Component
public class TimeSheetService {
	
	private User userLogged = new User(123456789L, "Fulano");
	private TimeSheet timeSheet;
	private RegisterSet registersSet;

	@Autowired
	TimeSheetRepository timeSheetRepository;
	
	public TimeSheet save(LocalDateTime newRegister) {
		this.timeSheet = this.findTimeSheetByUserPisAndYearMonth(this.userLogged.getPis(), YearMonth.from(newRegister));

		if (this.timeSheet == null) {
			this.timeSheet = new TimeSheet(YearMonth.from(newRegister), this.userLogged);
		}
		
		if(this.timeSheet.getRegistersMap().get(newRegister.toLocalDate()) == null) {
			this.registersSet = new RegisterSet(new HashSet<>());
		}
		this.registersSet.getRegisters().add(newRegister.toLocalTime());
		this.timeSheet.getRegistersMap().put(newRegister.toLocalDate(), this.registersSet);
		
		return this.timeSheetRepository.save(this.timeSheet);
	}	
	
	public TimeSheet findTimeSheetByYearMonth(YearMonth yearMonth) throws Exception {
		//return this.timeSheetRepository.findById(yearMonth).orElse(null);
		return null;
	}
	
	public TimeSheet findTimeSheetByUserPisAndYearMonth(Long pis, YearMonth yearMonth) {
		return this.timeSheetRepository.findByUserPisAndYearMonth(pis, yearMonth);
	}
}
