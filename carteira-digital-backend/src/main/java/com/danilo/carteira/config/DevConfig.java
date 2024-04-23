package com.danilo.carteira.config;

import java.text.ParseException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.danilo.carteira.service.DBService;
import com.danilo.carteira.service.EmailService;
import com.danilo.carteira.service.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instantiateDatabase() throws ParseException {

		if (!"create".equals(strategy)) {
			return false;
		}
		return true;
	}

	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
	@Bean
	 public Random random() {
	     return new Random();
	 }

}