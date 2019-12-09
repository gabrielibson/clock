package com.liferay.clock.api.service.strategy;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Sunday work hours strategy class.
 * Implements {@link WorkHoursCalculationStrategy} interface
 * @author Gabriel
 *
 */
public class SundayWorkHours implements WorkHoursCalculationStrategy {
	static double MULTIPLICATIVE_FACTOR = 2;

	/**
	 * Sunday work hours calculation strategy.
	 * @param time1 LocalDateTime check-in time
	 * @param time2 LocalDateTime check-out time
	 * @return Sunday work hours {@link Duration}
	 */
	@Override
	public Duration calculateWorkHours(LocalDateTime time1, LocalDateTime time2) {
		Double time = Duration.between(time1, time2).toMinutes() * MULTIPLICATIVE_FACTOR;
		return Duration.ofMinutes(time.longValue());
	}

}
