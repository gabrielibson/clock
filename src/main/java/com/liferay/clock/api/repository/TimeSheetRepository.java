package com.liferay.clock.api.repository;

import java.time.YearMonth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.liferay.clock.api.model.TimeSheet;

public interface TimeSheetRepository extends JpaRepository<TimeSheet, Long>{

	@Query("from TimeSheet ts where ts.user.pis=:pis and ts.yearMonth =:year_month")
	public TimeSheet findByUserPisAndYearMonth(@Param("pis")Long pis, @Param("year_month")YearMonth yearMonth);
}
