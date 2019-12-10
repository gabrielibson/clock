package com.liferay.clock.api.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.liferay.clock.api.model.DailyRegister;
import com.liferay.clock.api.model.WorkHours;
import com.liferay.clock.api.service.DailyRegisterService;

import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;

@RestController
public class DailyRegisterController {
	
	@Autowired
	DailyRegisterService dailyRegisterService;

	@ApiOperation(value = "Endpoint to get registers of a specific date")
	@GetMapping("/registers/{date}")
	public ResponseEntity<?> getRegistersByDate(@PathVariable 
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		//Set<LocalDateTime> registers = new HashSet<LocalDateTime>();
		DailyRegister register = this.dailyRegisterService.findByDate(date);
		if(register == null) {
			return new ResponseEntity<Exception>(new NotFoundException("Register not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<DailyRegister>(register, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Endpoint to get work hours of a specific date")
	@GetMapping("/registers/work-hours/{date}")
	public WorkHours getWorkHoursByDate(@PathVariable 
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		WorkHours workHours = this.dailyRegisterService.calculateWorkHoursByDate(date);
		return workHours;
	}
}
