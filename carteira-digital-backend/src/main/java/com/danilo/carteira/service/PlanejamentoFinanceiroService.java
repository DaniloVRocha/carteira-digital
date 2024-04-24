package com.danilo.carteira.service;

import com.danilo.carteira.config.security.UserSS;
import com.danilo.carteira.domain.Cliente;
import com.danilo.carteira.domain.Conta;
import com.danilo.carteira.domain.OperacaoConta;
import com.danilo.carteira.domain.PlanejamentoFinanceiro;
import com.danilo.carteira.dto.OperacaoContaDTO;
import com.danilo.carteira.dto.request.ContaRequest;
import com.danilo.carteira.dto.response.ContaValoresResponse;
import com.danilo.carteira.repository.ContaRepository;
import com.danilo.carteira.repository.PlanejamentoFinanceiroRepository;
import com.danilo.carteira.service.exceptions.AuthorizationException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PlanejamentoFinanceiroService {

	@Autowired
	private PlanejamentoFinanceiroRepository repository;
	@Autowired
	private ClienteService clienteService;

	public List<PlanejamentoFinanceiro> listarTodos(){
		return repository.findAll();
	}

	public PlanejamentoFinanceiro inserirPlanejamento(PlanejamentoFinanceiro planejamento) {
		return repository.save(planejamento);
	}
}
