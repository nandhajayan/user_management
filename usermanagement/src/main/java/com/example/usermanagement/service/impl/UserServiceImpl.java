package com.example.usermanagement.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.usermanagement.dto.FilterRequestTO;
import com.example.usermanagement.dto.UserFilterRequestTO;
import com.example.usermanagement.dto.UserRequestTO;
import com.example.usermanagement.dto.UserResponseTO;
import com.example.usermanagement.exception.DataNotFoundException;
import com.example.usermanagement.model.Property;
import com.example.usermanagement.model.User;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public User createUser(UserRequestTO user) throws Exception {
		// if user already existed don't create new
		if (Objects.nonNull(user.getId())) {
			Optional<User> existingUser = userRepository.findById(user.getId());
			if (existingUser.isPresent()) {
				throw new Exception("User already exists");
			}
		}
		User newUser = User.builder().firstName(user.getFirstName()).lastName(user.getLastName()).email(user.getEmail())
				.properties(setProperties(user.getAttributes())).build();
		return userRepository.save(newUser);
	}

	private List<Property> setProperties(Map<String, String> map) {
		List<Property> properties = new ArrayList<>();
		if (!map.isEmpty()) {
			map.entrySet().stream().forEach(entry -> {
				properties.add(Property.builder().propertyKey(entry.getKey()).propertyValue(entry.getValue()).build());
			});
		}
		return properties;
	}

	@Override
	public User getUserById(long id) {
		Optional<User> user = userRepository.findById(id);
		return user.isPresent() ? user.get() : null;
	}

	@Override
	public void deleteUserById(long id) throws DataNotFoundException {
		// check weather the delete request is from already existing user or not
		Optional<User> existingUser = userRepository.findById(id);
		if (!existingUser.isPresent()) {
			throw new DataNotFoundException("No Such User found to delete.");
		}
		userRepository.deleteById(id);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateUser(UserRequestTO user) throws Exception {
		Optional<User> existingUser = userRepository.findById(user.getId());
		if (!existingUser.isPresent()) {
			throw new Exception("No Such User found to update.");
		}
		User dbUser = User.builder()
				.firstName(StringUtils.isEmpty(user.getFirstName()) ? existingUser.get().getFirstName()
						: user.getFirstName())
				.lastName(
						StringUtils.isEmpty(user.getLastName()) ? existingUser.get().getLastName() : user.getLastName())
				.email(StringUtils.isEmpty(user.getEmail()) ? existingUser.get().getEmail() : user.getEmail())
				.properties(setProperties(user.getAttributes())).build();
		userRepository.save(dbUser);
	}

	@Override
	public List<User> insertAll(List<UserRequestTO> users) {
		List<User> userList = new ArrayList<User>();
		if (!CollectionUtils.isEmpty(users)) {
			users.stream().forEach(user -> {
				User newUser = User.builder().firstName(user.getFirstName()).lastName(user.getLastName())
						.email(user.getEmail()).properties(setProperties(user.getAttributes())).build();
				userList.add(newUser);
			});
		}
		return userRepository.saveAll(userList);
	}

	@Override
	public Page<User> findUserByFilter(int pageNo, int pageSize, String sortField, String sortDirection) {
		return null;
	}

	@Override
	public UserResponseTO findUserByFilter(UserFilterRequestTO users) {
		Integer pageSize = 10;
		Integer pageNo = 0;
		FilterRequestTO filterRequestTO = users.getFilterRequest();
		if (null != users.getPageDetails()) {
			pageSize = users.getPageDetails().getPageSize();
			pageNo = users.getPageDetails().getPageNum();
		}
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<User> page = userRepository.findFilteredUsers(filterRequestTO.getFirstName(),
				filterRequestTO.getLastName(), filterRequestTO.getEmail(),
				//filterRequestTO.getPropertyKey(), 
				pageable);
		List<User> userList = CollectionUtils.isEmpty(page.getContent()) ? new ArrayList<User>() : page.getContent();
		return new UserResponseTO(userList,users.getPageDetails(),page.getTotalElements());
	}

}
