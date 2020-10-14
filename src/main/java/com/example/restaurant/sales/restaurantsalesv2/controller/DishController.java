package com.example.restaurant.sales.restaurantsalesv2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restaurant.sales.restaurantsalesv2.dto.DishDto;
import com.example.restaurant.sales.restaurantsalesv2.service.DishService;

@RestController
@RequestMapping(path = "dishes")
public class DishController {

	@Autowired
	private DishService dishService;

	@GetMapping(path = "/")
	public ResponseEntity<List<DishDto>> getDishes() {

		List<DishDto> dishesDto = dishService.getDishes();
		return new ResponseEntity<>(dishesDto, HttpStatus.OK);
	}
}
