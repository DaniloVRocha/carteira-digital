package com.danilo.carteira.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.danilo.carteira.domain.Cliente;

public abstract class AbstractEmailService implements EmailService{

	@Value("${default.sender}")
	private String sender;

	@Override
	public void sendCreateClientConfirmation(Cliente obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromCliente(obj);	
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromCliente(Cliente obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Bem Vindo ao Carteira Digital, " + obj.getNome() + " seu acesso já está liberado.");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}

}
