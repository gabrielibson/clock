package com.liferay.clock.api.repository;

import java.time.YearMonth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.liferay.clock.api.model.TimeSheet;

public interface TimeSheetRepository extends JpaRepository<TimeSheet, Long>{

	@Query("from TimeSheet ts where ts.user_pis=:pis and ts.year_month := yearMonth")
	public TimeSheet findByUserPisAndYearMonth(Long pis, YearMonth yearMonth);
}
