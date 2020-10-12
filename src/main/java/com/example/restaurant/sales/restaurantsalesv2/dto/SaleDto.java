package com.example.restaurant.sales.restaurantsalesv2.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDto {

	private Long id;

	@Valid
	@NotNull(message = "Se debe ingresar al menos un elemento")
	private List<DishDto> dishes;

	@NotNull(message = "Se debe ingresar un mesero.")
	private WaiterDto waiter;

	@NotNull(message = "Se debe ingresar una mesa.")
	private TableDto table;

	@Null(message = "Valor neto es auto calculado.")
	private Long neto;

	@Null(message = "Valor iva es auto calculado.")
	private Long tax;

	@Null(message = "Valor total es auto calculado.")
	private Long total;

	@NotNull(message = "El valor de la propina debe ser valido.")
	@Min(value = 1, message = "El valor de la propina debe ser valido.")
	private Long tip;
}
