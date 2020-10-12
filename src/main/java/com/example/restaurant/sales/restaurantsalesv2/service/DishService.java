package com.example.restaurant.sales.restaurantsalesv2.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restaurant.sales.restaurantsalesv2.dto.DishDto;
import com.example.restaurant.sales.restaurantsalesv2.entity.Dish;
import com.example.restaurant.sales.restaurantsalesv2.repository.DishRepository;

@Service
public class DishService {

	@Autowired
	private DishRepository dishRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<DishDto> getDishes() {

		List<DishDto> dishesDto = new ArrayList<DishDto>();
		List<Dish> dishesEntity = dishRepository.getDishes();

		dishesEntity.forEach(dishEntity -> {
			DishDto dishDto = modelMapper.map(dishEntity, DishDto.class);
			dishesDto.add(dishDto);
		});

		return dishesDto;
	}

}
