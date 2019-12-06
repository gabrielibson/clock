package com.liferay.clock.api.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClockController {

	LocalDate currentDate = LocalDate.now();
	
	/*@Autowired
	UserRepository userRepository;*/
	
//	User user = userRepository.findByPis(TimeSheet.userLogged.getPis());
	
	public void registerPoint(LocalDateTime punch) {
		if(this.currentDate != punch.toLocalDate()) {
			
		}
		
		
	}
}
