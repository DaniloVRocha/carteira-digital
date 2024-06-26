package com.danilo.carteira.rest;

import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.danilo.carteira.domain.OperacaoConta;
import com.danilo.carteira.dto.ContaTransferenciaDTO;
import com.danilo.carteira.dto.OperacaoContaDTO;
import com.danilo.carteira.dto.OperacaoPorDataDTO;
import com.danilo.carteira.service.OperacaoContaService;
import com.danilo.carteira.service.exceptions.OperacaoNaoEncontradaException;

@RestController
@RequestMapping(value="/operacoes")
public class OperacaoContaRest {
	@Autowired
	private OperacaoContaService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<OperacaoContaDTO> buscarId(@Valid @PathVariable Long id){
		OperacaoConta op = service.buscarId(id);
		OperacaoContaDTO opDTO = new OperacaoContaDTO(op);
		return ResponseEntity.ok().body(opDTO);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserirOperacao(@Valid @RequestBody OperacaoConta op){
		service.inserirOperacao(op);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(op.getId()).toUri();
				return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterarOperacao(@Valid @RequestBody OperacaoConta op, @Valid @PathVariable Long id) throws OperacaoNaoEncontradaException{
		service.alterarOperacao(op, id);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(op.getId()).toUri();
				return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="alterarEstadoPagamento/{idOperacao}", method = RequestMethod.PUT)
	public ResponseEntity<String> alterarEstadoPagamento(@Valid @PathVariable Long idOperacao) throws OperacaoNaoEncontradaException{
		service.alterarEstadoPagamento(idOperacao);
				return ResponseEntity.ok().body("Estado de Pagamento Alterado");
	}
	
	@RequestMapping(value = "/qntOperacoes/{idConta}", method = RequestMethod.GET)
	public ResponseEntity<List<Integer>> buscaQuantidadeDeOperacoesPorConta(@Valid @PathVariable Long idConta){
		List<Integer> quantidadeDeOperacoes = service.buscaQuantidadeDeOperacoesPorConta(idConta);
		return ResponseEntity.ok().body(quantidadeDeOperacoes);
	}
	
	@RequestMapping(value = "/{id}/{novoSaldo}", method=RequestMethod.POST)
	public ResponseEntity<String> ajustarSaldo(@Valid @PathVariable Long id,  @PathVariable String novoSaldo ){
		BigDecimal novoSaldoBig = new BigDecimal(novoSaldo);
		service.ajustarSaldo(novoSaldoBig, id);
		return ResponseEntity.ok().body("Saldo da conta ID: "+ id + " Alterado para: R$"
		+ novoSaldo);
	}
	
	@RequestMapping(value = "/consultarOperacaoData", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<OperacaoContaDTO>> consultarOperacaoPorData(@RequestBody OperacaoPorDataDTO op) throws Exception {
		
		List<OperacaoContaDTO> operacoes = service.consultarOperacoesPorData(op.getDataInicial(), op.getDataFinal());
		return new ResponseEntity<>(operacoes, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/consultarOperacaoMesAno/{numeroMes}/{numeroAno}", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<OperacaoContaDTO>> consultarOperacoesPorMes(@PathVariable Integer numeroMes, @PathVariable Integer numeroAno) throws Exception {
		
		List<OperacaoContaDTO> operacoes = service.consultarOperacoesPorMesAno(numeroMes, numeroAno);
		return new ResponseEntity<>(operacoes, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/consultarOperacaoVencimento", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<OperacaoContaDTO>> consultarOperacaoVencimento() throws Exception {
		
		List<OperacaoContaDTO> operacoes = service.consultarOperacoesVencidas();
		
		return new ResponseEntity<>(operacoes, HttpStatus.OK);
	}
	
	@RequestMapping(value="/transferencia" ,method=RequestMethod.POST)
	public ResponseEntity<String> transferencia(@Valid @RequestBody ContaTransferenciaDTO conta){
		service.transferenciaEntreContas(conta);
		return ResponseEntity.ok().body(conta.getValor() + "Transferido");
	}
	
	@RequestMapping(value = "/consultarQuantidadeCategoriasPorMesAno/{numeroMes}/{numeroAno}", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<HashMap<String, Integer>> consultarQuantidadeCategoriasPorMesAno(@PathVariable Integer numeroMes, @PathVariable Integer numeroAno) throws Exception {
		
		HashMap<String, Integer> categorias = service.buscarTodosPorIdCliente(numeroMes, numeroAno);
		return new ResponseEntity<>(categorias, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/consultarValoresAno", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Map<String, Double>>> preencherGraficoReceitas() throws Exception {
		
		List<Map<String, Double>> valores = service.consultarOperacoesPorAno();
		return new ResponseEntity<>(valores, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deletarOperacao(@Valid @PathVariable Long id){
		service.deletarOperacao(id);
		return ResponseEntity.ok().body("Operação ID: "+ id + " Deletada com sucesso.");
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<OperacaoContaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="5") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="id") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction){

		Page<OperacaoConta> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<OperacaoContaDTO> listDto = list.map(obj -> new OperacaoContaDTO(obj));
		return ResponseEntity.ok().body(listDto);

	}
	@RequestMapping(value = "/page-date", method = RequestMethod.GET)
	public ResponseEntity<Page<OperacaoContaDTO>> findPagePorData(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="5") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="id") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction,
			@RequestParam("dataInicial") String dataInicial, 
			@RequestParam("dataFinal") String dataFinal){

		Page<OperacaoConta> list = service.findPagePorData(page, linesPerPage, orderBy, direction,dataInicial.replace("\"", ""),dataFinal.replace("\"", ""));
		Page<OperacaoContaDTO> listDto = list.map(obj -> new OperacaoContaDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}
}
