package com.liferay.clock.api.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.liferay.clock.api.model.TimeSheet;
import com.liferay.clock.api.model.DailyRegister;
import com.liferay.clock.api.model.Punch;
import com.liferay.clock.api.model.User;
import com.liferay.clock.api.repository.UserRepository;
import com.liferay.clock.api.service.DailyRegisterService;

@RestController
public class DailyRegisterController {
	
	@Autowired
	UserRepository userRepository;
	
	User user = userRepository.findByPis(TimeSheet.userLogged.getPis());
	
	@Autowired
	DailyRegisterService dailyService;

	DailyRegister dailyRegister = this.dailyService.findByDate(LocalDate.now());
	
	@GetMapping("/daily-registers/{pis}")
	public List<DailyRegister> getDailyRegisters(@PathVariable String pis){
		return null;
	}
	
	@GetMapping("/daily-registers/{pis}/{date}")
	public List<Punch> getPunchesByDate(@PathVariable String pis, @PathVariable String date){
		return null;
	}
	
	@PostMapping("/daily-registers/punches")
	public DailyRegister newPunch(@RequestBody LocalDateTime newPunch) {
		LocalDate date = newPunch.toLocalDate();
		this.dailyRegister = this.dailyService.findByDate(date);
		if(this.dailyRegister == null) {
			this.dailyRegister = new DailyRegister(date);
		}
		return dailyService.save(dailyRegister);
	}
}
