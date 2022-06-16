package com.example.usermanagement.controller.advice;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.usermanagement.dto.ErrorResponse;
import com.example.usermanagement.exception.DataNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = DataNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleInvalidData(DataNotFoundException e) {
		ErrorResponse response = ErrorResponse.builder().timeStamp(new Timestamp(new Date().getTime()))
				.httpStatusCode(String.valueOf(HttpStatus.NOT_FOUND.value())).status(HttpStatus.NOT_FOUND.name())
				.message(e.getMessage()).build();
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

	}
}
