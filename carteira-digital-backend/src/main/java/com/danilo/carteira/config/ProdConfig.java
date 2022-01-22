package com.danilo.carteira.config;

import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.danilo.carteira.service.EmailService;
import com.danilo.carteira.service.SmtpEmailService;

@Configuration
@Profile("prod")
public class ProdConfig {
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
	@Bean
	 public Random random() {
	     return new Random();
	 }

}