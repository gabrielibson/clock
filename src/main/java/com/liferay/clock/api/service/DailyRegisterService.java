package com.liferay.clock.api.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.liferay.clock.api.model.DailyRegister;
import com.liferay.clock.api.repository.DailyRegisterRepository;
import com.liferay.clock.api.service.strategy.NightWorkHours;
import com.liferay.clock.api.service.strategy.RegularWorkHours;
import com.liferay.clock.api.service.strategy.SaturdaySundayWorkHours;
import com.liferay.clock.api.service.strategy.SaturdayWorkHours;
import com.liferay.clock.api.service.strategy.SundayWorkHours;
import com.liferay.clock.api.service.strategy.WorkHoursCalculationStrategy;

/**
 * Daily register service class. 
 * Responsible for do operations related to {@link DailyRegister} 
 * and for do interactions to {@link DailyRegisterRepository} 
 * @author Gabriel
 *
 */
@Component
public class DailyRegisterService {

	@Autowired
	DailyRegisterRepository dailyRepository;

	/**
	 * Service method to retrieve the DailyRegister given a date
	 * 
	 * @param date LocalDate
	 * @return DailyRegister representing the given date LocalDate
	 */
	public DailyRegister findByDate(LocalDate date) {
		return this.dailyRepository.findByDateOrderByDateDesc(date);
	}

	/**
	 * Service method to retrieve point registers (punches) given a date
	 * 
	 * @param yearMonth YearMonth
	 * @return a Set of LocalDateTime representing the given date punches
	 */
	public Set<DailyRegister> findByMonth(YearMonth yearMonth) {
		return this.dailyRepository.findByMonth(yearMonth.getYear(), yearMonth.getMonthValue());
	}

	/**
	 * Calculate worked hours by date.
	 * @param date {@link LocalDate}
	 * @return amount of worked hours {@link Duration}
	 */
	public Duration calculateWorkHoursByDate(LocalDate date) {
		Duration total = Duration.ZERO;
		DailyRegister dailyRegister = this.findByDate(date);
		if (dailyRegister.getPunches().size() >= 2) {
			Object[] punchesPairs = dailyRegister.getPunches().stream().sorted().toArray();
			for (int i = 0; i + 1 < punchesPairs.length; i = i + 2) {
				total = this.calculateWorkHours((LocalDateTime) punchesPairs[i], (LocalDateTime) punchesPairs[i + 1]);
			}
		}
		return total;
	}

	/**
	 * Calculate worked hours by month
	 * @param yearMonth {@link YearMonth}
	 * @return worked hours in {@link String} format
	 */
	public String calculateWorkHoursByMonth(YearMonth yearMonth) {
		Duration total = Duration.ZERO;
		Set<DailyRegister> dailyRegisters = this.findByMonth(yearMonth);
		for(DailyRegister register : dailyRegisters) {
			total = total.plus(this.calculateWorkHoursByDate(register.getDate()));
		}
		return total.toString().replace("PT", "");
	}

	/**
	 * Auxiliary method to decides what kind of {@link WorkHoursCalculationStrategy} will be implemented 
	 * @param time1 check-in {@link LocalDateTime}
	 * @param time2 check-out {@link LocalDateTime}
	 * @return amount of worked hours {@link Duration}
	 */
	private Duration calculateWorkHours(LocalDateTime time1, LocalDateTime time2) {
		Duration total = Duration.ZERO;
		WorkHoursCalculationStrategy strategy;
		//verify if check-in and checkout were made in different days
		if (!time1.toLocalDate().equals(time2.toLocalDate())) {
			//verify if the different days are Saturday and Sunday
			if (DayOfWeek.SATURDAY.equals(DayOfWeek.from(time1)) && DayOfWeek.SUNDAY.equals(DayOfWeek.from(time2))) {
				strategy = new SaturdaySundayWorkHours();
				total = total.plus(strategy.calculateWorkHours(time1, time2));
			}
			//if it's a week day, call night work hours calculator
			else {
				strategy = new NightWorkHours();
				total = total.plus(strategy.calculateWorkHours(time1, time2));
			}
		} 
		//calculate Saturday work hours
		else if (DayOfWeek.SATURDAY.equals(DayOfWeek.from(time1))) {
			strategy = new SaturdayWorkHours();
			total = total.plus(strategy.calculateWorkHours(time1, time2));
		}
		//calculate Sunday work hours
		else if (DayOfWeek.SUNDAY.equals(DayOfWeek.from(time2))) {
			strategy = new SundayWorkHours();
			total = total.plus(strategy.calculateWorkHours(time1, time2));
		}
		//if checkout after 22:00 or check-in before 6:00, calculate night work hours 
		else if (NightWorkHours.startExtraTime.isBefore(time2.toLocalTime()) 
				|| NightWorkHours.endExtraTime.isAfter(time1.toLocalTime())) {
			strategy = new NightWorkHours();
			total = total.plus(strategy.calculateWorkHours(time1, time2));
		} 
		//if it doesn't match the special cases, calculate regular work hours
		else {
			strategy = new RegularWorkHours();
			total = total.plus(strategy.calculateWorkHours(time1, time2));
		}
		return total;
	}
}
