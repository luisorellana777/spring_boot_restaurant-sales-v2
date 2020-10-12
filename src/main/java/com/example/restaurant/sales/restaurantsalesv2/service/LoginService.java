package com.example.restaurant.sales.restaurantsalesv2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restaurant.sales.restaurantsalesv2.exception.UserNotFoundException;
import com.example.restaurant.sales.restaurantsalesv2.repository.LoginRepository;
import com.example.restaurant.sales.restaurantsalesv2.security.service.TokenService;
import com.example.restaurant.sales.restaurantsalesv2.util.SaleCalculatorUtil;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class LoginService {

	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private SaleCalculatorUtil saleCalculatorUtil;

	public String login(String email, String password) {

		if (loginRepository.isValid(email, password)) {

			String token = tokenService.getJWTToken(email);
			log.info("Credencial Correcto. Token -> {}", token);

			return saleCalculatorUtil.getMessage("Credencial Correcto.", token);
		} else {

			throw new UserNotFoundException("Usuario(Email) o Clave No Encontrado");
		}

	}

}
