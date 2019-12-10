package com.liferay.clock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.liferay.clock.api.controller.exceptions.RepeatedPunchesException;
import com.liferay.clock.api.model.TimeSheet;
import com.liferay.clock.api.model.WorkHours;
import com.liferay.clock.api.service.DailyRegisterService;
import com.liferay.clock.api.service.TimeSheetService;

@SpringBootTest
class ClockApplicationTests {

	@Autowired
	TimeSheetService timeSheetService;

	@Autowired
	DailyRegisterService dailyRegisterService;

	@Test
	void contextLoads() {
	}

	void punchesByDate() {
		Set<LocalDateTime> punches = this.dailyRegisterService.findByDate(LocalDate.of(2019, Month.APRIL, 1))
				.getPunches();
		punches.stream().forEach(System.out::println);
		assertNotNull(punches);
	}

//	@Test
	void addNightWorkRegisters() {
		TimeSheet timeSheet;

		LocalDateTime newRegister1 = LocalDateTime.of(2019, Month.APRIL, 3, 21, 0);
		timeSheet = this.timeSheetService.save(newRegister1);
		assertNotNull(timeSheet);

		LocalDateTime newRegister2 = LocalDateTime.of(2019, Month.APRIL, 4, 1, 0);
		timeSheet = this.timeSheetService.save(newRegister2);
		assertNotNull(timeSheet);

		LocalDateTime newRegister3 = LocalDateTime.of(2019, Month.APRIL, 4, 2, 0);
		timeSheet = this.timeSheetService.save(newRegister3);
		assertNotNull(timeSheet);

		LocalDateTime newRegister4 = LocalDateTime.of(2019, Month.APRIL, 4, 6, 0);
		timeSheet = this.timeSheetService.save(newRegister4);
		assertNotNull(timeSheet);
	}

	//@Test
	void addNewRegister() {
		TimeSheet timeSheet;

		LocalDateTime newRegister1 = LocalDateTime.of(2019, Month.APRIL, 1, 8, 0);
		timeSheet = this.timeSheetService.save(newRegister1);
		assertNotNull(timeSheet);

		LocalDateTime newRegister2 = LocalDateTime.of(2019, Month.APRIL, 1, 12, 0);
		timeSheet = this.timeSheetService.save(newRegister2);
		assertNotNull(timeSheet);

		LocalDateTime newRegister3 = LocalDateTime.of(2019, Month.APRIL, 2, 8, 0);
		timeSheet = this.timeSheetService.save(newRegister3);
		assertNotNull(timeSheet);

		LocalDateTime newRegister4 = LocalDateTime.of(2019, Month.APRIL, 2, 12, 0);
		timeSheet = this.timeSheetService.save(newRegister4);
		assertNotNull(timeSheet);

		this.punchesByDate();
	}

//	@Test
	void registersBrokingRestRules() {
		LocalDate date = LocalDate.now();
		this.createBrokenRestRulesRegisters(date);
		WorkHours workHours = this.dailyRegisterService.calculateWorkHoursByDate(date);
		this.dailyRegisterService.deleteRegistersByDate(date);
		assertNotEquals("", workHours.getMessage());
	}
	
	@Test
	void duplicatedPunches() {
		LocalDateTime register1 = LocalDateTime.of(2019, Month.APRIL, 1, 1, 0);
		LocalDateTime register2 = LocalDateTime.of(2019, Month.APRIL, 1, 1, 0);
		this.timeSheetService.save(register1);
		assertThrows(RepeatedPunchesException.class, ()-> this.timeSheetService.save(register2));
	}
	
	/**
	 * Calculate regular work hours
	 */
//	@Test
	void workHoursRegular() {
		LocalDate date = LocalDate.of(2019, Month.DECEMBER, 2);
		this.create4DailyRegistersByDate(date);
		Duration duration = this.dailyRegisterService.calculateWorkHoursByDate(date).getWorkHours();
		this.dailyRegisterService.deleteRegistersByDate(date);
		assertEquals(480, duration.toMinutes());
	}
	
	/**
	 * Calculate Saturday work hours
	 */
//	@Test
	void workHoursSaturday() {
		LocalDate date = LocalDate.of(2019, Month.NOVEMBER, 30);
		this.create4DailyRegistersByDate(date);
		Duration duration = this.dailyRegisterService.calculateWorkHoursByDate(date).getWorkHours();
		this.dailyRegisterService.deleteRegistersByDate(date);
		assertEquals(720, duration.toMinutes());
	}
	
