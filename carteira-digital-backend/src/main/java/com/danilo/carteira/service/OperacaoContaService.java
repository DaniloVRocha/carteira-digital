package com.danilo.carteira.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danilo.carteira.domain.OperacaoConta;
import com.danilo.carteira.repository.OperacaoContaRepository;

@Service
public class OperacaoContaService {
	
	@Autowired
	private OperacaoContaRepository repository;
	
	public List<OperacaoConta> listarTodos(){
		List<OperacaoConta> obj = repository.findAll();
		return obj;
	}
	
	public OperacaoConta buscarId(Long id) {
		Optional<OperacaoConta> op = repository.findById(id);
		return op.orElseThrow(() -> new ObjectNotFoundException(
				"A busca do Id: " + id + " não retornou resultados, Tipo : " + OperacaoConta.class.getName(), null));
	}
	
	public OperacaoConta inserirOperacao(OperacaoConta oc) {
		repository.save(oc);
		return oc;
	}
}
