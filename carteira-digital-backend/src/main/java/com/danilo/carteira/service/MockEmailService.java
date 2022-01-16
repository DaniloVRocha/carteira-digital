package com.danilo.carteira.service;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService{

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Simulando Envio de Email");
		LOG.info(msg.toString());
		LOG.info("Email Enviado ...");

	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Simulando Envio de Email HTML");
		LOG.info(msg.toString());
		LOG.info("Email Enviado ...");
	}

}
