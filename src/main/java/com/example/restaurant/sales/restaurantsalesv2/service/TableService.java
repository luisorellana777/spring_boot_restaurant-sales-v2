package com.example.restaurant.sales.restaurantsalesv2.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restaurant.sales.restaurantsalesv2.dto.TableDto;
import com.example.restaurant.sales.restaurantsalesv2.entity.Table;
import com.example.restaurant.sales.restaurantsalesv2.repository.TableRepository;

@Service
public class TableService {

	@Autowired
	private TableRepository tableRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<TableDto> getTables() {

		List<TableDto> tablesDto = new ArrayList<TableDto>();
		List<Table> tablesEntity = tableRepository.getTables();

		tablesEntity.forEach(tableEntity -> {
			TableDto tableDto = modelMapper.map(tableEntity, TableDto.class);
			tablesDto.add(tableDto);
		});

		return tablesDto;
	}

}
