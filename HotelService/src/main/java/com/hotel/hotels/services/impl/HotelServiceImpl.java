package com.hotel.hotels.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.hotels.entities.Hotel;
import com.hotel.hotels.exceptions.ResourceNotFoundException;
import com.hotel.hotels.repositories.HotelRepository;
import com.hotel.hotels.services.HotelServices;

@Service
public class HotelServiceImpl implements HotelServices {

	@Autowired
	private HotelRepository hotelRepository;
	

	public Hotel create(Hotel hotel) {
		String hotelId = UUID.randomUUID().toString();
		hotel.setId(hotelId);         // from @setter Id  
		return hotelRepository.save(hotel);
	}

	@Override
	public List<Hotel> getAll() {
		return hotelRepository.findAll();
	}

	@Override
	public Hotel get(String id) {
		return hotelRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("hotel with given id not found !!"));

    }
}
