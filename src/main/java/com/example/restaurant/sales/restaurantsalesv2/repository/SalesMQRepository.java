package com.example.restaurant.sales.restaurantsalesv2.repository;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.restaurant.sales.restaurantsalesv2.config.ConfigurationValues;
import com.example.restaurant.sales.restaurantsalesv2.dto.SaleDto;
import com.example.restaurant.sales.restaurantsalesv2.service.SaleService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
@RabbitListener(queues = "${restaurant-sales.rabbitmq.queue-name}")
public class SalesMQRepository {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private SaleService saleService;

	@Autowired
	ConfigurationValues configurationValues;

	public void pushSale(SaleDto saleDto) {
		rabbitTemplate.convertAndSend(configurationValues.getQueueName(), saleDto);
	}

	public SaleDto pullSale() {
		SaleDto receiveAndConvert = (SaleDto) rabbitTemplate.receiveAndConvert(configurationValues.getQueueName());
		return receiveAndConvert;
	}

	@RabbitHandler
	public void receiveSale(SaleDto receivedSaleDto) {

		saleService.setSales(receivedSaleDto);
		log.info("Servicio planificador -> Se ha almacenado en la base de datos la siguiente venta: {}",
				receivedSaleDto);
	}

}
