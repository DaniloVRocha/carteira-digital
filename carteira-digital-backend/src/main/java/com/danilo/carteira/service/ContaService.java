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
import com.danilo.carteira.dto.OperacaoContaDTO;
import com.danilo.carteira.dto.request.ContaEdicaoRequest;
import com.danilo.carteira.dto.response.ContaValoresResponse;
import com.danilo.carteira.repository.ContaRepository;
import com.danilo.carteira.service.exceptions.AuthorizationException;

@Service
public class ContaService {

	@Autowired
	private ContaRepository repository;
	@Autowired
	private ClienteService clienteService;

	public Conta buscarId(Long id) {
		UserSS user = UserService.authenticated();
		if (id != user.getId()) {
			throw new AuthorizationException("Acesso Negado");
		}
		Optional<Conta> conta = repository.findById(id);
		return conta.orElseThrow(() -> new ObjectNotFoundException(
				"A busca da Conta Id: " + id + " não retornou resultados, Tipo : " + OperacaoConta.class.getName(),
				null));
	}
	
	public List<Conta> buscarPorIdCliente() {
		UserSS user = UserService.authenticated();
		List<Conta> contas = repository.findContasByIdCliente(user.getId());
		return contas;
	}
	
	public Integer buscarQuantidadeDeOperacaoPorConta(char tpOperacao, Long idConta){
		Integer quantidadeOperacoes = repository.numeroOperacoesPorConta(tpOperacao, idConta);
		return quantidadeOperacoes;
	}

	public ContaValoresResponse atualizarPreencherSaldo() {
		UserSS user = UserService.authenticated();
		Double saldoTotal = 0.0;
		Double despesaTotal = 0.0;
		Double receitaTotal = 0.0;

		List<Conta> contas = repository.findContasByIdCliente(user.getId());

		for (Conta conta : contas) {

			saldoTotal += conta.getSaldo();
			despesaTotal += conta.getDespesas();
			receitaTotal += conta.getReceitas();

		}

		ContaValoresResponse conta = new ContaValoresResponse(saldoTotal, despesaTotal, receitaTotal);
		return conta;
	}
	
	public ContaValoresResponse calcularTotalOperacoesMes(List<OperacaoContaDTO> operacoesMes) {
		Double saldoTotal = 0.0;
		Double despesaTotal = 0.0;
		Double receitaTotal = 0.0;

		for (OperacaoContaDTO operacao : operacoesMes) {
			if (Character.toLowerCase(operacao.getTpOperacao()) == 'r') {
				receitaTotal += operacao.getValor();
				saldoTotal += operacao.getValor();
			} else if (Character.toLowerCase(operacao.getTpOperacao()) == 'd') {
				despesaTotal += operacao.getValor();
				saldoTotal -= operacao.getValor();
			}			
		}

		ContaValoresResponse conta = new ContaValoresResponse(saldoTotal, despesaTotal, receitaTotal);
		return conta;
	}
	
	public List<Conta> buscarContasCliente() {
		UserSS user = UserService.authenticated();

		List<Conta> contas = repository.findContasByIdCliente(user.getId());
		
		return contas;
	}

	public Conta alterarConta(ContaEdicaoRequest obj, Long id) {
		List<Conta> contas = buscarPorIdCliente();
		
		for(Conta conta: contas) {
			if(conta.getId() == id) {
				Conta newObj = conta;
				newObj.setInstituicão(obj.getInstituicao());
				newObj.setMostrarTelaInicial(obj.getMostrarTelaInicial());
				return repository.save(newObj);
			}
		}
		return null;
	}

	public void deletarConta(Long id) {
		UserSS user = UserService.authenticated();
		Conta conta = buscarId(id);
		if (conta.getCliente().getId() != user.getId()) {
			throw new AuthorizationException("Acesso Negado");
		}
		repository.deleteById(id);
	}

	public Conta inserirConta(Conta conta) {
		UserSS user = UserService.authenticated();
		Cliente cliente =  clienteService.buscarId(user.getId());
		conta.setCliente(cliente);
		conta.setMostrarTelaInicial(true);
		return repository.save(conta);
	}

	public void updateValorInsert(Long id, OperacaoConta op) {
		Conta conta = buscarId(id);
		Double saldo = conta.getSaldo();
		Double despesa = conta.getDespesas();
		Double receita = conta.getReceitas();
		if (op != null) {
			if (op.getEstadoPagamento().getDescricao().equals("Quitado")) {
				if (Character.toLowerCase(op.getTpOperacao()) == 'r') {
					receita += op.getValor();
					saldo += op.getValor();
					conta.setReceitas(receita);
				} else if (Character.toLowerCase(op.getTpOperacao()) == 'd') {
					despesa -= op.getValor();
					saldo -= op.getValor();
					conta.setDespesas(despesa);
				}
			} 
//			else if (op.getEstadoPagamento().getDescricao().equals("Pendente")) {
//				if (Character.toLowerCase(op.getTpOperacao()) == 'r') {
//					receita += op.getValor();
//					conta.setReceitas(receita);
//				} else if (Character.toLowerCase(op.getTpOperacao()) == 'd') {
//					despesa -= op.getValor();
//					conta.setDespesas(despesa);
//				}
//			}
			conta.setSaldo(saldo);
		}
	}

	public void updateValorDelete(Long id, OperacaoConta op) {
		Conta conta = buscarId(id);
		Double saldo = conta.getSaldo();
		Double despesa = conta.getDespesas();
		Double receita = conta.getReceitas();
		if (op != null) {
			if (op.getEstadoPagamento().getDescricao().equals("Quitado")) {
				if (Character.toLowerCase(op.getTpOperacao()) == 'r') {
					receita -= op.getValor();
					saldo -= op.getValor();
					conta.setReceitas(receita);
				} else if (Character.toLowerCase(op.getTpOperacao()) == 'd') {
					despesa += op.getValor();
					saldo += op.getValor();
					conta.setDespesas(despesa);
				}
//			} else if (op.getEstadoPagamento().getDescricao().equals("Pendente")) {
//				if (Character.toLowerCase(op.getTpOperacao()) == 'r') {
//					receita -= op.getValor();
//					conta.setReceitas(receita);
//				} else if (Character.toLowerCase(op.getTpOperacao()) == 'd') {
//					despesa += op.getValor();
//					conta.setDespesas(despesa);
//				}
//			}
				conta.setSaldo(saldo);
			}
		}
	}

	public Page<Conta> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.buscarId(user.getId());

		return repository.findByCliente(cliente, pageRequest);
	}

}
