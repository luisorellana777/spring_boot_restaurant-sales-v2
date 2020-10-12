package com.example.restaurant.sales.restaurantsalesv2.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restaurant.sales.restaurantsalesv2.dto.WaiterDto;
import com.example.restaurant.sales.restaurantsalesv2.entity.Waiter;
import com.example.restaurant.sales.restaurantsalesv2.repository.WaiterRepository;

@Service
public class WaiterService {

	@Autowired
	private WaiterRepository waiterRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<WaiterDto> getWaiters() {

		List<WaiterDto> waitersDto = new ArrayList<WaiterDto>();
		List<Waiter> waitersEntity = waiterRepository.getWaiters();

		waitersEntity.forEach(waiterEntity -> {
			WaiterDto waiterDto = modelMapper.map(waiterEntity, WaiterDto.class);
			waitersDto.add(waiterDto);
		});

		return waitersDto;
	}

}
