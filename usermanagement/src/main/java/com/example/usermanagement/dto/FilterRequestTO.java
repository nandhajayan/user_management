package com.example.usermanagement.dto;

import java.util.List;

import lombok.Data;

@Data
public class FilterRequestTO {

	private long userId;
	private String firstName;
	private String lastName;
	private String email;
	private List<String> propertyKey;
}
