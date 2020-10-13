package com.example.restaurant.sales.restaurantsalesv2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.restaurant.sales.restaurantsalesv2.config.ConfigurationValues;
import com.example.restaurant.sales.restaurantsalesv2.dto.DishDto;
import com.example.restaurant.sales.restaurantsalesv2.dto.SaleDto;
import com.example.restaurant.sales.restaurantsalesv2.dto.TableDto;
import com.example.restaurant.sales.restaurantsalesv2.dto.WaiterDto;
import com.example.restaurant.sales.restaurantsalesv2.service.SaleService;
import com.example.restaurant.sales.restaurantsalesv2.service.SalesMQService;
import com.example.restaurant.sales.restaurantsalesv2.util.SaleCalculatorUtil;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestaurantSalesV2ApplicationTests {

	@Autowired
	private ConfigurationValues configurationValues;

	@Autowired
	private AmqpAdmin amqpAdmin;

	@Autowired
	private SalesMQService salesMQService;

	@Autowired
	private SaleService saleService;

	@Autowired
	private SaleCalculatorUtil saleCalculatorUtil;

	@Test
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	void testOneToOneMQ() {

		SaleDto newSaleDto = new SaleDto();
		newSaleDto.setTip(1000L);
		DishDto newDishDto = new DishDto();
		newDishDto.setId(1000L);
		newDishDto.setQuantity(3);
		ArrayList<DishDto> listDishDto = new ArrayList<DishDto>();
		listDishDto.add(newDishDto);
		newSaleDto.setDishes(listDishDto);
		TableDto newTableDto = new TableDto();
		newTableDto.setId(1000L);
		newSaleDto.setTable(newTableDto);
		WaiterDto newWaiterDto = new WaiterDto();
		newWaiterDto.setId(1000L);
		newSaleDto.setWaiter(newWaiterDto);

		saleService.setSales(newSaleDto);

		List<SaleDto> sales = saleService.getSales();
		SaleDto saleDto = saleService.getSale(1L);

		salesMQService.pushSale(saleDto);

		SaleDto pullSale = salesMQService.pullSale();

		assertNull(pullSale);

		assertEquals(1L, saleDto.getId());
		assertEquals(13865L, saleDto.getNeto());
		assertEquals(2634L, saleDto.getTax());
		assertEquals(17500L, saleDto.getTotal());
		assertEquals(1, saleDto.getDishes().size());

		List<SaleDto> salesAll = sales;
		salesMQService.pushSales(salesAll);
		List<SaleDto> pullSalesAll = salesMQService.pullSales();
		assertEquals(0, pullSalesAll.size());
	}

	@Test
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	void testManyToManyMQ() {

		List<SaleDto> newSalesDto = saleCalculatorUtil.createMockSaleDto(10);
		newSalesDto.forEach(saleDto -> {
			saleService.setSales(saleDto);
		});

		List<SaleDto> salesToday = saleService.getSalesToday();
		salesMQService.pushSales(salesToday);
		assertEquals(10, salesToday.size());
		List<SaleDto> pullSales = salesMQService.pullSales();
		assertEquals(0, pullSales.size());

		List<SaleDto> salesAll = saleService.getSales();
		salesMQService.pushSales(salesAll);
		List<SaleDto> pullSalesAll = salesMQService.pullSales();
		assertEquals(0, pullSalesAll.size());
	}

	@AfterAll
	public void cleanUp() {

		amqpAdmin.deleteQueue(configurationValues.getQueueName());
	}

	@BeforeEach
	public void purgeUp() {

		amqpAdmin.purgeQueue(configurationValues.getQueueName());
	}

}
