package com.example.restaurant.sales.restaurantsalesv2.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import com.example.restaurant.sales.restaurantsalesv2.dto.SaleDto;
import com.example.restaurant.sales.restaurantsalesv2.repository.SalesMQRepository;
import com.example.restaurant.sales.restaurantsalesv2.util.SaleCalculatorUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@EnableScheduling
@Profile("usage_message")
public class SchedulerMockSaleConfig {

	private SaleCalculatorUtil saleCalculatorUtil;

	private SalesMQRepository salesMQRepository;

	@Autowired
	public SchedulerMockSaleConfig(@Lazy SalesMQRepository salesMQRepository,
			@Lazy SaleCalculatorUtil saleCalculatorUtil) {
		this.salesMQRepository = salesMQRepository;
		this.saleCalculatorUtil = saleCalculatorUtil;
	}

	@Transactional
	@Scheduled(cron = "${restaurant-sales.attriblog-scheduler.cron-expression}")
	public void scheduleSaleQueue() {

		List<SaleDto> salesDto = saleCalculatorUtil.createMockSaleDto(5);
		salesDto.forEach(saleDto -> {

			salesMQRepository.pushSale(saleDto);

			log.info("Servicio planificador -> Se ha almacenado en la cola la siguiente venta: {}", saleDto);
		});

	}

}
