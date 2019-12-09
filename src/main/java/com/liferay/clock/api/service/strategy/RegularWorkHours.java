package com.liferay.clock.api.service.strategy;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Regular work hours strategy class.
 * Implements {@link WorkHoursCalculationStrategy} interface
 * @author Gabriel
 *
 */
public class RegularWorkHours implements WorkHoursCalculationStrategy{

	/**
	 * Regular work hours calculation strategy.
	 * @param time1 LocalDateTime check-in time
	 * @param time2 LocalDateTime check-out time
	 * @return regular work hours {@link Duration}
	 */
	@Override
	public Duration calculateWorkHours(LocalDateTime time1, LocalDateTime time2) {
		return Duration.between(time1, time2);
	}

}
