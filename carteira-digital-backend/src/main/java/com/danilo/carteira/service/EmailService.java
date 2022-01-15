package com.danilo.carteira.service;

import org.springframework.mail.SimpleMailMessage;

import com.danilo.carteira.domain.Cliente;

public interface EmailService {

	void sendCreateClientConfirmation(Cliente obj);
	void sendEmail(SimpleMailMessage msg);

}
