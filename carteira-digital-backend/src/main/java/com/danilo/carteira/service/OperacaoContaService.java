package com.danilo.carteira.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.danilo.carteira.dto.OperacaoContaDTO;
import com.danilo.carteira.repository.OperacaoContaRepository;
import com.danilo.carteira.service.exceptions.AuthorizationException;
import com.danilo.carteira.service.exceptions.OperacaoNaoEncontradaException;

@Service
public class OperacaoContaService {

	@Autowired
	private OperacaoContaRepository repository;
	@Autowired
	private ContaService contaService;
	
	
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
	
	public HashMap<String, Integer> buscarTodosPorIdCliente(Integer numeroMes, Integer numeroAno) {
		UserSS user = UserService.authenticated();
		
		HashMap<String, Integer> listaExistentes = new HashMap<String, Integer>();
		
		List<OperacaoConta> operacoes = repository.buscarOperacoesPorIdCliente(user.getId(), numeroMes, numeroAno);

		for(OperacaoConta op : operacoes) {
			Integer quantidade = repository.numeroOperacoesPorCategoria(user.getId(), numeroMes, numeroAno, op.getCategoria().getCod());
			listaExistentes.put(op.getCategoria().getDescricao(), quantidade);
		}
		
		return listaExistentes;
		
	}
	
	public List<Integer> buscaQuantidadeDeOperacoesPorConta(Long id){
		//TODO IMPLEMENTAR VERIFICAÇÃO DE SEGURANÇA
		List<Integer> quantidadeOperacoes = new ArrayList<Integer>();
		Integer quantidadeReceita = contaService.buscarQuantidadeDeOperacaoPorConta('R', id);
		Integer quantidadeDespesa = contaService.buscarQuantidadeDeOperacaoPorConta('D', id);
		quantidadeOperacoes.add(quantidadeReceita);
		quantidadeOperacoes.add(quantidadeDespesa);
		
		return quantidadeOperacoes;
	}

