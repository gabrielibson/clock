package com.liferay.clock.api.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.liferay.clock.api.model.DailyRegister;
import com.liferay.clock.api.model.WorkHours;
import com.liferay.clock.api.service.DailyRegisterService;

import io.swagger.annotations.ApiOperation;

@RestController
public class DailyRegisterController {
	
	@Autowired
	DailyRegisterService dailyRegisterService;

	@ApiOperation(value = "Endpoint to get registers of a specific date")
	@GetMapping("/registers/{date}")
	public Set<LocalDateTime> getRegistersByDate(@PathVariable 
			@DateTimeFormat(iso = ISO.DATE) LocalDate date) {
		Set<LocalDateTime> registers = new HashSet<LocalDateTime>();
		DailyRegister register = this.dailyRegisterService.findByDate(date);
		if(register != null) {
			registers = register.getPunches();
		}
		return registers;
	}
	
	@ApiOperation(value = "Endpoint to get work hours of a specific date")
	@GetMapping("/registers/work-hours/{date}")
	public WorkHours getWorkHoursByDate(@PathVariable 
			@DateTimeFormat(iso = ISO.DATE) LocalDate date) {
		WorkHours workHours = this.dailyRegisterService.calculateWorkHoursByDate(date);
		return workHours;
	}
}
