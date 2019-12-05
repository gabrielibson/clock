package com.liferay.clock.api.model;

import java.time.LocalTime;

public class Punch {

	private PunchType punchType;
	private LocalTime punchTime;
	
	public Punch(PunchType punchType, LocalTime punchTime) {
		super();
		this.punchType = punchType;
		this.punchTime = punchTime;
	}
	
	public PunchType getPunchType() {
		return punchType;
	}
	public LocalTime getPunchTime() {
		return punchTime;
	}
	
}
