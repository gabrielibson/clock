package com.liferay.clock.api.service.strategy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SaturdaySundayWorkHours implements WorkHoursCalculationStrategy {
	private static double MULTIPLICATIVE_FACTOR_SATURDAY = 1.5;
	private static double MULTIPLICATIVE_FACTOR_SUNDAY = 2.0;

	LocalTime endSaturdayTime = LocalTime.of(23, 59);
	LocalTime startSundayTime = LocalTime.of(00, 00);

	@Override
	public Duration calculateWorkHours(LocalDateTime time1, LocalDateTime time2) {
		Duration total = Duration.ZERO;
		Double timeSaturday = Duration.between(time1, endSaturdayTime).plusMinutes(1).toMinutes()
				* MULTIPLICATIVE_FACTOR_SATURDAY;
		Double timeSunday = Duration.between(startSundayTime, time2).toMinutes()
				* MULTIPLICATIVE_FACTOR_SUNDAY;
		total = Duration.ofMinutes(timeSaturday.longValue() + timeSunday.longValue());
		return total;
	}

}
