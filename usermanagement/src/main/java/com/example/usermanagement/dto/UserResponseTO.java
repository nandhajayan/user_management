package com.example.usermanagement.dto;

import java.util.List;

import com.example.usermanagement.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseTO {
	
	  private List<User> userList;
	  private PageDetails pageDetails;
	  private Long totalRecords;

}
