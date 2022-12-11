package com.in28minutes.rest.webservices.user.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.in28minutes.rest.webservices.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {


}
