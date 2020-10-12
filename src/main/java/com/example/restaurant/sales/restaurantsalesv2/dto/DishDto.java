package com.example.restaurant.sales.restaurantsalesv2.dto;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishDto {

	private Long id;

	private String name;

	@Min(value = 1, message = "Cantidad minima de platos de este tipo es 1.")
	private int quantity;

	private Long price;

}
