package com.liferay.clock.api.repository;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.liferay.clock.api.model.DailyRegister;

@Repository
public interface DailyRegisterRepository extends JpaRepository<DailyRegister, String>{

	public DailyRegister findByDateOrderByDateDesc(LocalDate date);
	
	@Query("from DailyRegister dr where YEAR(dr.date)=?1 and MONTH(dr.date)=?2")
	public Set<DailyRegister> findByMonth(int year, int month);
}
