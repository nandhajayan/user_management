package com.example.usermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataNotFoundException(String message) {
		super(message);
	}

	public DataNotFoundException(String message, Throwable e) {
		super(message, e);
	}

}
