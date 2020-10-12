package com.example.restaurant.sales.restaurantsalesv2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String message) {
		super(message);
		log.error(message);
	}
}