package com.danilo.carteira.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.danilo.carteira.domain.enums.EstadoPagamento;
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
import com.danilo.carteira.dto.request.ContaRequest;
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
		if (!verificarIdConta(id, user.getId())) {
			throw new AuthorizationException("Acesso Negado");
		}
		Optional<Conta> conta = repository.findById(id);
		return conta.orElseThrow(() -> new ObjectNotFoundException(
				"A busca da Conta Id: " + id + " não retornou resultados, Tipo : " + OperacaoConta.class.getName(),
				null));
	}
	
	public boolean verificarIdConta(Long idConta, Long idUsuario) {
		List<Conta> contasCliente = repository.findContasByIdCliente(idUsuario);
	
		for(Conta conta : contasCliente) {
			if(conta.getId().equals(idConta)) {
				return true;
			};
		}
		return false;
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
		BigDecimal saldoTotal = BigDecimal.ZERO;
		BigDecimal despesaTotal = BigDecimal.ZERO;
		BigDecimal receitaTotal = BigDecimal.ZERO;

		List<Conta> contas = repository.findContasByIdCliente(user.getId());

		for (Conta conta : contas) {

			saldoTotal = saldoTotal.add(conta.getSaldo());
			despesaTotal = despesaTotal.add(conta.getDespesas());
			receitaTotal = receitaTotal.add(conta.getReceitas());

		}

		ContaValoresResponse conta = new ContaValoresResponse(saldoTotal, despesaTotal, receitaTotal);
		return conta;
	}
	
	public ContaValoresResponse calcularTotalOperacoesMes(List<OperacaoContaDTO> operacoesMes) {
		BigDecimal saldoTotal = BigDecimal.ZERO;
		BigDecimal despesaTotal = BigDecimal.ZERO;
		BigDecimal receitaTotal = BigDecimal.ZERO;

		for (OperacaoContaDTO operacao : operacoesMes) {
			if (Character.toLowerCase(operacao.getTpOperacao()) == 'r') {
				receitaTotal = receitaTotal.add(operacao.getValor());
				saldoTotal = saldoTotal.add(operacao.getValor());
			} else if (Character.toLowerCase(operacao.getTpOperacao()) == 'd') {
				despesaTotal = despesaTotal.subtract(operacao.getValor());
				saldoTotal = saldoTotal.subtract(operacao.getValor());
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

	public Conta alterarConta(ContaRequest obj, Long id) {
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
		conta.setDespesas(BigDecimal.ZERO);
		conta.setSaldo(BigDecimal.ZERO);
		conta.setReceitas(BigDecimal.ZERO);
		conta.setCliente(cliente);
		conta.setMostrarTelaInicial(true);
		return repository.save(conta);
	}

	public void updateValorInsert(Long id, OperacaoConta op) {
		Conta conta = buscarId(id);
		BigDecimal saldo = conta.getSaldo();
		BigDecimal despesa = conta.getDespesas();
		BigDecimal receita = conta.getReceitas();
		if (op != null) {
			if (op.getEstadoPagamento().getDescricao().equals("Quitado")) {
				if (Character.toLowerCase(op.getTpOperacao()) == 'r') {
					receita = receita.add(op.getValor());
					saldo = saldo.add(op.getValor());
					conta.setReceitas(receita);
				} else if (Character.toLowerCase(op.getTpOperacao()) == 'd') {
					despesa = despesa.subtract(op.getValor());
					saldo = saldo.subtract(op.getValor());
					conta.setDespesas(despesa);
				}
			}
			conta.setSaldo(saldo);
			repository.save(conta);
		}
	}

	public void updateValorDelete(Long id, OperacaoConta op, String tipoUpdate) {
		Conta conta = buscarId(id);
		BigDecimal saldo = conta.getSaldo();
		BigDecimal despesa = conta.getDespesas();
		BigDecimal receita = conta.getReceitas();
		if (op != null) {
			if (op.getEstadoPagamento().getDescricao().equals("Pendente") || tipoUpdate.equals("deletar")) {
				if (Character.toLowerCase(op.getTpOperacao()) == 'r') {
					receita = receita.subtract(op.getValor());
					saldo = saldo.subtract(op.getValor());
					conta.setReceitas(receita);
				} else if (Character.toLowerCase(op.getTpOperacao()) == 'd') {
					despesa = despesa.add(op.getValor());
					saldo = saldo.add(op.getValor());
					conta.setDespesas(despesa);
				}
				conta.setSaldo(saldo);
				repository.save(conta);
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
