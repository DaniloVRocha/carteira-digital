package com.danilo.carteira.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import com.danilo.carteira.domain.enums.Categoria;
import com.danilo.carteira.domain.enums.EstadoPagamento;
import com.danilo.carteira.dto.ContaTransferenciaDTO;
import com.danilo.carteira.repository.OperacaoContaRepository;
import com.danilo.carteira.service.exceptions.AuthorizationException;
import com.danilo.carteira.service.exceptions.OperacaoNaoEncontradaException;

@Service
public class OperacaoContaService {

	@Autowired
	private OperacaoContaRepository repository;
	@Autowired
	private ContaService contaService;

	public List<OperacaoConta> listarTodos() {
		List<OperacaoConta> obj = repository.findAll();
		return obj;
	}

	public OperacaoConta buscarId(Long id) {
		UserSS user = UserService.authenticated();
		Optional<OperacaoConta> oc = repository.findById(id);

		if (user.getId() == oc.get().getConta().getCliente().getId()) {
			return oc.orElseThrow(() -> new ObjectNotFoundException(
					"A busca do Id: " + id + " não retornou resultados, Tipo : " + OperacaoConta.class.getName(),
					null));
		} else {
			throw new AuthorizationException("Acesso Negado");
		}
	}

	public void deletarOperacao(Long id) {
		UserSS user = UserService.authenticated();
		OperacaoConta oc = buscarId(id);
		if (oc.getConta().getCliente().getId() != user.getId()) {
			throw new AuthorizationException("Acesso Negado");
		}
		contaService.updateValorDelete(oc.getConta().getId(), oc);
		repository.deleteById(id);
	}

	@Transactional
	public OperacaoConta inserirOperacao(OperacaoConta oc) {
		UserSS user = UserService.authenticated();
		Conta con = contaService.buscarId(oc.getConta().getId());
		if (con.getCliente().getId() != user.getId()) {
			throw new AuthorizationException("Acesso Negado");
		}
		contaService.updateValorInsert(oc.getConta().getId(), oc);
		repository.save(oc);
		return oc;
	}

	public void ajustarSaldo(Double novoSaldo, Long id) {
		UserSS user = UserService.authenticated();
		Conta con = contaService.buscarId(id);
		if (con.getCliente().getId() != user.getId()) {
			throw new AuthorizationException("Acesso Negado");
		}

		double diferenca = con.getSaldo() - novoSaldo;
		OperacaoConta oc1 = new OperacaoConta(null, LocalDateTime.now(), LocalDateTime.now(), 'R', diferenca,
				EstadoPagamento.QUITADO, con, "Ajuste de Saldo", Categoria.AJUSTE);
		if (diferenca > 0) {
			oc1.setTpOperacao('D');
		}
		inserirOperacao(oc1);
	}
	
	public void transferenciaEntreContas(ContaTransferenciaDTO conta) {
		Conta contaOrigem = contaService.buscarId(conta.getIdOrigem());
		Conta contaDestino = contaService.buscarId(conta.getIdDestino());
		boolean id = contaOrigem.getCliente().getId() == contaDestino.getCliente().getId();
		if(!id) {
			throw new AuthorizationException("Só é possivel fazer transações entre suas contas");
		}
		
		UserSS user = UserService.authenticated();
		if (contaOrigem.getCliente().getId() != user.getId()) {
			throw new AuthorizationException("Acesso Negado");
		}
        
		OperacaoConta op1 = new OperacaoConta();
		op1.setConta(contaOrigem);
		op1.setDataHora(LocalDateTime.now());
		op1.setEstadoPagamento(EstadoPagamento.QUITADO);
		op1.setTpOperacao('D');
		op1.setValor(conta.getValor());
		op1.setObservacao("Transferencia entre contas" );
		op1.setVencimento(LocalDateTime.now());
		
		OperacaoConta op2 = new OperacaoConta();
		op2.setConta(contaDestino);
		op2.setDataHora(LocalDateTime.now());
		op2.setEstadoPagamento(EstadoPagamento.QUITADO);
		op2.setTpOperacao('R');
		op2.setValor(conta.getValor());
		op2.setObservacao("Transferencia entre contas" );
		op2.setVencimento(LocalDateTime.now());
		
		inserirOperacao(op1);
		inserirOperacao(op2);
	}
	
	public List<OperacaoConta> consultarOperacoesPorData(Long id, String dataInicio, String dataFinal) throws Exception {
		Conta c = contaService.buscarId(id);
		List<OperacaoConta> operacoes = repository.findOperacaoByConta(c);
		List<OperacaoConta> aux = new ArrayList<>();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dataInicioBusca = LocalDateTime.parse(dataInicio, format);
		LocalDateTime dataFimBusca = LocalDateTime.parse(dataFinal, format);
		
		for (OperacaoConta operacao: operacoes) {
			if(operacao.getDataHora().isAfter(dataInicioBusca) && operacao.getDataHora().isBefore(dataFimBusca)) {
				aux.add(operacao);
			}
		}
		return aux;
	}

	@Transactional
	public OperacaoConta alterarOperacao(OperacaoConta obj, Long id) throws OperacaoNaoEncontradaException {
		UserSS user = UserService.authenticated();
		OperacaoConta oc = verificarExistencia(id);
		if (oc.getConta().getCliente().getId() != user.getId()) {
			throw new AuthorizationException("Acesso Negado");
		}
		obj.setId(id);
		contaService.updateValorDelete(oc.getConta().getId(), oc);
		oc = repository.save(obj);
		contaService.updateValorInsert(obj.getConta().getId(), obj);
		return oc;
	}

	private OperacaoConta verificarExistencia(Long id) throws OperacaoNaoEncontradaException {
		return repository.findById(id)
				.orElseThrow(() -> new OperacaoNaoEncontradaException("Operação não encontrada."));
	}

	public Page<OperacaoConta> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Conta conta = contaService.buscarId(user.getId());
		return repository.findByConta(conta, pageRequest);
	}

}
