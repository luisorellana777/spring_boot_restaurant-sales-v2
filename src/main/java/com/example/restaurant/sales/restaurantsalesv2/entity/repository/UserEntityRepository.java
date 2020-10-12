package com.example.restaurant.sales.restaurantsalesv2.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restaurant.sales.restaurantsalesv2.entity.User;

public interface UserEntityRepository extends JpaRepository<User, Long> {

	public List<User> findByName(String name);
}
