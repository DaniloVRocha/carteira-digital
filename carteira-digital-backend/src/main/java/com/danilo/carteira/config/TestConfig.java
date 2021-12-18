package com.danilo.carteira.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.danilo.carteira.domain.Cliente;
import com.danilo.carteira.domain.enums.Perfil;
import com.danilo.carteira.domain.enums.TipoCliente;
import com.danilo.carteira.repository.ClienteRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private BCryptPasswordEncoder pe;
	

	@Override
	public void run(String... args) throws Exception {
		Cliente cli1 = new Cliente(null, "Danilo", "00000000000", "danilo@gmail.com", 
				pe.encode("testesenha"), TipoCliente.PESSOAFISICA, true, "Observação Cliente");
		cli1.addPerfil(Perfil.ADMIN);
		
		Cliente cli2 = new Cliente(null, "James Gosling", "00000000001", "java@gmail.com", 
				pe.encode("java123"), TipoCliente.PESSOAJURIDICA, true, "Use Java");
		
		Cliente cli3 = new Cliente(null, "Ana Costa", "00000000022", "ana@gmail.com", 
				pe.encode("ana123"), TipoCliente.PESSOAFISICA, true, "Cadastro Ana Costa");
		
		clienteRepository.saveAll(Arrays.asList(cli1,cli2, cli3));
	}
	
	
}
