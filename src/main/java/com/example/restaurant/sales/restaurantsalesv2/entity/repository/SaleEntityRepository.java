package com.example.restaurant.sales.restaurantsalesv2.entity.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.restaurant.sales.restaurantsalesv2.entity.Sale;

public interface SaleEntityRepository extends JpaRepository<Sale, Long> {

	@Query(value = "SELECT * FROM sale S WHERE (S.CREATED_DATE = :DAY OR S.LAST_UPDATED_DATE = :DAY) AND S.IS_DELETED = FALSE", nativeQuery = true)
	public List<Sale> findSalesByDate(@Param("DAY") LocalDate day);
}
