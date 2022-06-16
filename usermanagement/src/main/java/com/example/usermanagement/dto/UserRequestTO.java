package com.example.usermanagement.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequestTO {
	
	private long id;

	private String firstName;

	private String lastName;

	private String email;

	private Map<String, String> attributes;
}
