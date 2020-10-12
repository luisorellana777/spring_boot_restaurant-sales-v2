package com.example.restaurant.sales.restaurantsalesv2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.restaurant.sales.restaurantsalesv2.entity.Table;
import com.example.restaurant.sales.restaurantsalesv2.entity.repository.TableEntityRepository;
import com.example.restaurant.sales.restaurantsalesv2.exception.EntityNotFoundException;
import com.example.restaurant.sales.restaurantsalesv2.exception.GeneralException;

@Repository
public class TableRepository {

	@Autowired
	private TableEntityRepository tableEntityRepository;

	public Table getTable(Long tableId) {

		Optional<Table> optionalTable = tableEntityRepository.findById(tableId);
		if (!optionalTable.isPresent())
			throw new EntityNotFoundException("Mesa no encontrada: " + (tableId));
		Table tableEntity = optionalTable.get();

		return tableEntity;
	}

	public List<Table> getTables() {

		try {

			List<Table> tables = tableEntityRepository.findAll();

			return tables;
		} catch (Exception ex) {

			throw new GeneralException("Error al obtener las mesas.");
		}
	}
}
