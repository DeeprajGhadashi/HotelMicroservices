package com.hotel.user.service.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hotel.user.service.entities.Rating;
import com.hotel.user.service.entities.User;
import com.hotel.user.service.exceptions.ResourceNotFoundException;
import com.hotel.user.service.repositories.UserRepository;
import com.hotel.user.service.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public User saveUser(User user) {
		//generate unique userId
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
	    List<User> users = userRepository.findAll();

	    for (User user : users) {
	        try {
	            String url = "http://localhost:8083/ratings/users/" + user.getUserId();
	            Rating[] ratingsArray = restTemplate.getForObject(url, Rating[].class);
	            List<Rating> ratingsList = ratingsArray != null ? List.of(ratingsArray) : List.of();
	            user.setRatings(ratingsList);
	        } catch (Exception e) {
	            logger.error("Error fetching ratings for user {}: {}", user.getUserId(), e.getMessage());
	            user.setRatings(List.of()); // Set empty list if API call fails
	        }
	    }

	    return users;
	}


	@Override
	public User getUser(String userId) {
		//get user from database with the help of user repository
		User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with given id is not found on server !! :" + userId));
	    // fetch rating of the above user from RATING SERVICE
		//http://localhost:8083/ratings/users/b21bbc74-7dc0-40ad-8be3-653b79239f94 | calling with the http client 
		//used REST TEMPLATE
		ArrayList<Rating> ratingsOfUser = restTemplate.getForObject("http://localhost:8083/ratings/users/" + user.getUserId() , ArrayList.class);
		logger.info("Rating for user {}",ratingsOfUser);
		user.setRatings(ratingsOfUser);
		
		return user;
	}

	
	
}
