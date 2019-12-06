package com.liferay.clock.api.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.liferay.clock.api.model.DailyRegister;


public interface DailyRegisterRepository extends JpaRepository<DailyRegister, String>{

	public DailyRegister findByDate(LocalDate date);
}
