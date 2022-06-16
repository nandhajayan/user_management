package com.example.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usermanagement.dto.UserFilterRequestTO;
import com.example.usermanagement.dto.UserRequestTO;
import com.example.usermanagement.dto.UserResponseTO;
import com.example.usermanagement.model.User;
import com.example.usermanagement.service.UserService;

@RestController
@RequestMapping("/user")
public class UserManagementController {

	@Autowired
	private UserService userService;

	@GetMapping("/all")
	public ResponseEntity<List<User>> listAllUser() {
		return new ResponseEntity<List<User>>(userService.getAllUser(), HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody UserRequestTO user) throws Exception {
		User dbUser = userService.createUser(user);
		return new ResponseEntity<>(dbUser, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<String> updateUser(@RequestBody UserRequestTO user) throws Exception {
		userService.updateUser(user);
		return new ResponseEntity<String>("User updated", HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") long id) throws Exception {
		userService.deleteUserById(id);
		return new ResponseEntity<String>("User deleted.", HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
		User user = userService.getUserById(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@PostMapping("/bulk")
	public ResponseEntity<String> addUsers(@RequestBody List<UserRequestTO> users) {
		if (!CollectionUtils.isEmpty(users)) {
			List<User> usersList = userService.insertAll(users);
			return new ResponseEntity<String>(String.format("Added %d users.", usersList.size()), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Request does not contain a body", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/_search")
	public ResponseEntity<UserResponseTO> filterUserByRequest(@RequestBody UserFilterRequestTO users) {
		UserResponseTO usersList = userService.findUserByFilter(users);
		return new ResponseEntity<UserResponseTO>(usersList, HttpStatus.OK);
	}

}