	public void deletarOperacao(Long id) {
		UserSS user = UserService.authenticated();
		OperacaoConta oc = buscarId(id);
		if (oc.getConta().getCliente().getId() != user.getId()) {
			throw new AuthorizationException("Acesso Negado");
		}
		try{
			repository.deleteById(id);
			contaService.updateValorDelete(oc.getConta().getId(), oc, "deletar");
		}catch (RuntimeException e ){
			System.out.println("Erro ao deletar conta");
		}
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

	public void ajustarSaldo(BigDecimal novoSaldo, Long id) {
		UserSS user = UserService.authenticated();
		Conta con = contaService.buscarId(id);
		if (con.getCliente().getId() != user.getId()) {
			throw new AuthorizationException("Acesso Negado");
		}

		BigDecimal diferenca = con.getSaldo().subtract(novoSaldo);
		OperacaoConta oc1 = new OperacaoConta(null, "Ajuste de Saldo", LocalDateTime.now(), LocalDateTime.now(), 'R', diferenca,
				EstadoPagamento.QUITADO, con, Categoria.AJUSTE);
		if (diferenca.compareTo(BigDecimal.ZERO) > 0) {
			oc1.setTpOperacao('D');
		}
		inserirOperacao(oc1);
	}
	
	public List<BigDecimal> gastoPorMes(String dataInicio, String dataFinal) throws Exception {
		List<OperacaoContaDTO> operacoes = consultarOperacoesPorData(dataInicio, dataFinal);
		BigDecimal gastoMes = BigDecimal.ZERO;
		BigDecimal receitaMes = BigDecimal.ZERO;
		
		for(OperacaoContaDTO operacao: operacoes) {
			if(operacao.getEstadoPagamento().equals(EstadoPagamento.QUITADO) && operacao.getTpOperacao() == 'D') {
				gastoMes = gastoMes.add(operacao.getValor());
			}else if(operacao.getEstadoPagamento().equals(EstadoPagamento.QUITADO) && operacao.getTpOperacao() == 'R') {
				receitaMes = receitaMes.add(operacao.getValor());
			}
		}
		
		List<BigDecimal> resultado = new ArrayList<BigDecimal>();
		resultado.add(gastoMes);
		resultado.add(receitaMes);
		
		return resultado;	
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
		op1.setNome("Transferencia entre contas" );
		op1.setVencimento(LocalDateTime.now());
		
		OperacaoConta op2 = new OperacaoConta();
		op2.setConta(contaDestino);
		op2.setDataHora(LocalDateTime.now());
		op2.setEstadoPagamento(EstadoPagamento.QUITADO);
		op2.setTpOperacao('R');
		op2.setValor(conta.getValor());
		op2.setNome("Transferencia entre contas" );
		op2.setVencimento(LocalDateTime.now());
		
		inserirOperacao(op1);
		inserirOperacao(op2);
	}
	public List<OperacaoContaDTO> consultarOperacoesPorData(String dataInicio, String dataFinal) throws Exception {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dataInicioConvertida = LocalDate.parse(dataInicio);
		LocalDate dataFimConvertida = LocalDate.parse(dataFinal);

		LocalDateTime dataInicioBusca = dataInicioConvertida.atStartOfDay();
		LocalDateTime dataFimBusca = dataFimConvertida.atStartOfDay();

		List<OperacaoContaDTO> auxDTO = new ArrayList<>();
		
		List<Conta> contas = contaService.buscarContasCliente();
		for(Conta conta: contas) {
			List<OperacaoConta> operacoes = repository.findOperacaoByDate(conta.getId(),dataInicioBusca, dataFimBusca);
			for (OperacaoConta operacao: operacoes) {
				OperacaoContaDTO opDTO = new OperacaoContaDTO(operacao);
				auxDTO.add(opDTO);
			}
		}
		return auxDTO;
	}
	
	public List<OperacaoContaDTO> consultarOperacoesPorMesAno(Integer numeroMes, Integer numeroAno) throws Exception {
		List<Conta> contas = contaService.buscarContasCliente();
		List<OperacaoContaDTO> auxDTO = new ArrayList<>();
		for(Conta conta: contas) {
			List<OperacaoConta> operacoes = repository.findOperacaoByMesAno(conta.getId(),numeroMes, numeroAno);
			for (OperacaoConta operacao: operacoes) {
				OperacaoContaDTO opDTO = new OperacaoContaDTO(operacao);
				auxDTO.add(opDTO);
			}
		}
		return auxDTO;
	}
	
	public List<Map<String,Double>> consultarOperacoesPorAno() throws Exception {
		LocalDateTime dataAtual = LocalDateTime.now();
		List<Conta> contas = contaService.buscarContasCliente();
		List<Map<String,Double>> retorno = new ArrayList<Map<String,Double>>();
		HashMap<String, Double> receitaMês = new HashMap<String, Double>();
		HashMap<String, Double> despesaMês = new HashMap<String, Double>();
		
		for(Conta conta: contas) {
			for(int i=0;i<12;i++){
				Double somaReceitasMêsAno = repository.findValoresOperacoesMes(conta.getId(),(i+1),dataAtual.getYear(),'R');
				Double somaDespesasMêsAno = repository.findValoresOperacoesMes(conta.getId(),(i+1),dataAtual.getYear(),'D');
				
				if(null != somaReceitasMêsAno) {
					receitaMês.put(selecionaMes(i+1), somaReceitasMêsAno);
				}else {
					receitaMês.put(selecionaMes(i+1), 0.00);
				}
				if(null != somaDespesasMêsAno) {
					despesaMês.put(selecionaMes(i+1), somaDespesasMêsAno);
				}else {
					despesaMês.put(selecionaMes(i+1), 0.00);
				}
				
			}
		
			retorno.add(receitaMês);
			retorno.add(despesaMês);
		}
		return retorno;
	}	

	
	public List<OperacaoContaDTO> consultarOperacoesVencidas() throws Exception {
		LocalDateTime dataAtual = LocalDateTime.now();
		List<OperacaoContaDTO> aux = new ArrayList<>();
		
		List<Conta> contas = contaService.buscarContasCliente();
		for(Conta conta: contas) {
			List<OperacaoConta> operacoes = repository.findOperacaoVencidas(conta.getId(),dataAtual);
			for (OperacaoConta operacao: operacoes) {
				OperacaoContaDTO opDTO = new OperacaoContaDTO(operacao);
				aux.add(opDTO);
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
		contaService.updateValorDelete(oc.getConta().getId(), oc, "alterar");
		obj.setDataHora(LocalDateTime.now());
		oc = repository.save(obj);
		contaService.updateValorInsert(obj.getConta().getId(), obj);
		return oc;
	}
	
	@Transactional
	public void alterarEstadoPagamento(Long idOperacao) {
		UserSS user = UserService.authenticated();
		OperacaoConta oc = verificarExistencia(idOperacao);
		if (oc.getConta().getCliente().getId() != user.getId()) {
			throw new AuthorizationException("Acesso Negado");
		}
		if(oc.getEstadoPagamento().equals(EstadoPagamento.PENDENTE)) {
			repository.informarPagamento(idOperacao, EstadoPagamento.QUITADO);
			oc.setEstadoPagamento(EstadoPagamento.QUITADO);
			contaService.updateValorInsert(oc.getConta().getId(), oc);
		}else {
			repository.informarPagamento(idOperacao, EstadoPagamento.PENDENTE);
			oc.setEstadoPagamento(EstadoPagamento.PENDENTE);
			contaService.updateValorDelete(oc.getConta().getId(), oc, "alterar");
		}
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
	
	public Page<OperacaoConta> findPagePorData(Integer page, Integer linesPerPage, String orderBy, String direction, String dataInicio, String dataFinal) {
		UserSS user = UserService.authenticated();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dataInicioBusca = LocalDateTime.parse(dataInicio, format);
		LocalDateTime dataFimBusca = LocalDateTime.parse(dataFinal, format);
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.pageByCliente(user.getId(),dataInicioBusca,dataFimBusca,pageRequest);
	}
	
	public String selecionaMes(Integer numeroMes) {
		String nomeMês = null;
		switch (numeroMes){
        case 1:
        	nomeMês = "Janeiro";
            break;
        case 2:
        	nomeMês = "Fevereiro";
            break;
        case 3:
        	nomeMês = "Março";
            break;
        case 4:
        	nomeMês = "Abril";
            break;
        case 5:
        	nomeMês = "Maio";
            break;
        case 6:
        	nomeMês = "Junho";
            break;
        case 7:
        	nomeMês = "Julho";
            break;
        case 8:
        	nomeMês = "Agosto";
            break;
        case 9:
        	nomeMês = "Setembro";
            break;
        case 10:
        	nomeMês = "Outubro";
            break;
        case 11:
        	nomeMês = "Novembro";
            break;
        case 12:
        	nomeMês = "Dezembro";
            break;
    }
		return nomeMês;		
	}

}
