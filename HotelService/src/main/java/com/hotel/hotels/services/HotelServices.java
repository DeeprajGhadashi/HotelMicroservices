package com.hotel.hotels.services;

import java.util.List;

import com.hotel.hotels.entities.Hotel;

public interface HotelServices {

	//create
	Hotel create (Hotel hotel);
	
	//getall
	List<Hotel> getAll();
	
	//get single
	Hotel get(String id);
		
}
