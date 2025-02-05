package com.hotel.hotels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.hotels.entities.Hotel;

public interface HotelRepository extends JpaRepository<Hotel,String>{

	
}
