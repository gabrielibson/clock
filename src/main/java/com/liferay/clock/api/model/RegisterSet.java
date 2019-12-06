package com.liferay.clock.api.model;

import java.time.LocalTime;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Embeddable
@Entity
public class RegisterSet{
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ElementCollection
	@JoinColumn(name = "register_set")
	Set<LocalTime> registers;
	
	public RegisterSet(Set<LocalTime> registers) {
		super();
		this.registers = registers;
	}

	public Set<LocalTime> getRegisters() {
		return registers;
	}
}
