package com.liferay.clock.api.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.liferay.clock.api.model.TimeSheet;
import com.liferay.clock.api.service.TimeSheetService;

@RestController
public class TimeSheetController {

	@Autowired
	private TimeSheetService timeSheetService;
	
	@PostMapping("/time-sheet/registers")
	public TimeSheet newRegister(@RequestBody LocalDateTime newRegister) {
		return this.timeSheetService.save(newRegister);
	}
}
