package com.example.usermanagement.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.usermanagement.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("From User u WHERE ( (:firstName is NULL OR u.firstName LIKE %:firstName%) AND "
			+ "(:lastName IS NULL OR u.lastName LIKE %:lastName%) AND (:email is NULL OR u.email LIKE %:email%) )")
		//	+ " AND (COALESCE(:propertyKey,NULL) IS NULL OR u.properties.propertyKey IN :propertyKey) )")
	Page<User> findFilteredUsers(@Param("firstName") String firstName, @Param("lastName") String lastName,
			@Param("email") String email,
			//@Param("propertyKey") List<String> propertyKey,
			Pageable pageable);

}
