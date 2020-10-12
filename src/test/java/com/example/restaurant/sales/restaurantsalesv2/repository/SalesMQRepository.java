package com.example.restaurant.sales.restaurantsalesv2.repository;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.restaurant.sales.restaurantsalesv2.config.ConfigurationValues;
import com.example.restaurant.sales.restaurantsalesv2.dto.SaleDto;

@Repository
public class SalesMQRepository {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	ConfigurationValues configurationValues;

	public void pushSale(SaleDto saleDto) {
		rabbitTemplate.convertAndSend(configurationValues.getQueueName(), saleDto);
	}

	public SaleDto pullSale() {
		SaleDto receiveAndConvert = (SaleDto) rabbitTemplate.receiveAndConvert(configurationValues.getQueueName());
		return receiveAndConvert;
	}

}
