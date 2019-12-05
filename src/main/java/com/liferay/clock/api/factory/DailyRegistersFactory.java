package com.liferay.clock.api.factory;

import com.liferay.clock.api.model.DailyRegister;

public class DailyRegistersFactory {

	private static DailyRegistersFactory dailyRegistersFactory;
	
	private DailyRegistersFactory() {}
	
	public static DailyRegistersFactory getInstance() {
		if(dailyRegistersFactory == null) {
			return new DailyRegistersFactory();
		}
		return dailyRegistersFactory;
	}
	
	public static DailyRegister createDailyRegister() {
		return null;
	}
}
