package com.liferay.clock.api.service.strategy;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * WorkHoursCalculationStrategy interface.
 * @author Gabriel
 *
 */
public interface WorkHoursCalculationStrategy {

	Duration calculateWorkHours(LocalDateTime time1, LocalDateTime time2);
	
}
