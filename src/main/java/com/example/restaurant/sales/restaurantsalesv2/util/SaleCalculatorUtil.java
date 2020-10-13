package com.example.restaurant.sales.restaurantsalesv2.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.example.restaurant.sales.restaurantsalesv2.config.ConfigurationValues;
import com.example.restaurant.sales.restaurantsalesv2.dto.AmountDto;
import com.example.restaurant.sales.restaurantsalesv2.dto.DishDto;
import com.example.restaurant.sales.restaurantsalesv2.dto.SaleDto;
import com.example.restaurant.sales.restaurantsalesv2.dto.TableDto;
import com.example.restaurant.sales.restaurantsalesv2.dto.WaiterDto;
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

	public List<SaleDto> createMockSaleDto(Integer salesQuantity) {

		List<SaleDto> salesDto = new ArrayList<SaleDto>();

		Random random = new Random();

		for (int iSale = 0; iSale < salesQuantity; iSale++) {

			SaleDto newSaleDto = new SaleDto();

			Long tip = (long) (random.nextInt(5000 - 2) + 1);
			newSaleDto.setTip(tip);

			ArrayList<DishDto> listDishDto = new ArrayList<DishDto>();
			Long dishes = (long) (random.nextInt(10 - 1 + 1) + 1);

			for (int iDish = 0; iDish < dishes; iDish++) {

				Long dishId = (long) (random.nextInt(1002 - 1000 + 1) + 1000);
				Integer dishQuantity = (random.nextInt(10 - 1 + 1) + 1);

				DishDto newDishDto = new DishDto();
				newDishDto.setId(dishId);
				newDishDto.setQuantity(dishQuantity);

				listDishDto.add(newDishDto);
			}

			newSaleDto.setDishes(listDishDto);

			TableDto newTableDto = new TableDto();
			Long tableId = (long) (random.nextInt(1002 - 1000 + 1) + 1000);
			newTableDto.setId(tableId);
			newSaleDto.setTable(newTableDto);
			WaiterDto newWaiterDto = new WaiterDto();
			Long waiterId = (long) (random.nextInt(1002 - 1000 + 1) + 1000);
			newWaiterDto.setId(waiterId);
			newSaleDto.setWaiter(newWaiterDto);

			salesDto.add(newSaleDto);
		}

		return salesDto;
	}
}
