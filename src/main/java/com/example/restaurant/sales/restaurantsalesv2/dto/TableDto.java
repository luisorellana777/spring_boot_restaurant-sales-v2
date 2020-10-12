package com.example.restaurant.sales.restaurantsalesv2.dto;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableDto {

	private Long id;
	@Min(value = 1, message = "Ingresar un valor valido para el numero de la mesa.")
	private int number;

	@Min(value = 1, message = "Cantidad minima de cantidad de personas es 1.")
	private int seats;
}
