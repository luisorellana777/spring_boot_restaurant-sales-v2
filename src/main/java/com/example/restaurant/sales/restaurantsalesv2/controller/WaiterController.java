package com.example.restaurant.sales.restaurantsalesv2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restaurant.sales.restaurantsalesv2.dto.WaiterDto;
import com.example.restaurant.sales.restaurantsalesv2.service.WaiterService;

@RestController
@RequestMapping(path = "waiters")
public class WaiterController {

	@Autowired
	private WaiterService waiterService;

	@GetMapping(path = "/")
	public ResponseEntity<List<WaiterDto>> getWaiters() {

		List<WaiterDto> waitersDto = waiterService.getWaiters();
		return new ResponseEntity<>(waitersDto, HttpStatus.OK);
	}
}
