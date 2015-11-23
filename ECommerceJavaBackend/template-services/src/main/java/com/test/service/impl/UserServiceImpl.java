package com.test.service.impl;

import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.test.persistence.entities.User;
import com.test.service.UserService;

/**
 * Created by santoshm1 on 30/05/14.
 */
@Service
public class UserServiceImpl implements UserService {
	private static Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);

	@Override
	public ParseObject findUserById(String userId) {
		/*log.info("Request to fetch user with Id : "+userId);
		User user = users.findByUserIdAndIsDeleted(userId,CommonConstant.IS_NOT_DELETED);
		log.info("Request to fetch user with Id success : "+user);*/
		ParseObject user = new ParseObject("User");
		ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
		query.whereEqualTo("objectId", user.getObjectId());
		List<ParseObject> userDetails=null;
		try {
			userDetails = query.find();
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		user = userDetails.get(0);		
		return user;
	}

	@Override
	public User findByUserName(String userName) {
		/*logger.info("Request to fetch user with Id : "+userId);
		User user = users.findByUserIdAndIsDeleted(userId,CommonConstant.IS_NOT_DELETED);
		logger.info("Request to fetch user with Id success : "+user);*/
		return null;
	}
}
