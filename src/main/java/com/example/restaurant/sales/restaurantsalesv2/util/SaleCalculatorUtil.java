package com.example.restaurant.sales.restaurantsalesv2.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.example.restaurant.sales.restaurantsalesv2.config.ConfigurationValues;
import com.example.restaurant.sales.restaurantsalesv2.dto.AmountDto;
import com.example.restaurant.sales.restaurantsalesv2.dto.DishDto;
import com.example.restaurant.sales.restaurantsalesv2.dto.SaleDto;
import com.example.restaurant.sales.restaurantsalesv2.exception.GeneralException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SaleCalculatorUtil {

	@Autowired
	ConfigurationValues configurationValues;

	@Autowired
	@Qualifier("createObjectMapper")
	private ObjectMapper mapper;

	public AmountDto calculateAmounts(SaleDto saleDto) {

		List<DishDto> dishes = saleDto.getDishes();

		Long total = 0L;
		for (DishDto dishDto : dishes) {
			total = total + dishDto.getPrice() * dishDto.getQuantity();
		}

		Long neto = (long) (total / configurationValues.getNetoValue());

		Long tax = (long) (configurationValues.getTaxValue() * neto);

		total = total + saleDto.getTip();
		return new AmountDto(neto, tax, total);
	}

	public String getMessage(final String gotMessage) {

		try {

			@SuppressWarnings("unused")
			class MessageInnerClass {
				public String message = gotMessage;

			}

			return mapper.writeValueAsString(new MessageInnerClass());

		} catch (JsonProcessingException ex) {

			throw new GeneralException("Error al crear mensaje de respuesta.");
		}
	}

	public String getMessage(final String gotMessage, final String gotToken) {

		try {

			@SuppressWarnings("unused")
			class MessageInnerClass {
				public String message = gotMessage;
				public String token = gotToken;
			}

			return mapper.writeValueAsString(new MessageInnerClass());

		} catch (JsonProcessingException ex) {

			throw new GeneralException("Error al crear mensaje de respuesta.");
		}
	}
}
