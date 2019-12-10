package com.liferay.clock.api.service.factory;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import com.liferay.clock.api.service.strategy.NightWorkHours;
import com.liferay.clock.api.service.strategy.RegularWorkHours;
import com.liferay.clock.api.service.strategy.SaturdaySundayWorkHours;
import com.liferay.clock.api.service.strategy.SaturdayWorkHours;
import com.liferay.clock.api.service.strategy.SundayWorkHours;
import com.liferay.clock.api.service.strategy.WorkHoursCalculationStrategy;

public class WorkHoursCalculationStrategyFactory {
	private static WorkHoursCalculationStrategyFactory workHoursCalculationStrategyFactory;
	
	private final WorkHoursCalculationStrategy regularWorkHours = new RegularWorkHours();
	private final WorkHoursCalculationStrategy saturdayWorkHours = new SaturdayWorkHours();
	private final WorkHoursCalculationStrategy sundayWorkHours = new SundayWorkHours();
	private final WorkHoursCalculationStrategy saturdaySundayWorkHours = new SaturdaySundayWorkHours();
	private final WorkHoursCalculationStrategy nightWorkHours = new NightWorkHours();
	
	private WorkHoursCalculationStrategyFactory() {}
	
	public static WorkHoursCalculationStrategyFactory getInstance() {
		if(workHoursCalculationStrategyFactory == null) {
			return new WorkHoursCalculationStrategyFactory();
		}
		return workHoursCalculationStrategyFactory;
	}
	
	public WorkHoursCalculationStrategy getWorkHoursCalculationStrategy(LocalDateTime time1, LocalDateTime time2) {
		//verify if check-in and checkout were made in different days
		if (!time1.toLocalDate().equals(time2.toLocalDate())) {
			//verify if the different days are Saturday and Sunday
			if (DayOfWeek.SATURDAY.equals(DayOfWeek.from(time1)) && DayOfWeek.SUNDAY.equals(DayOfWeek.from(time2))) {
				return saturdaySundayWorkHours;
			}
			//if it's a week day, give a NightWorkHours strategy instance
			else {
				return nightWorkHours;
			}
		} 
		//if it's Saturday work hours strategy
		else if (DayOfWeek.SATURDAY.equals(DayOfWeek.from(time1))) {
			return saturdayWorkHours;
		}
		//if it's Sunday work hours strategy
		else if (DayOfWeek.SUNDAY.equals(DayOfWeek.from(time2))) {
			return sundayWorkHours;
		}
		//if checkout after 22:00 or check-in before 6:00, it's night work hours strategy 
		else if (NightWorkHours.startExtraTime.isBefore(time2.toLocalTime()) 
				|| NightWorkHours.endExtraTime.isAfter(time1.toLocalTime())) {
			return nightWorkHours;
		} 
		//if it doesn't match the special cases, it's regular work hours strategy
		else {
			return regularWorkHours;
		}
	}
}
