package com.example.restaurant.sales.restaurantsalesv2.exception;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExceptionResponse {

	private Date timeStamp;
	private String message;
	private String details;

	public ExceptionResponse(String message, String details) {
		super();
		this.timeStamp = new Date();
		this.message = message;
		this.details = details;
	}

	public ExceptionResponse(Date timeStamp, String message, String details) {
		super();
		this.timeStamp = timeStamp;
		this.message = message;
		this.details = details;
	}

}
