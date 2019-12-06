package com.liferay.clock.api.util;

import java.time.YearMonth;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.DateTypeDescriptor;

public class YearMonthDateType extends AbstractSingleColumnStandardBasicType<YearMonth> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final YearMonthDateType INSTANCE = new YearMonthDateType();

	public YearMonthDateType() {
		super(DateTypeDescriptor.INSTANCE, YearMonthTypeDescriptor.INSTANCE);
	}

	public String getName() {
		return "yearmonth-date";
	}

	@Override
	protected boolean registerUnderJavaType() {
		return true;
	}
}
