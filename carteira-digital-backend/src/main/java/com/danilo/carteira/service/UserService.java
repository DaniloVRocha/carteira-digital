package com.danilo.carteira.service;

import org.springframework.security.core.context.SecurityContextHolder;

import com.danilo.carteira.config.security.UserSS;

public class UserService {

	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
}
