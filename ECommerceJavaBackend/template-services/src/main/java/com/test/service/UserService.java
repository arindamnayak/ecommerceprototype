package com.test.service;

import org.parse4j.ParseObject;
import org.springframework.stereotype.Service;

import com.test.persistence.entities.User;

@Service
public interface UserService {
	public ParseObject findUserById(String userId);
	public User findByUserName(String userName);

}
