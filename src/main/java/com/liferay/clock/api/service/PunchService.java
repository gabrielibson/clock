package com.liferay.clock.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.liferay.clock.api.model.Punch;
import com.liferay.clock.api.repository.IPunchRepository;

@Component
public class PunchService {

	@Autowired
	IPunchRepository punchRepository;
	
	public Punch save(Punch newPunch) {
		return this.punchRepository.save(newPunch);
	}
}
