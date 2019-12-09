package com.liferay.clock.api.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class DailyRegister {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, columnDefinition = "date", unique = true, name = "date")
	private LocalDate date;

	@ElementCollection(fetch = FetchType.EAGER)
	@JoinColumn(name = "punches_id")
	@JsonFormat(pattern="HH:mm")
	private Set<LocalDateTime> punches;

	public DailyRegister() {}

	public DailyRegister(LocalDate date) {
		super();
		this.date = date;
		this.punches = new HashSet<LocalDateTime>();
	}

	public DailyRegister registerPoint(LocalDateTime register) {
		this.punches.add(register);
		return this;
	}
	
	public boolean hasOddRegisters() {
		if(this.punches.size()%2 > 0) {
			return true;
		}
		return false;
	}
	
	@JsonIgnore
	public LocalDateTime getLastPunch() {
		List<LocalDateTime> list = new ArrayList<LocalDateTime>();
		list.addAll(this.punches);
		return list.get(0);
	}

	public LocalDate getDate() {
		return date;
	}

	public Set<LocalDateTime> getPunches() {
		return punches;
	}
}