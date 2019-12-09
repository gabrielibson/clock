package com.liferay.clock.api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.liferay.clock.api.model.DailyRegister;
import com.liferay.clock.api.model.TimeSheet;
import com.liferay.clock.api.model.User;
import com.liferay.clock.api.repository.TimeSheetRepository;

/**
 * Class service responsible for intermediate TimeSheetController and TimeSheetRepository interactions
 * @author Gabriel
 *
 */
@Component
public class TimeSheetService {

	private User userLogged = new User(123456789L, "Fulano");
	private TimeSheet timeSheet;

	@Autowired
	TimeSheetRepository timeSheetRepository;

	/**
	 * Service method to add a new register in the current TimeSheet
	 * @param newRegister LocalDateTime
	 * @return a new TimeSheet with the new register or the current TimeSheet updated
	 */
	public TimeSheet save(LocalDateTime newRegister) {
		//Retrieve the TimeSheet according to newRegister date
		this.timeSheet = this.getTimeSheetByDate(newRegister.toLocalDate());
		if (this.timeSheet == null) {
			this.timeSheet = new TimeSheet(YearMonth.from(newRegister), this.userLogged);
		}
		DailyRegister currentRegister = this.timeSheet.currentDailyRegister(newRegister);
		currentRegister.registerPoint(newRegister);
		this.timeSheet.getDailyRegisters().add(currentRegister);

		return this.timeSheetRepository.save(this.timeSheet);
	}
	
	public Set<DailyRegister> findRegistersByMonth(YearMonth month){
		Set<DailyRegister> dailyRegisters = new HashSet<DailyRegister>();
		TimeSheet timeSheet = this.findTimeSheetByUserPisAndYearMonth(this.userLogged.getPis(), month);
		if(timeSheet.getDailyRegisters() != null) {
			timeSheet.getDailyRegisters().stream().forEach(entry -> dailyRegisters.add(entry));
		}
		return dailyRegisters;
	}

	/**
	 * Service method to find a TimeSheet by an User identifier (pis) and an YearMonth date
	 * @param pis Long
	 * @param yearMonth YearMonth
	 * @return a TimeSheet or null if there isn't a register that matches the parameters 
	 */
	public TimeSheet findTimeSheetByUserPisAndYearMonth(Long pis, YearMonth yearMonth) {
		return this.timeSheetRepository.findByUserPisAndYearMonth(pis, yearMonth);
	}

	/**
	 * Auxiliar method to find a TimeSheet by an User identifier (pis) and an YearMonth date.
	 * Method makes the conversion from LocalDate to YearMonth
	 * @param date LocalDate
	 * @return a TimeSheet or null if there isn't a register that matches the parameter
	 */
	private TimeSheet getTimeSheetByDate(LocalDate date) {
		return this.findTimeSheetByUserPisAndYearMonth(this.userLogged.getPis(), YearMonth.from(date));
	}
}
