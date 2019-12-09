package com.liferay.clock.api.service.strategy;

import java.time.Duration;
import java.time.LocalDateTime;

public class RegularWorkHours implements WorkHoursCalculationStrategy{

	@Override
	public Duration calculateWorkHours(LocalDateTime time1, LocalDateTime time2) {
		return Duration.between(time1, time2);
	}

}
