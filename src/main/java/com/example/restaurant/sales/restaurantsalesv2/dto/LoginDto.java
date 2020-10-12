package com.example.restaurant.sales.restaurantsalesv2.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

	@NotNull(message = "El email es un campo requerido")
	private String email;
	@NotNull(message = "La password es un campo requerido")
	private String password;
}
