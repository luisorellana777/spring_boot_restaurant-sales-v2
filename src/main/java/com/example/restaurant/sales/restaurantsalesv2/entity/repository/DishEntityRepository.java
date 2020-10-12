package com.example.restaurant.sales.restaurantsalesv2.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.restaurant.sales.restaurantsalesv2.entity.Dish;

public interface DishEntityRepository extends JpaRepository<Dish, Long> {

	@Query(value = "SELECT COUNT(DISH_ID) FROM sale_dish WHERE DISH_ID = :DISH_ID AND SALE_ID = :SALE_ID", nativeQuery = true)
	public Integer getQuantity(@Param("DISH_ID") Long idDish, @Param("SALE_ID") Long idSale);

}
