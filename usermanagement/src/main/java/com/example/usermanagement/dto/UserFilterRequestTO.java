package com.example.usermanagement.dto;

import lombok.Data;

@Data
public class UserFilterRequestTO {

	private FilterRequestTO filterRequest;
	private PageDetails pageDetails;

}
