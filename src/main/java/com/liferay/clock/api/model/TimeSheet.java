package com.liferay.clock.api.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
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

	public TimeSheet() {}

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

	public DailyRegister currentDailyRegister(LocalDateTime newRegister) {
		if (!this.dailyRegisters.isEmpty()) {
			DailyRegister dailyRegister = this.getLastRegisterIndex();
			if (dailyRegister.getDate().isBefore(newRegister.toLocalDate())) {
				if (dailyRegister.hasOddRegisters()) {
					if (Duration.between(dailyRegister.getLastPunch(), newRegister)
							.compareTo(Duration.ofHours(4)) > 0) {
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
		return (DailyRegister) this.dailyRegisters.toArray()[this.dailyRegisters.size()-1];
	}

	/*
	 * public Map<LocalDate, RegisterSet> getRegistersMap() { return registersMap; }
	 */

}
