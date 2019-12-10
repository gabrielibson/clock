package com.liferay.clock.api.model;

import java.time.Duration;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class WorkHours {

	@Id
	@GeneratedValue
	@JsonIgnore
	private long id;
	@JsonFormat(pattern="HH:mm")
	private Duration workHours;
	private String message;
	
	public WorkHours() {}
	
	public WorkHours(Duration workHours, String message) {
		super();
		this.workHours = workHours;
		this.message = message;
	}
	
	public static WorkHours verifyRestRules(Duration workHours) {
		String message = "";
		if(workHours.compareTo(Duration.ofHours(4)) > 0 && workHours.compareTo(Duration.ofHours(6)) < 0 
				|| workHours.compareTo(Duration.ofHours(6)) > 0) {
			message = "It's lacking a rest interval";
		}
		return new WorkHours(workHours, message);
	}

	public long getId() {
		return id;
	}

	public void setWorkHours(Duration workHours) {
		this.workHours = workHours;
	}

	public Duration getWorkHours() {
		return workHours;
	}

	public String getMessage() {
		return message;
	}
}
