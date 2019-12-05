package com.liferay.clock.api.repository;

import java.time.YearMonth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.liferay.clock.api.model.TimeSheet;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheet, YearMonth>{

	
}
