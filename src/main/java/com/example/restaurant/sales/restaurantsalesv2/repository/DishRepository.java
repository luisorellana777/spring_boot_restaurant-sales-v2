package com.example.restaurant.sales.restaurantsalesv2.repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.restaurant.sales.restaurantsalesv2.entity.Dish;
import com.example.restaurant.sales.restaurantsalesv2.entity.repository.DishEntityRepository;
import com.example.restaurant.sales.restaurantsalesv2.exception.EntityNotFoundException;
import com.example.restaurant.sales.restaurantsalesv2.exception.GeneralException;

@Repository
public class DishRepository {

	@Autowired
	private DishEntityRepository dishEntityRepository;

	public Integer getQuantityDishes(Long idDish, Long idSale) {

		Integer quantity = dishEntityRepository.getQuantity(idDish, idSale);
		if (Objects.isNull(quantity))
			throw new EntityNotFoundException("Venta no encontrada: " + (idSale));

		return quantity;
	}

	public Dish getDish(Long dishId) {

		Optional<Dish> optionalDish = dishEntityRepository.findById(dishId);
		if (!optionalDish.isPresent())
			throw new EntityNotFoundException("Plato no encontrado: " + (dishId));

		Dish dishEntity = optionalDish.get();

		return dishEntity;
	}

	public List<Dish> getDishes() {

		try {

			List<Dish> dishes = dishEntityRepository.findAll();

			return dishes;
		} catch (Exception ex) {

			throw new GeneralException("Error al obtener los platos de comida.");
		}
	}
}
