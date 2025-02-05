package com.hotel.user.service.services;

import java.util.List;

import com.hotel.user.service.entities.User;

public interface UserService {

    // user operations
	
	//create
	User saveUser (User user);
	
	//get all user
	List<User> getAllUser();
	
	//get single user of given userId
	User getUser(String userId);
}
