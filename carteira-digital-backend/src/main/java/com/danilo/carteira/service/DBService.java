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
import com.danilo.carteira.domain.enums.Perfil;
import com.danilo.carteira.domain.enums.TipoCliente;
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
		
		Cliente cli1 = new Cliente(null, "Danilo", "00000000000", "danilo@gmail.com", pe.encode("testesenha"),
				TipoCliente.PESSOAFISICA, true, "Observação Cliente");
		cli1.addPerfil(Perfil.ADMIN);

		Cliente cli2 = new Cliente(null, "James Gosling", "00000000001", "java@gmail.com", pe.encode("java123"),
				TipoCliente.PESSOAJURIDICA, true, "Use Java");

		Cliente cli3 = new Cliente(null, "Ana Costa", "00000000022", "ana@gmail.com", pe.encode("ana123"),
				TipoCliente.PESSOAFISICA, true, "Cadastro Ana Costa");

		Conta con1 = new Conta(null, "Itau", 900.00, cli1);
		Conta con4 = new Conta(null, "NuBank", 800.00, cli2);
		Conta con2 = new Conta(null, "Bradesco", 20.00, cli2);
		Conta con3 = new Conta(null, "Itau", 2000.00, cli3);

		OperacaoConta op1 = new OperacaoConta(null, LocalDateTime.now(), LocalDateTime.now(), 'R', 20.00,
				EstadoPagamento.QUITADO, con1, "Futebol", Categoria.LAZER);

		OperacaoConta op2 = new OperacaoConta(null, LocalDateTime.now(), LocalDateTime.now(), 'R', 20.00,
				EstadoPagamento.QUITADO, con1, "Futebol", Categoria.LAZER);

		OperacaoConta op3 = new OperacaoConta(null, LocalDateTime.now(), LocalDateTime.now(), 'R', 20.00,
				EstadoPagamento.QUITADO, con1, "Futebol", Categoria.LAZER);

		OperacaoConta op4 = new OperacaoConta(null, LocalDateTime.now(), LocalDateTime.now(), 'R', 40.00,
				EstadoPagamento.QUITADO, con2, "teste", Categoria.GERAL);

		OperacaoConta op5 = new OperacaoConta(null, LocalDateTime.now(), LocalDateTime.now(), 'R', 40.00,
				EstadoPagamento.QUITADO, con2, "pix jantar", Categoria.ALIMENTACAO);

		OperacaoConta op6 = new OperacaoConta(null, LocalDateTime.now(), LocalDateTime.now(), 'D', 40.00,
				EstadoPagamento.QUITADO, con2, "pix estacionamento", Categoria.GERAL);

		clienteRepository.saveAll(Arrays.asList(cli1, cli2, cli3));
		contaRepository.saveAll(Arrays.asList(con1, con2, con3, con4));
		operacaoContaRepository.saveAll(Arrays.asList(op1, op2, op3, op4, op5, op6));

	}
}