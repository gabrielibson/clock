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
import com.liferay.clock.api.service.DailyRegisterService;

@RestController
public class DailyRegisterController {
	
	@Autowired
	DailyRegisterService dailyRegisterService;

	DailyRegister dailyRegister; //= this.dailyService.findByDate(LocalDate.now());
	
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
	
	/*
	 * @GetMapping("/daily-registers/{pis}") public List<DailyRegister>
	 * getDailyRegisters(@PathVariable String pis){ return null; }
	 * 
	 * @GetMapping("/daily-registers/{pis}/{date}") public Set<String>
	 * getRegistersByDate(@PathVariable String pis, @PathVariable String date){
	 * return null; }
	 * 
	 * @PostMapping("/daily-registers/registers") public DailyRegister
	 * newRegister(@RequestBody LocalDateTime newRegister) { return
	 * dailyService.save(newRegister); }
	 */
}
