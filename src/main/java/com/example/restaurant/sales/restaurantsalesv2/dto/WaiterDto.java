package com.example.restaurant.sales.restaurantsalesv2.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaiterDto {

	private Long id;

	@Pattern(regexp = "^0*(\\d{1,3}(\\.?\\d{3})*)\\-?([\\dkK])$", message = "El rut debe ser valido: Ejemplo (8200530-7)")
	private String rut;

	@NotBlank(message = "Se debe especificar un nombre")
	private String name;
}
