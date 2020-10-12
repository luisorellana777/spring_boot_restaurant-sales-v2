package com.example.restaurant.sales.restaurantsalesv2.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.restaurant.sales.restaurantsalesv2.entity.Sale;
import com.example.restaurant.sales.restaurantsalesv2.entity.repository.SaleEntityRepository;
import com.example.restaurant.sales.restaurantsalesv2.exception.EntityNotFoundException;
import com.example.restaurant.sales.restaurantsalesv2.exception.GeneralException;

@Repository
public class SaleRepository {

	@Autowired
	private SaleEntityRepository saleEntityRepository;

	public Sale getSale(Long idSale) {

		try {

			Optional<Sale> optionalSaleEntity = saleEntityRepository.findById(idSale);
			if (!optionalSaleEntity.isPresent())
				throw new EntityNotFoundException("Venta no encontrada: " + (idSale));
			Sale saleEntity = optionalSaleEntity.get();

			return saleEntity;

		} catch (IllegalArgumentException ex) {

			throw new GeneralException("Error al almacenar la venta.");
		}
	}

	public Sale setSale(Sale saleEntity) {

		try {

			Sale savedSale = saleEntityRepository.save(saleEntity);

			return savedSale;
		} catch (Exception ex) {

			throw new GeneralException("Error al almacenar la venta.");
		}

	}

	public List<Sale> findAllSales() {

		try {

			List<Sale> sales = saleEntityRepository.findAll();

			return sales;
		} catch (Exception ex) {

			throw new GeneralException("Error al obtener las ventas.");
		}

	}

	public void deleteSale(Long idSale) {

		try {

			boolean existsById = saleEntityRepository.existsById(idSale);
			if (!existsById)
				throw new EntityNotFoundException("Venta no encontrada: " + (idSale));

			saleEntityRepository.deleteById(idSale);

		} catch (IllegalArgumentException ex) {

			throw new GeneralException("Error al almacenar la venta.");
		}

	}

	public List<Sale> getSalesToday() {

		try {

			List<Sale> sales = saleEntityRepository.findSalesByDate(LocalDate.now());

			return sales;
		} catch (Exception ex) {

			throw new GeneralException("Error al obtener las ventas del dia de hoy.");
		}
	}
}
