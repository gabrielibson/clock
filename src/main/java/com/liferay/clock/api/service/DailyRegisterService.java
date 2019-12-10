package com.liferay.clock.api.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.liferay.clock.api.model.DailyRegister;
import com.liferay.clock.api.model.WorkHours;
import com.liferay.clock.api.repository.DailyRegisterRepository;
import com.liferay.clock.api.service.factory.WorkHoursCalculationStrategyFactory;
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
	
	WorkHoursCalculationStrategyFactory workHoursCalculationStrategyFactory = WorkHoursCalculationStrategyFactory.getInstance();

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
	public WorkHours calculateWorkHoursByDate(LocalDate date) {
		Duration total = Duration.ZERO;
		WorkHours workHours = null;
		DailyRegister dailyRegister = this.findByDate(date);
		if (dailyRegister!= null && dailyRegister.getPunches().size() >= 2) {
			Object[] punchesPairs = dailyRegister.getPunches().stream().sorted().toArray();
			for (int i = 0; i + 1 < punchesPairs.length; i = i + 2) {
				workHours = this.calculateWorkHours((LocalDateTime) punchesPairs[i], (LocalDateTime) punchesPairs[i + 1]);
				total = total.plus(workHours.getWorkHours());
			}
			workHours.setWorkHours(total);
			dailyRegister.setWorkHours(workHours);
			this.dailyRepository.save(dailyRegister);
		}
		return dailyRegister.getWorkHours();
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
			total = total.plus(this.calculateWorkHoursByDate(register.getDate()).getWorkHours());
		}
		return total.toString().replace("PT", "");
	}

	/**
	 * Auxiliary method to decides what kind of {@link WorkHoursCalculationStrategy} will be implemented 
	 * @param time1 check-in {@link LocalDateTime}
	 * @param time2 check-out {@link LocalDateTime}
	 * @return amount of worked hours {@link Duration}
	 */
	private WorkHours calculateWorkHours(LocalDateTime time1, LocalDateTime time2) {
		WorkHoursCalculationStrategy strategy = workHoursCalculationStrategyFactory.getWorkHoursCalculationStrategy(time1, time2);
		WorkHours workHours = WorkHours.verifyRestRules(strategy.calculateWorkHours(time1, time2));		
		return workHours;
	}
	
	public void deleteAllRegisters() {
		this.dailyRepository.deleteAll();
	}
	
	public void deleteRegistersByDate(LocalDate date) {
		this.dailyRepository.delete(this.findByDate(date));
	}
}
