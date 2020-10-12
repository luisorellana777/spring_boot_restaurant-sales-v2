package com.example.restaurant.sales.restaurantsalesv2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restaurant.sales.restaurantsalesv2.dto.TableDto;
import com.example.restaurant.sales.restaurantsalesv2.service.TableService;

@RestController
@RequestMapping(path = "tables")
public class TableController {

	@Autowired
	private TableService tableService;

	@GetMapping(path = "/")
	public ResponseEntity<Object> getTables() {

		List<TableDto> tablesDto = tableService.getTables();
		return new ResponseEntity<>(tablesDto, HttpStatus.ACCEPTED);
	}
}
