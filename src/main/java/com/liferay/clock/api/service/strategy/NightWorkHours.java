package com.liferay.clock.api.service.strategy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class NightWorkHours implements WorkHoursCalculationStrategy {
	WorkHoursCalculationStrategy strategy;
	private static double MULTIPLICATIVE_FACTOR = 1.2;

	public static LocalTime endExtraTime = LocalTime.of(06, 00);
	public static LocalTime startExtraTime = LocalTime.of(22, 00);

	@Override
	public Duration calculateWorkHours(LocalDateTime time1, LocalDateTime time2) {
		Duration total = Duration.ZERO;
		if (time1.toLocalTime().isBefore(startExtraTime)) {
			this.strategy = new RegularWorkHours();
			total = total.plus(this.strategy.calculateWorkHours(time1,
					LocalDateTime.of(time1.toLocalDate(), startExtraTime)));
			
			Double time = Duration.between(startExtraTime, time2).toMinutes() * MULTIPLICATIVE_FACTOR;
			total = total.plus(Duration.ofMinutes(time.longValue()));
		}else {
			Double time = Duration.between(time1, time2).toMinutes() * MULTIPLICATIVE_FACTOR;
			total = total.plus(Duration.ofMinutes(time.longValue()));
		}
		return total;
	}
}
