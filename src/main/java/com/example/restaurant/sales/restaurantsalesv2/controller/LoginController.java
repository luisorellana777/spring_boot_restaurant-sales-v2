package com.example.restaurant.sales.restaurantsalesv2.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.restaurant.sales.restaurantsalesv2.dto.LoginDto;
import com.example.restaurant.sales.restaurantsalesv2.service.LoginService;

@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PutMapping(path = "/login")
	public ResponseEntity<Object> login(@RequestBody @Valid LoginDto loginDto) {

		String message = this.loginService.login(loginDto.getEmail(), loginDto.getPassword());
		return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
	}
}
