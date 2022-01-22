package com.danilo.carteira.config;

import java.text.ParseException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.danilo.carteira.service.DBService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService;

	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();
		return true;
	}
	
    @Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }
	
	@Bean
	 public Random random() {
	     return new Random();
	 }

}
