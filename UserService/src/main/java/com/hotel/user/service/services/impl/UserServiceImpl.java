package com.hotel.user.service.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hotel.user.service.entities.Hotel;
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
		//implement RATING SERVICE CALL: USING REST TEMPLATE
		return userRepository.findAll();
	}

	@Override
	public User getUser(String userId) {
	    // Get user from database with the help of user repository
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server !! :" + userId));

	    // Fetch ratings of the user from RATING SERVICE
	    Rating[] ratingsOfUser = restTemplate.getForObject("http://localhost:8083/ratings/users/" + user.getUserId(), Rating[].class);
	    
	    logger.info("Rating for user {}", (Object) ratingsOfUser);  // Explicit cast to avoid array printing issue

	    // Convert array to list safely
	    List<Rating> ratings = (ratingsOfUser != null) ? Arrays.asList(ratingsOfUser) : new ArrayList<>();

	    List<Rating> ratingList = ratings.stream().map(rating -> {
	        // API call to hotel service to get the hotel
	        ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://localhost:8082/hotels/" + rating.getHotelId(), Hotel.class);
	        Hotel hotel = forEntity.getBody();
	        
	        logger.info("Response status code: {}", forEntity.getStatusCode());

	        // Set the hotel to rating if not null
	        if (hotel != null) {
	            rating.setHotel(hotel);
	        }

	        return rating;
	    }).collect(Collectors.toList());

	    // Set ratings to user
	    user.setRatings(ratingList);

	    return user;
	}

	
	
}
