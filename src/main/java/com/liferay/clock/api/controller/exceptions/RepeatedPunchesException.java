package com.liferay.clock.api.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class RepeatedPunchesException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String MESSAGE = "Punches at the same minute are not permitted.";

	public RepeatedPunchesException(String message) {
		super(message);
	}
	
}
