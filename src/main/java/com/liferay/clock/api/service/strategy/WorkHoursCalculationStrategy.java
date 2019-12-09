package com.liferay.clock.api.service.strategy;

import java.time.Duration;
import java.time.LocalDateTime;

@FunctionalInterface
public interface WorkHoursCalculationStrategy {

	Duration calculateWorkHours(LocalDateTime time1, LocalDateTime time2);
	
}
