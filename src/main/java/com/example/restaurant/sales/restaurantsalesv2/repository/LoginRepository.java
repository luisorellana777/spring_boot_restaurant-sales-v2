package com.example.restaurant.sales.restaurantsalesv2.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.restaurant.sales.restaurantsalesv2.entity.User;
import com.example.restaurant.sales.restaurantsalesv2.entity.repository.UserEntityRepository;
import com.example.restaurant.sales.restaurantsalesv2.exception.UserNotFoundException;

@Repository
public class LoginRepository {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserEntityRepository userEntityRepository;

	public boolean isValid(String email, String password) {

		List<User> user = userEntityRepository.findByName(email);

		if (user.isEmpty()) {
			throw new UserNotFoundException("Usuario(Email) o Clave No Encontrado");
		}

		String passwordEncoded = user.iterator().next().getPassword();

		return passwordEncoder.matches(password, passwordEncoded);
	}
}
