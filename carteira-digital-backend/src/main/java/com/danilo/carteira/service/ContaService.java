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
import com.danilo.carteira.domain.Cliente;
import com.danilo.carteira.domain.Conta;
import com.danilo.carteira.domain.OperacaoConta;
import com.danilo.carteira.repository.ContaRepository;
import com.danilo.carteira.service.exceptions.AuthorizationException;

@Service
public class ContaService {
	
	@Autowired
	private ContaRepository repository;
	@Autowired
	private ClienteService clienteService;
	
	public List<Conta> listarTodos(){
		List<Conta> obj = repository.findAll();
		return obj;
	}
	
	public Conta buscarId(Long id) {
		UserSS user = UserService.authenticated();
		if(id != user.getId()) {
			throw new AuthorizationException("Acesso Negado");
		}
		Optional<Conta> conta = repository.findById(id);
		return conta.orElseThrow(() -> new ObjectNotFoundException(
				"A busca da Conta Id: " + id + " não retornou resultados, Tipo : " + OperacaoConta.class.getName(), null));
	}
	
	public Conta alterarConta(Conta obj) {
		UserSS user = UserService.authenticated();
		if(obj.getCliente().getId() != user.getId()) {
			throw new AuthorizationException("Acesso Negado");
		}
		Conta newObj = buscarId(user.getId());
		updateData(newObj, obj);
		return repository.save(newObj);
	}
	
	public void deletarConta(Long id) {
		UserSS user = UserService.authenticated();
		Conta conta = buscarId(id);
		if(conta.getCliente().getId() != user.getId()) {
			throw new AuthorizationException("Acesso Negado");
		}
		repository.deleteById(id);
	}
	
	public Conta inserirConta(Conta conta) {
		return repository.save(conta);
	}

	public void updateValor(Long id, OperacaoConta op) {
		Conta conta = buscarId(id);
		 Double saldo = conta.getSaldo();
		 Double despesa = conta.getDespesas();
		 Double receita = conta.getReceitas();
		if(op != null) { 
			if(op.getEstadoPagamento().getDescricao().equals("Quitado")) {
				if( Character.toLowerCase(op.getTpOperacao()) == 'r') {
					 receita += op.getValor();
					 saldo += op.getValor();
					 conta.setReceitas(receita);
				 }else if( Character.toLowerCase(op.getTpOperacao()) == 'd') {
					 despesa -= op.getValor();
					 saldo -= op.getValor();
					 conta.setDespesas(despesa);
				 }
			}else if(op.getEstadoPagamento().getDescricao().equals("Pendente")) {
				if( Character.toLowerCase(op.getTpOperacao()) == 'r') {
					 receita += op.getValor();
					 conta.setReceitas(receita);
				 }else if( Character.toLowerCase(op.getTpOperacao()) == 'd') {
					 despesa -= op.getValor();
					 conta.setDespesas(despesa);
				 }
			}else if(op.getEstadoPagamento().getDescricao().equals("Cancelado") && op.getConta().getId() == conta.getId()) {
				if( Character.toLowerCase(op.getTpOperacao()) == 'r' ) {
					 receita -= op.getValor();
					 conta.setReceitas(receita);
				 }else if( Character.toLowerCase(op.getTpOperacao()) == 'd') {
					 despesa += op.getValor();
					 conta.setDespesas(despesa);
				 }
			}	
			 conta.setSaldo(saldo);	 
		}
	}
	
	
	public Page<Conta> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
				
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.buscarId(user.getId());
		
		return repository.findByCliente(cliente, pageRequest);
	}
	
	private void updateData(Conta newObj, Conta obj) {
		newObj.setInstituicão(obj.getInstituicao());
	}
}