	/**
	 * Calculate Sunday work hours
	 */
//	@Test
	void workHoursSunday() {
		LocalDate date = LocalDate.of(2019, Month.DECEMBER, 8);
		this.create4DailyRegistersByDate(date);
		Duration duration = this.dailyRegisterService.calculateWorkHoursByDate(date).getWorkHours();
		this.dailyRegisterService.deleteRegistersByDate(date);
		assertEquals(960, duration.toMinutes());
	}

//	@Test
	void weekWorkHoursAtNight() {
		LocalDate date = LocalDate.of(2019, Month.DECEMBER, 9);
		createWeekNightWorkHours(date);
		Duration duration = this.dailyRegisterService.calculateWorkHoursByDate(date).getWorkHours();
		this.dailyRegisterService.deleteRegistersByDate(date);
		this.dailyRegisterService.deleteRegistersByDate(date.plusDays(1));
		assertEquals(276, duration.toMinutes());
	}
	
//	@Test
	void saturdaySundayWorkHours() {
		LocalDate date = LocalDate.of(2019, Month.DECEMBER, 7);
		createSaturdaySundayWorkHours(date);
		Duration duration = this.dailyRegisterService.calculateWorkHoursByDate(date).getWorkHours();
		this.dailyRegisterService.deleteRegistersByDate(date);
		this.dailyRegisterService.deleteRegistersByDate(date.plusDays(1));
		assertEquals(390, duration.toMinutes());
	}

	void create4DailyRegistersByDate(LocalDate date) {
		TimeSheet timeSheet;
		LocalDateTime newRegister1 = LocalDateTime.of(date, LocalTime.of(8, 0));
		timeSheet = this.timeSheetService.save(newRegister1);
		assertNotNull(timeSheet);
		
		LocalDateTime newRegister2 = LocalDateTime.of(date, LocalTime.of(12, 0));
		timeSheet = this.timeSheetService.save(newRegister2);
		assertNotNull(timeSheet);
		
		LocalDateTime newRegister3 = LocalDateTime.of(date, LocalTime.of(13, 0));
		timeSheet = this.timeSheetService.save(newRegister3);
		assertNotNull(timeSheet);
		
		LocalDateTime newRegister4 = LocalDateTime.of(date, LocalTime.of(17, 0));
		timeSheet = this.timeSheetService.save(newRegister4);
		assertNotNull(timeSheet);
	}
	
	void createWeekNightWorkHours(LocalDate date) {
		TimeSheet timeSheet;
		LocalDateTime newRegister1 = LocalDateTime.of(date, LocalTime.of(21, 0));
		timeSheet = this.timeSheetService.save(newRegister1);
		assertNotNull(timeSheet);
		
		LocalDateTime newRegister2 = LocalDateTime.of(date.plusDays(1), LocalTime.of(1, 0));
		timeSheet = this.timeSheetService.save(newRegister2);
		assertNotNull(timeSheet);
		
		LocalDateTime newRegister3 = LocalDateTime.of(date.plusDays(1), LocalTime.of(2, 0));
		timeSheet = this.timeSheetService.save(newRegister3);
		assertNotNull(timeSheet);
		
		LocalDateTime newRegister4 = LocalDateTime.of(date.plusDays(1), LocalTime.of(6, 0));
		timeSheet = this.timeSheetService.save(newRegister4);
		assertNotNull(timeSheet);
	}
	
	void createSaturdaySundayWorkHours(LocalDate date) {
		TimeSheet timeSheet;
		LocalDateTime newRegister1 = LocalDateTime.of(date, LocalTime.of(21, 0));
		timeSheet = this.timeSheetService.save(newRegister1);
		assertNotNull(timeSheet);
		
		LocalDateTime newRegister2 = LocalDateTime.of(date.plusDays(1), LocalTime.of(1, 0));
		timeSheet = this.timeSheetService.save(newRegister2);
		assertNotNull(timeSheet);
		
		LocalDateTime newRegister3 = LocalDateTime.of(date.plusDays(1), LocalTime.of(2, 0));
		timeSheet = this.timeSheetService.save(newRegister3);
		assertNotNull(timeSheet);
		
		LocalDateTime newRegister4 = LocalDateTime.of(date.plusDays(1), LocalTime.of(6, 0));
		timeSheet = this.timeSheetService.save(newRegister4);
		assertNotNull(timeSheet);
	}
	
	void createBrokenRestRulesRegisters(LocalDate date) {
		TimeSheet timeSheet;
		LocalDateTime newRegister1 = LocalDateTime.of(date, LocalTime.of(8, 0));
		timeSheet = this.timeSheetService.save(newRegister1);
		assertNotNull(timeSheet);
		
		LocalDateTime newRegister2 = LocalDateTime.of(date, LocalTime.of(13, 0));
		timeSheet = this.timeSheetService.save(newRegister2);
		assertNotNull(timeSheet);
		
		LocalDateTime newRegister3 = LocalDateTime.of(date, LocalTime.of(14, 0));
		timeSheet = this.timeSheetService.save(newRegister3);
		assertNotNull(timeSheet);
		
		LocalDateTime newRegister4 = LocalDateTime.of(date, LocalTime.of(21, 0));
		timeSheet = this.timeSheetService.save(newRegister4);
		assertNotNull(timeSheet);
		
	}
}
