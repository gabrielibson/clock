package com.liferay.clock.api.controller;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liferay.clock.api.controller.exceptions.RepeatedPunchesException;
import com.liferay.clock.api.model.DailyRegister;
import com.liferay.clock.api.model.TimeSheet;
import com.liferay.clock.api.service.DailyRegisterService;
import com.liferay.clock.api.service.TimeSheetService;

import io.swagger.annotations.ApiOperation;

@RestController
public class TimeSheetController {

	@Autowired
	private TimeSheetService timeSheetService;

	@Autowired
	private DailyRegisterService dailyRegisterService;

	@ApiOperation(value = "Endpoint to add new registers to a Time Sheet")
	@PostMapping("/time-sheet/registers")
	public ResponseEntity<TimeSheet> newRegister(@RequestBody @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime newRegister) {
		TimeSheet timeSheet = this.timeSheetService.save(newRegister);
		if(timeSheet == null) {
			throw new RepeatedPunchesException(RepeatedPunchesException.MESSAGE);
		}
		return new ResponseEntity<TimeSheet>(timeSheet, HttpStatus.OK);
	}

	@ApiOperation(value = "Endpoint to get registers of a specific month")
	@GetMapping("/time-sheet/registers")
	public Set<DailyRegister> getRegistersByMonth(@RequestParam Year year, @RequestParam Month month) {
		Set<DailyRegister> registers = this.dailyRegisterService
				.findByMonth(YearMonth.of(year.getValue(), month.getValue()));

		return registers;
	}
	
	@ApiOperation(value = "Endpoint to get work hours of a specific month")
	@GetMapping("/time-sheet/work-hours")
	public String getWorkHoursByMonth(@RequestParam Year year, @RequestParam Month month) {
		return this.dailyRegisterService.calculateWorkHoursByMonth(YearMonth.of(year.getValue(), month.getValue()));
	}
}
