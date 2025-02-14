package com.hotel.user.service.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.hotel.user.service.entities.Rating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Service;

@Service
@FeignClient(name = "RATINGSERVICE")
public interface RatingService {
	
	//GET
	
	
	
	//POST
	@PostMapping("/ratings")
	public Rating createRating(Rating values);
	
	
	//PUT
	@PutMapping("/ratings/{ratingId}")
    public Rating updateRating(@PathVariable("ratingId") String ratingId, Rating rating);
	
	//DELETE
	@DeleteMapping("/ratings/{ratingId}")
    public void DeleteRating(@PathVariable("ratingId") String ratingId );
	
}
