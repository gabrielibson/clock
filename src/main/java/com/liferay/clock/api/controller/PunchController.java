package com.liferay.clock.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.liferay.clock.api.model.Punch;
import com.liferay.clock.api.service.PunchService;

@RestController
public class PunchController {

	@Autowired
	PunchService punchService;
	
	@PostMapping("/punches")
	public Punch newPunch(@RequestBody Punch newPunch) {
		return punchService.save(newPunch);
	}
}
