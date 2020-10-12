package com.example.restaurant.sales.restaurantsalesv2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.restaurant.sales.restaurantsalesv2.entity.Waiter;
import com.example.restaurant.sales.restaurantsalesv2.entity.repository.WaiterEntityRepository;
import com.example.restaurant.sales.restaurantsalesv2.exception.EntityNotFoundException;
import com.example.restaurant.sales.restaurantsalesv2.exception.GeneralException;

@Repository
public class WaiterRepository {

	@Autowired
	private WaiterEntityRepository waiterEntityRepository;

	public Waiter getWaiter(Long waiterId) {

		Optional<Waiter> optionalWaiter = waiterEntityRepository.findById(waiterId);
		if (!optionalWaiter.isPresent())
			throw new EntityNotFoundException("Mesero no encontrado: " + (waiterId));
		Waiter waiterEntity = optionalWaiter.get();

		return waiterEntity;
	}

	public List<Waiter> getWaiters() {

		try {

			List<Waiter> waiters = waiterEntityRepository.findAll();

			return waiters;
		} catch (Exception ex) {

			throw new GeneralException("Error al obtener los meseros.");
		}
	}

}
