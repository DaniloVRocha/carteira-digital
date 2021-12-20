package com.danilo.carteira.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.danilo.carteira.config.security.UserSS;
import com.danilo.carteira.domain.Conta;
import com.danilo.carteira.domain.OperacaoConta;
import com.danilo.carteira.repository.OperacaoContaRepository;
import com.danilo.carteira.service.exceptions.AuthorizationException;

@Service
public class OperacaoContaService {
	
	@Autowired
	private OperacaoContaRepository repository;
	@Autowired
	private ContaService contaService;
	
	public List<OperacaoConta> listarTodos(){
		List<OperacaoConta> obj = repository.findAll();
		return obj;
	}
	
	public OperacaoConta buscarId(Long id) {
		UserSS user = UserService.authenticated();
		Optional<OperacaoConta> op = repository.findById(id);
		
		if(user.getId() == op.get().getConta().getCliente().getId()) {
			return op.orElseThrow(() -> new ObjectNotFoundException(
					"A busca do Id: " + id + " não retornou resultados, Tipo : " + OperacaoConta.class.getName(), null));
		}else {
			throw new AuthorizationException("Acesso Negado");
		}
	}
	
	public OperacaoConta inserirOperacao(OperacaoConta oc) {
		UserSS user = UserService.authenticated();
		if(oc.getConta().getCliente().getId() != user.getId()) {
			throw new AuthorizationException("Acesso Negado");
		}
		repository.save(oc);
		return oc;
	}
	
	public Page<OperacaoConta> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Conta conta = contaService.buscarId(user.getId());
		return repository.findByConta(conta, pageRequest);
	}
	
}
