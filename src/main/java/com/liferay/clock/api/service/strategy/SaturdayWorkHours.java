package com.liferay.clock.api.service.strategy;

import java.time.Duration;
import java.time.LocalDateTime;

public class SaturdayWorkHours implements WorkHoursCalculationStrategy{
	private static double MULTIPLICATIVE_FACTOR = 1.5;
	
	@Override
	public Duration calculateWorkHours(LocalDateTime time1, LocalDateTime time2) {
		Double time = Duration.between(time1, time2).toMinutes() * MULTIPLICATIVE_FACTOR;
		return Duration.ofMinutes(time.longValue());
	}

}
