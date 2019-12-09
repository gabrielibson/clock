package com.liferay.clock.api.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.liferay.clock.api.util.YearMonthDateAttributeConverter;

@Entity
public class TimeSheet {

	private static final int NOT_NEEDED_INTERVAL = 4;

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "year_month", columnDefinition = "date", unique = true)
	@Convert(converter = YearMonthDateAttributeConverter.class)
	private YearMonth yearMonth;

	@OneToOne
	@JoinColumn(name = "user_pis")
	private User user;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "daily_registers_id")
	private Set<DailyRegister> dailyRegisters;

	public TimeSheet() {
	}

	public TimeSheet(YearMonth yearMonth, User user) {
		super();
		this.yearMonth = yearMonth;
		this.user = user;
		this.dailyRegisters = new HashSet<DailyRegister>();
	}

	public YearMonth getYearMonth() {
		return yearMonth;
	}

	public Set<DailyRegister> getDailyRegisters() {
		return dailyRegisters;
	}

	public User getUser() {
		return user;
	}

	/**
	 * Defines whether the new register will be registered in the same day or in the
	 * day after.
	 * The register is created according to following:
	 * If the date of the current DailyRegister is before the newRegister, it
	 * verifies whether the current DailyRegister has odd punches, in positive case,
	 * it verifies  whether the interval between the last punch and the current punch
	 * is higher than 4 hours (the maximum permitted interval of work without rest), in
	 * negative case, the register is created in the same date of current DailyRegister,
	 * otherwise it is created a new DailyRegister.
	 * @param newRegister LocalDateTime
	 * @return current DailyRegister
	 */
	public DailyRegister currentDailyRegister(LocalDateTime newRegister) {
		if (!this.dailyRegisters.isEmpty()) {
			DailyRegister dailyRegister = this.getLastRegisterIndex();
			//verify if the current date is before the newRegister date
			if (dailyRegister.getDate().isBefore(newRegister.toLocalDate())) {
				//verify if the current date has odd punches
				if (dailyRegister.hasOddRegisters()) {
					//verify if the duration between the last punch and the newRegister is less 
					//than maximum permitted without rest
					if (Duration.between(dailyRegister.getLastPunch(), newRegister)
							.compareTo(Duration.ofHours(NOT_NEEDED_INTERVAL)) > 0) {
						return new DailyRegister(newRegister.toLocalDate());
					}
					return dailyRegister;
				}
				return new DailyRegister(newRegister.toLocalDate());
			}
			return dailyRegister;
		}
		return new DailyRegister(newRegister.toLocalDate());
	}

	private DailyRegister getLastRegisterIndex() {
		Object[] registers = this.dailyRegisters.stream().toArray();
		Arrays.sort(registers);
		return (DailyRegister) registers[this.dailyRegisters.size() - 1];
	}

}
