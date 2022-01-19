package com.danilo.carteira.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.danilo.carteira.domain.Cliente;
import com.danilo.carteira.repository.ClienteRepository;
import com.danilo.carteira.service.exceptions.NotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private Random rand =  new Random();

	public void sendNewPassword(String email) {
		
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if(cliente == null) {
			throw new NotFoundException("Email não Encontrado");
		}
		
		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));
		
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for(int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);

		if (opt == 0) {// gera digito
			return (char) (rand.nextInt(10) + 48);
		} else if (opt == 1) { // gera maiuscula
			return (char) (rand.nextInt(26) + 65);
		} else { // gera minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}
	
}
