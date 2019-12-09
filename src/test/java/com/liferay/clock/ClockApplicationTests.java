package com.liferay.clock;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.liferay.clock.api.model.TimeSheet;
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

	@Test
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

}
