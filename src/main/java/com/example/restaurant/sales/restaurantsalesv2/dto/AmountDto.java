package com.example.restaurant.sales.restaurantsalesv2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmountDto {

	private Long neto;
	private Long tax;
	private Long total;
}
