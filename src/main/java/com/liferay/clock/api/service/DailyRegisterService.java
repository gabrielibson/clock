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

@Component
public class DailyRegisterService {

	@Autowired
	DailyRegisterRepository dailyRepository;

	public DailyRegister save(LocalDateTime newRegister) {
		return null;
	}

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

	public String calculateWorkHoursByMonth(YearMonth yearMonth) {
		Duration total = Duration.ZERO;
		Set<DailyRegister> dailyRegisters = this.findByMonth(yearMonth);
		for(DailyRegister register : dailyRegisters) {
			total = total.plus(this.calculateWorkHoursByDate(register.getDate()));
		}
		return total.toString().replace("PT", "");
	}

	private Duration calculateWorkHours(LocalDateTime time1, LocalDateTime time2) {
		Duration total = Duration.ZERO;
		WorkHoursCalculationStrategy strategy;
		if (!time1.toLocalDate().equals(time2.toLocalDate())) {
			if (DayOfWeek.SATURDAY.equals(DayOfWeek.from(time1)) && DayOfWeek.SUNDAY.equals(DayOfWeek.from(time2))) {
				strategy = new SaturdaySundayWorkHours();
				total = total.plus(strategy.calculateWorkHours(time1, time2));
			} else {
				strategy = new NightWorkHours();
				total = total.plus(strategy.calculateWorkHours(time1, time2));
			}
		} else if (DayOfWeek.SATURDAY.equals(DayOfWeek.from(time1))) {
			strategy = new SaturdayWorkHours();
			total = total.plus(strategy.calculateWorkHours(time1, time2));
		} else if (DayOfWeek.SUNDAY.equals(DayOfWeek.from(time2))) {
			strategy = new SundayWorkHours();
			total = total.plus(strategy.calculateWorkHours(time1, time2));
		} else if (NightWorkHours.startExtraTime.isBefore(time2.toLocalTime())) {
			strategy = new NightWorkHours();
			total = total.plus(strategy.calculateWorkHours(time1, time2));
		} else {
			strategy = new RegularWorkHours();
			total = total.plus(strategy.calculateWorkHours(time1, time2));
		}
		return total;
	}
}
