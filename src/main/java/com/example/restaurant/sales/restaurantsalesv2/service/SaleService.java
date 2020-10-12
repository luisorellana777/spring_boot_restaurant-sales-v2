package com.example.restaurant.sales.restaurantsalesv2.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restaurant.sales.restaurantsalesv2.dto.AmountDto;
import com.example.restaurant.sales.restaurantsalesv2.dto.SaleDto;
import com.example.restaurant.sales.restaurantsalesv2.entity.Dish;
import com.example.restaurant.sales.restaurantsalesv2.entity.Sale;
import com.example.restaurant.sales.restaurantsalesv2.entity.Table;
import com.example.restaurant.sales.restaurantsalesv2.entity.Waiter;
import com.example.restaurant.sales.restaurantsalesv2.repository.DishRepository;
import com.example.restaurant.sales.restaurantsalesv2.repository.SaleRepository;
import com.example.restaurant.sales.restaurantsalesv2.repository.TableRepository;
import com.example.restaurant.sales.restaurantsalesv2.repository.WaiterRepository;
import com.example.restaurant.sales.restaurantsalesv2.util.SaleCalculatorUtil;

@Service
public class SaleService {

	@Autowired
	private DishRepository dishRepository;

	@Autowired
	private TableRepository tableRepository;

	@Autowired
	private WaiterRepository waiterRepository;

	@Autowired
	private SaleRepository saleRepository;

	@Autowired
	private SaleCalculatorUtil saleCalculatorUtil;

	@Autowired
	private ModelMapper modelMapper;

	public List<SaleDto> getSales() {

		List<SaleDto> salesDto = new ArrayList<SaleDto>();
		List<Sale> salesEntity = saleRepository.findAllSales();

		salesEntity.forEach(saleEntity -> {

			SaleDto saleDto = this.transformSaleEntityToDto(saleEntity);
			salesDto.add(saleDto);
		});

		return salesDto;
	}

	public String eliminateSale(Long idSale) {

		saleRepository.deleteSale(idSale);

		return saleCalculatorUtil.getMessage("Venta eliminada: " + idSale);
	}

	public SaleDto getSale(Long idSale) {

		Sale saleEntity = saleRepository.getSale(idSale);
		SaleDto saleDto = this.transformSaleEntityToDto(saleEntity);
		return saleDto;
	}

	public SaleDto setSales(SaleDto saleDto) {

		Sale saleEntity = new Sale();
		saleDto.getDishes().forEach(dish -> {

			Dish dishEntity = dishRepository.getDish(dish.getId());
			dish.setPrice(dishEntity.getPrice());
			dish.setName(dishEntity.getName());
			for (int i = 0; i < dish.getQuantity(); i++) {
				dishEntity.addSale(saleEntity);
				saleEntity.addDish(dishEntity);
			}
		});

		AmountDto amounts = saleCalculatorUtil.calculateAmounts(saleDto);
		saleEntity.setNeto(amounts.getNeto());
		saleEntity.setTax(amounts.getTax());
		saleEntity.setTotal(amounts.getTotal());
		saleEntity.setTip(saleDto.getTip());

		Long tableId = saleDto.getTable().getId();
		Table tableEntity = tableRepository.getTable(tableId);
		tableEntity.addSale(saleEntity);
		saleEntity.setTable(tableEntity);

		Long waiterId = saleDto.getWaiter().getId();
		Waiter waiterEntity = waiterRepository.getWaiter(waiterId);
		waiterEntity.addSale(saleEntity);
		saleEntity.setWaiter(waiterEntity);

		saleEntity.setId(saleDto.getId());
		Sale savedSale = saleRepository.setSale(saleEntity);
		this.assignDtoValues(saleDto, savedSale);

		return saleDto;
	}

	private void assignDtoValues(SaleDto saleDto, Sale savedSale) {

		saleDto.setId(savedSale.getId());
		saleDto.setNeto(savedSale.getNeto());
		saleDto.setTax(savedSale.getTax());
		saleDto.setTotal(savedSale.getTotal());
		saleDto.getTable().setNumber(savedSale.getTable().getNumber());
		saleDto.getTable().setSeats(savedSale.getTable().getSeats());
		saleDto.getWaiter().setName(savedSale.getWaiter().getName());
		saleDto.getWaiter().setRut(savedSale.getWaiter().getRut());
	}

	private SaleDto transformSaleEntityToDto(Sale saleEntity) {

		SaleDto saleDto = modelMapper.map(saleEntity, SaleDto.class);
		saleDto.setDishes(new ArrayList<>(new HashSet<>(saleDto.getDishes())));

		saleDto.getDishes().forEach(dishDto -> {

			Integer quantity = dishRepository.getQuantityDishes(dishDto.getId(), saleEntity.getId());
			dishDto.setQuantity(quantity);
		});

		return saleDto;
	}

	public List<SaleDto> getSalesToday() {

		List<SaleDto> salesDto = new ArrayList<SaleDto>();
		List<Sale> salesEntity = saleRepository.getSalesToday();

		salesEntity.forEach(saleEntity -> {

			SaleDto saleDto = this.transformSaleEntityToDto(saleEntity);
			salesDto.add(saleDto);
		});

		return salesDto;
	}

}
