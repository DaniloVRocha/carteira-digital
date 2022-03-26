package com.danilo.carteira.service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.danilo.carteira.domain.Cliente;
import com.danilo.carteira.domain.Conta;
import com.danilo.carteira.domain.OperacaoConta;
import com.danilo.carteira.domain.enums.Categoria;
import com.danilo.carteira.domain.enums.EstadoPagamento;
import com.danilo.carteira.repository.ClienteRepository;
import com.danilo.carteira.repository.ContaRepository;
import com.danilo.carteira.repository.OperacaoContaRepository;

@Service
public class DBService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ContaRepository contaRepository;
	@Autowired
	private OperacaoContaRepository operacaoContaRepository;
	@Autowired
	private BCryptPasswordEncoder pe;

	public void instantiateTestDatabase() throws ParseException {
		
		Cliente cli1 = new Cliente(null, "Danilo", "40476325030", "danilo@gmail.com", pe.encode("testesenha"));

		Cliente cli2 = new Cliente(null, "James Gosling", "42024840027", "java@gmail.com", pe.encode("java123"));

		Cliente cli3 = new Cliente(null, "Ana Costa", "26658128006", "ana@gmail.com", pe.encode("ana123"));

		Conta con1 = new Conta(null, "Itau", 0.00, cli1);
		Conta con4 = new Conta(null, "NuBank", 0.00, cli2);
		Conta con2 = new Conta(null, "Bradesco", 0.00, cli2);
		Conta con3 = new Conta(null, "Itau", 0.00, cli3);
		
		
		//Inclusão de Operações Teste
		
		OperacaoConta op1 = new OperacaoConta(null, "Futebol", LocalDateTime.of(2022, 1, 28, 14, 33, 48, 000000) , LocalDateTime.of(2022, 1, 28, 14, 33, 48, 000000), 'R', 20.00,
				EstadoPagamento.PENDENTE, con1, Categoria.LAZER);

		OperacaoConta op2 = new OperacaoConta(null,"Futebol", LocalDateTime.of(2022, 1, 28, 14, 33, 48, 000000), LocalDateTime.of(2022, 1, 28, 14, 33, 48, 000000), 'D', 30.00,
				EstadoPagamento.PENDENTE, con1, Categoria.FIXO);
		
		OperacaoConta op3 = new OperacaoConta(null, "Futebol", LocalDateTime.of(2022, 2, 28, 14, 33, 48, 000000), LocalDateTime.of(2022, 2, 28, 14, 33, 48, 000000), 'R', 50.00,
				EstadoPagamento.PENDENTE, con1, Categoria.LAZER);

		OperacaoConta op4 = new OperacaoConta(null,"Futebol", LocalDateTime.of(2022, 2, 28, 14, 33, 48, 000000), LocalDateTime.of(2022, 2, 28, 14, 33, 48, 000000), 'D', 90.00,
				EstadoPagamento.PENDENTE, con1, Categoria.FIXO);
		
		OperacaoConta op5 = new OperacaoConta(null, "Futebol", LocalDateTime.of(2022, 3, 28, 14, 33, 48, 000000), LocalDateTime.of(2022, 3, 28, 14, 33, 48, 000000), 'R', 100.00,
				EstadoPagamento.PENDENTE, con1, Categoria.LAZER);

		OperacaoConta op6 = new OperacaoConta(null,"Futebol", LocalDateTime.of(2022, 3, 28, 14, 33, 48, 000000), LocalDateTime.of(2022, 3, 28, 14, 33, 48, 000000), 'D', 80.00,
				EstadoPagamento.PENDENTE, con1, Categoria.FIXO);
		
		OperacaoConta op7 = new OperacaoConta(null, "Futebol", LocalDateTime.of(2022, 4, 28, 14, 33, 48, 000000), LocalDateTime.of(2022, 4, 28, 14, 33, 48, 000000), 'R', 20.00,
				EstadoPagamento.PENDENTE, con1, Categoria.LAZER);

		OperacaoConta op8 = new OperacaoConta(null,"Futebol", LocalDateTime.of(2022, 4, 28, 14, 33, 48, 000000), LocalDateTime.of(2022, 4, 28, 14, 33, 48, 000000), 'D', 20.00,
				EstadoPagamento.PENDENTE, con1, Categoria.FIXO);
		
		OperacaoConta op9 = new OperacaoConta(null, "Futebol", LocalDateTime.of(2022, 5, 28, 14, 33, 48, 000000), LocalDateTime.of(2022, 5, 28, 14, 33, 48, 000000), 'R', 25.00,
				EstadoPagamento.PENDENTE, con1, Categoria.LAZER);

		OperacaoConta op10 = new OperacaoConta(null,"Futebol", LocalDateTime.of(2022, 5, 28, 14, 33, 48, 000000), LocalDateTime.of(2022, 5, 28, 14, 33, 48, 000000), 'D', 47.00,
				EstadoPagamento.PENDENTE, con1, Categoria.FIXO);
		
		OperacaoConta op11 = new OperacaoConta(null, "Futebol", LocalDateTime.of(2022, 6, 28, 14, 33, 48, 000000), LocalDateTime.of(2022, 6, 28, 14, 33, 48, 000000), 'R', 20.35,
				EstadoPagamento.PENDENTE, con1, Categoria.LAZER);

		OperacaoConta op12 = new OperacaoConta(null,"Futebol", LocalDateTime.of(2022, 6, 28, 14, 33, 48, 000000), LocalDateTime.of(2022, 6, 28, 14, 33, 48, 000000), 'D', 27.82,
				EstadoPagamento.PENDENTE, con1, Categoria.FIXO);
		
		OperacaoConta op13 = new OperacaoConta(null, "Futebol", LocalDateTime.of(2022, 7, 28, 14, 33, 48, 000000), LocalDateTime.of(2022, 7, 28, 14, 33, 48, 000000), 'R', 20.10,
				EstadoPagamento.PENDENTE, con1, Categoria.LAZER);

		OperacaoConta op14 = new OperacaoConta(null,"Futebol", LocalDateTime.of(2022, 7, 28, 14, 33, 48, 000000), LocalDateTime.of(2022, 7, 28, 14, 33, 48, 000000), 'D', 150.37,
				EstadoPagamento.PENDENTE, con1, Categoria.FIXO);
		
		OperacaoConta op15 = new OperacaoConta(null, "Futebol", LocalDateTime.of(2022, 8, 28, 14, 33, 48, 000000), LocalDateTime.of(2022, 8, 28, 14, 33, 48, 000000), 'R', 90.00,
				EstadoPagamento.PENDENTE, con1, Categoria.LAZER);

		OperacaoConta op16 = new OperacaoConta(null,"Futebol", LocalDateTime.of(2022, 8, 28, 14, 33, 48, 000000), LocalDateTime.of(2022, 8, 28, 14, 33, 48, 000000), 'D', 130.00,
				EstadoPagamento.PENDENTE, con1, Categoria.FIXO);
		
		OperacaoConta op17 = new OperacaoConta(null, "Futebol", LocalDateTime.of(2022, 9, 28, 14, 33, 48, 000000), LocalDateTime.of(2022, 9, 28, 14, 33, 48, 000000), 'R', 10.00,
				EstadoPagamento.PENDENTE, con1, Categoria.LAZER);

		OperacaoConta op18 = new OperacaoConta(null,"Futebol", LocalDateTime.of(2022, 9, 28, 14, 33, 48, 000000), LocalDateTime.of(2022, 9, 28, 14, 33, 48, 000000), 'D', 5.55,
				EstadoPagamento.PENDENTE, con1, Categoria.FIXO);
		
		OperacaoConta op19 = new OperacaoConta(null, "Futebol", LocalDateTime.of(2022, 10, 28, 14, 33, 48, 000000), LocalDateTime.of(2022, 10, 28, 14, 33, 48, 000000), 'R', 34.90,
				EstadoPagamento.PENDENTE, con1, Categoria.LAZER);

		OperacaoConta op20 = new OperacaoConta(null,"Futebol", LocalDateTime.of(2022, 10, 28, 14, 33, 48, 000000), LocalDateTime.of(2022, 10, 28, 14, 33, 48, 000000), 'D', 27.80,
				EstadoPagamento.PENDENTE, con1, Categoria.FIXO);
		
		OperacaoConta op21 = new OperacaoConta(null, "Futebol", LocalDateTime.of(2022, 11, 28, 14, 33, 48, 000000), LocalDateTime.of(2022, 11, 28, 14, 33, 48, 000000), 'R', 12.95,
				EstadoPagamento.PENDENTE, con1, Categoria.LAZER);

		OperacaoConta op22 = new OperacaoConta(null,"Futebol", LocalDateTime.of(2022, 11, 28, 14, 33, 48, 000000), LocalDateTime.of(2022, 11, 28, 14, 33, 48, 000000), 'D', 20.00,
				EstadoPagamento.PENDENTE, con1, Categoria.FIXO);
		
		OperacaoConta op23 = new OperacaoConta(null, "Futebol", LocalDateTime.of(2022, 12, 28, 14, 33, 48, 000000), LocalDateTime.of(2022, 12, 28, 14, 33, 48, 000000), 'R', 20.00,
				EstadoPagamento.PENDENTE, con1, Categoria.LAZER);

		OperacaoConta op24 = new OperacaoConta(null,"Futebol", LocalDateTime.of(2022, 12, 28, 14, 33, 48, 000000), LocalDateTime.of(2022, 12, 28, 14, 33, 48, 000000), 'D', 20.00,
				EstadoPagamento.PENDENTE, con1, Categoria.FIXO);

//		OperacaoConta op3 = new OperacaoConta(null, "Futebol",LocalDateTime.now(), LocalDateTime.now(), 'D', 20.00,
//				EstadoPagamento.PENDENTE, con1, Categoria.ALIMENTACAO);
//		OperacaoConta op7 = new OperacaoConta(null, "Futebol",LocalDateTime.now(), LocalDateTime.now(), 'D', 20.00,
//				EstadoPagamento.PENDENTE, con1, Categoria.ALIMENTACAO);
//		
//		OperacaoConta op8 = new OperacaoConta(null, "Futebol",LocalDateTime.now(), LocalDateTime.now(), 'D', 20.00,
//				EstadoPagamento.PENDENTE, con1, Categoria.ALIMENTACAO);
//		
//		OperacaoConta op9 = new OperacaoConta(null, "Futebol",LocalDateTime.now(), LocalDateTime.now(), 'D', 20.00,
//				EstadoPagamento.PENDENTE, con1, Categoria.ALIMENTACAO);
//
//		OperacaoConta op4 = new OperacaoConta(null, "teste", LocalDateTime.now(), LocalDateTime.now(), 'R', 40.00,
//				EstadoPagamento.PENDENTE, con2,  Categoria.GERAL);
//
//		OperacaoConta op5 = new OperacaoConta(null, "pix jantar",LocalDateTime.now(), LocalDateTime.now(), 'R', 40.00,
//				EstadoPagamento.PENDENTE, con2, Categoria.ALIMENTACAO);
//
//		OperacaoConta op6 = new OperacaoConta(null, "pix estacionamento",LocalDateTime.now(), LocalDateTime.now(), 'D', 40.00,
//				EstadoPagamento.PENDENTE, con2, Categoria.GERAL);

		clienteRepository.saveAll(Arrays.asList(cli1, cli2, cli3));
		contaRepository.saveAll(Arrays.asList(con1, con2, con3, con4));
		operacaoContaRepository.saveAll(Arrays.asList(op1, op2, op3, op4, op5, op6, op7, op8, op9,op10,op11,op12,op13,op14,op15,op16,op17,op18,op19,op20,op21,op22,op23,op24));

	}
}
