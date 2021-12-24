package com.danilo.carteira.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danilo.carteira.config.security.UserSS;
import com.danilo.carteira.domain.Conta;
import com.danilo.carteira.domain.OperacaoConta;
import com.danilo.carteira.domain.enums.EstadoPagamento;
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
	
	public void deletarOperacao(Long id) {
		UserSS user = UserService.authenticated();
		OperacaoConta con = buscarId(id);
		if( con.getConta().getCliente().getId() != user.getId()) {
			throw new AuthorizationException("Acesso Negado");
		}
		repository.deleteById(id);
	}
	
	@Transactional
	public OperacaoConta inserirOperacao(OperacaoConta oc) {
		UserSS user = UserService.authenticated();
		Conta con = contaService.buscarId(oc.getConta().getId());
		if(con.getCliente().getId() != user.getId()) {
			throw new AuthorizationException("Acesso Negado");
		}
		contaService.updateValor(oc.getConta().getId(), oc);
		repository.save(oc);
		return oc;
	}
	
	public void ajustarSaldo(Double novoSaldo, Long id) {
		UserSS user = UserService.authenticated();
		Conta con = contaService.buscarId(id);
		if(con.getCliente().getId() != user.getId()) {
			throw new AuthorizationException("Acesso Negado");
		}
	
		double diferenca = con.getSaldo() - novoSaldo;
		OperacaoConta op1 = new OperacaoConta(null, LocalDateTime.now(),LocalDateTime.now(), 'R', diferenca , EstadoPagamento.QUITADO, con, "Ajuste de Saldo");
		if(diferenca > 0) {
			op1.setTpOperacao('D');
		}
		inserirOperacao(op1);
	}
	
	public Page<OperacaoConta> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Conta conta = contaService.buscarId(user.getId());
		return repository.findByConta(conta, pageRequest);
	}
	
}
