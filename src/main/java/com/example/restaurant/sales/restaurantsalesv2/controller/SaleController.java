package com.example.restaurant.sales.restaurantsalesv2.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restaurant.sales.restaurantsalesv2.dto.SaleDto;
import com.example.restaurant.sales.restaurantsalesv2.service.SaleService;

@RestController
@RequestMapping(path = "sales")
public class SaleController {

	@Autowired
	private SaleService saleService;

	@GetMapping(path = "/")
	public ResponseEntity<List<SaleDto>> getSales() {

		List<SaleDto> salesDto = saleService.getSales();
		return new ResponseEntity<>(salesDto, HttpStatus.OK);
	}

	@GetMapping(path = "/{idSale}")
	public ResponseEntity<SaleDto> getSale(@PathVariable Long idSale) {

		SaleDto saleDto = saleService.getSale(idSale);
		return new ResponseEntity<>(saleDto, HttpStatus.OK);
	}

	@GetMapping(path = "/today")
	public ResponseEntity<List<SaleDto>> getSalesToday() {

		List<SaleDto> salesDto = saleService.getSalesToday();
		return new ResponseEntity<>(salesDto, HttpStatus.OK);
	}

	@PostMapping(path = "/")
	public ResponseEntity<SaleDto> postSales(@RequestBody @Valid SaleDto saleDto) {

		SaleDto newSaleDto = saleService.setSales(saleDto);
		return new ResponseEntity<>(newSaleDto, HttpStatus.OK);
	}

	@PutMapping(path = "/")
	public ResponseEntity<SaleDto> putSales(@RequestBody @Valid SaleDto saleDto) {

		SaleDto newSaleDto = saleService.setSales(saleDto);
		return new ResponseEntity<>(newSaleDto, HttpStatus.OK);
	}

	@DeleteMapping(path = "/{idSale}")
	public ResponseEntity<String> deleteSales(@PathVariable Long idSale) {

		String message = saleService.eliminateSale(idSale);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}
