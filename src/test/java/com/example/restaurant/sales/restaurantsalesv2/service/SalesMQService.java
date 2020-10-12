package com.example.restaurant.sales.restaurantsalesv2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.restaurant.sales.restaurantsalesv2.dto.AmountDto;
import com.example.restaurant.sales.restaurantsalesv2.dto.SaleDto;
import com.example.restaurant.sales.restaurantsalesv2.exception.GeneralException;
import com.example.restaurant.sales.restaurantsalesv2.repository.SalesMQRepository;
import com.example.restaurant.sales.restaurantsalesv2.util.SaleCalculatorUtil;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SalesMQService {

	@Autowired
	SalesMQRepository salesMQRepository;

	@Autowired
	private SaleCalculatorUtil calculatorUtil;

	public List<SaleDto> pullSales() {
		try {

			List<SaleDto> salesDto = new ArrayList<SaleDto>();
			while (true) {

				SaleDto receiveAndConvert = salesMQRepository.pullSale();

				if (Objects.isNull(receiveAndConvert))
					break;

				salesDto.add(receiveAndConvert);
				log.info("{}", receiveAndConvert);
			}

			return salesDto;

		} catch (Exception ex) {

			throw new GeneralException(ex.getMessage());
		}
	}

	public SaleDto pullSale() {
		try {

			SaleDto receiveAndConvert = salesMQRepository.pullSale();
			log.info("{}", receiveAndConvert);
			return receiveAndConvert;

		} catch (Exception ex) {

			throw new GeneralException(ex.getMessage());
		}
	}

	@Transactional
	public String pushSales(List<SaleDto> salesDto) {

		try {

			salesDto.parallelStream().forEach(saleDto -> {

				AmountDto amounts = calculatorUtil.calculateAmounts(saleDto);
				saleDto.setNeto(amounts.getNeto());
				saleDto.setTax(amounts.getTax());
				saleDto.setTotal(amounts.getTotal());
				saleDto.setTip(saleDto.getTip());

			});

			salesDto.forEach(saleDto -> salesMQRepository.pushSale(saleDto));

			return calculatorUtil.getMessage("Ventas Almacenadas en Cola.");

		} catch (Exception ex) {

			throw new GeneralException(ex.getMessage());
		}
	}

	public String pushSale(SaleDto saleDto) {

		try {

			AmountDto amounts = calculatorUtil.calculateAmounts(saleDto);
			saleDto.setNeto(amounts.getNeto());
			saleDto.setTax(amounts.getTax());
			saleDto.setTotal(amounts.getTotal());
			saleDto.setTip(saleDto.getTip());

			salesMQRepository.pushSale(saleDto);

			return calculatorUtil.getMessage("Venta Almacenada en Cola.");

		} catch (Exception ex) {

			throw new GeneralException(ex.getMessage());
		}
	}

}