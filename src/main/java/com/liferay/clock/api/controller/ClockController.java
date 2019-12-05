package com.liferay.clock.api.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.liferay.clock.api.model.TimeSheet;
import com.liferay.clock.api.model.User;
import com.liferay.clock.api.repository.UserRepository;

@RestController
public class ClockController {

	LocalDate currentDate = LocalDate.now();
	
	@Autowired
	UserRepository userRepository;
	
	User user = userRepository.findByPis(TimeSheet.userLogged.getPis());
	
	public void registerPoint(LocalDateTime punch) {
		if(this.currentDate != punch.toLocalDate()) {
			
		}
		
		
	}
}
