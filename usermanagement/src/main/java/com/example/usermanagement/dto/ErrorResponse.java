package com.example.usermanagement.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

	private Date timeStamp;
	private String httpStatusCode;
	private String status;
	private String error;
	private String message;

}
