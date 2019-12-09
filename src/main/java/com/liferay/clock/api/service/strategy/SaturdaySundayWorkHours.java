package com.liferay.clock.api.service.strategy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Saturday and Sunday work hours strategy class.
 * Implements {@link WorkHoursCalculationStrategy} interface
 * @author Gabriel
 *
 */
public class SaturdaySundayWorkHours implements WorkHoursCalculationStrategy {
	double multiplicative_factor_saturday = SaturdayWorkHours.MULTIPLICATIVE_FACTOR;
	double multiplicative_factor_sunday = SundayWorkHours.MULTIPLICATIVE_FACTOR;

	LocalTime endSaturdayTime = LocalTime.of(23, 59);
	LocalTime startSundayTime = LocalTime.of(00, 00);

	/**
	 * Saturday and Sunday work hours calculation strategy.
	 * Used when a work starts on Saturday and ends on Sunday.
	 * @param time1 LocalDateTime check-in time
	 * @param time2 LocalDateTime check-out time
	 * @return Saturday and Sunday work hours {@link Duration}
	 */
	@Override
	public Duration calculateWorkHours(LocalDateTime time1, LocalDateTime time2) {
		Duration total = Duration.ZERO;
		Double timeSaturday = Duration.between(time1, endSaturdayTime).plusMinutes(1).toMinutes()
				* multiplicative_factor_saturday;
		Double timeSunday = Duration.between(startSundayTime, time2).toMinutes()
				* multiplicative_factor_sunday;
		total = Duration.ofMinutes(timeSaturday.longValue() + timeSunday.longValue());
		return total;
	}

}
