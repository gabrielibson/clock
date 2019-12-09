package com.liferay.clock.api.service.strategy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Night work hours strategy class.
 * Implements {@link WorkHoursCalculationStrategy} interface
 * @author Gabriel
 *
 */
public class NightWorkHours implements WorkHoursCalculationStrategy {
	WorkHoursCalculationStrategy strategy;
	private static double MULTIPLICATIVE_FACTOR = 1.2;

	public static LocalTime startExtraTime = LocalTime.of(22, 00);
	public static LocalTime endExtraTime = LocalTime.of(06, 00);

	private Duration total = Duration.ZERO;
	private Double time = 0.0;

	/**
	 * Night work hours calculation strategy.
	 * @param time1 LocalDateTime check-in time
	 * @param time2 LocalDateTime check-out time
	 * @return night work hours {@link Duration}
	 */
	@Override
	public Duration calculateWorkHours(LocalDateTime time1, LocalDateTime time2) {
		//verify if check-in is before the extra time beginning, in positive case,
		//calculate regular work hours and night work hours
		if (time1.toLocalTime().isBefore(startExtraTime)) {
			this.total = calculateRegularWorkHoursInterval(time1, LocalDateTime.of(time1.toLocalDate(), startExtraTime));
			this.total = this.calculateWorkHoursInterval(LocalDateTime.of(time2.toLocalDate(), startExtraTime), time2);
		} 
		//verify if check-in is before the extra time end
		else if (time1.toLocalTime().isBefore(endExtraTime)) {
			//verify if check-out also is before the extra time end, in positive case,
			//calculate night work hours from check-in to check-out
			if (time2.toLocalTime().isBefore(endExtraTime)) {
				this.total = this.calculateWorkHoursInterval(time1, time2);
			} 
			//otherwise, calculate night work hours from check-in to extra time end
			//and calculate regular work hours from extra time end to check-out
			else {
				this.total = calculateWorkHoursInterval(time1, LocalDateTime.of(time1.toLocalDate(), endExtraTime));
				this.total = this.calculateRegularWorkHoursInterval(LocalDateTime.of(time2.toLocalDate(), endExtraTime), time2);
			}
		} else {
			this.total = this.calculateWorkHoursInterval(time1, time2);
		}
		return this.total;
	}

	/**
	 * Auxiliary method to calculate night work hours interval
	 * @param time1 {@link LocalDateTime}
	 * @param time2 {@link LocalDateTime}
	 * @return interval {@link Duration}
	 */
	private Duration calculateWorkHoursInterval(LocalDateTime time1, LocalDateTime time2) {
		this.time = Duration.between(time1, time2).toMinutes() * MULTIPLICATIVE_FACTOR;
		this.total = this.total.plus(Duration.ofMinutes(this.time.longValue()));

		return this.total;
	}
	
	/**
	 * Auxiliary method to calculate regular work hours interval
	 * @param time1 {@link LocalDateTime}
	 * @param time2 {@link LocalDateTime}
	 * @return interval {@link Duration}
	 */
	private Duration calculateRegularWorkHoursInterval(LocalDateTime time1, LocalDateTime time2) {
		this.strategy = new RegularWorkHours();
		this.total = this.total.plus(this.strategy.calculateWorkHours(time1, time2));
		return total;
	}
}
