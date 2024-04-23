package com.danilo.carteira.rest;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.danilo.carteira.api.mapper.ContaMapper;
import com.danilo.carteira.domain.Conta;
import com.danilo.carteira.dto.OperacaoContaDTO;
import com.danilo.carteira.dto.request.ContaRequest;
import com.danilo.carteira.dto.response.ContaResponse;
import com.danilo.carteira.dto.response.ContaValoresResponse;
import com.danilo.carteira.service.ContaService;
import com.danilo.carteira.service.OperacaoContaService;

@RestController
@RequestMapping(value="/contas")
public class ContaRest {
	
	@Autowired
	private ContaService service;
	
	@Autowired
	private OperacaoContaService operacaoService;
	
	@Autowired
	private ContaMapper mapper;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ContaResponse>> listarContasCliente(){
		List<Conta> contas = service.buscarPorIdCliente();
		List<ContaResponse> response = mapper.toCollectionModel(contas);
		return ResponseEntity.ok().body(response);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ContaResponse> listarPorId(@PathVariable Long id){
		Conta conta = service.buscarId(id);
		ContaResponse response = mapper.toModel(conta);
		return ResponseEntity.ok().body(response);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deletar(@PathVariable Long id){
		service.deletarConta(id);
		return ResponseEntity.ok().body("Conta ID: " + id + " Deletado com sucesso");
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserirConta( @RequestBody Conta conta){
		service.inserirConta(conta);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(conta.getId()).toUri();
				return ResponseEntity.created(uri).build();
	}	
	
	@RequestMapping(value = "/consultar-valores", method = RequestMethod.GET)
	public ResponseEntity<ContaValoresResponse> atualizarPreencherSaldo(){
		ContaValoresResponse conta = service.atualizarPreencherSaldo();
		return ResponseEntity.ok().body(conta);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ContaResponse> alterarConta(@Valid @RequestBody ContaRequest conta, @PathVariable Long id){
		Conta contaEditada = service.alterarConta(conta, id);
		ContaResponse response = mapper.toModel(contaEditada);
		return ResponseEntity.ok().body(response);
	}	
	
	@RequestMapping(value = "/consultar-valores-data", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ContaValoresResponse> atualizarPreencherSaldoPorMes(@RequestParam("dataInicial") String dataInicial, @RequestParam("dataFinal") String dataFinal) throws Exception {
		List<OperacaoContaDTO> operacoesMes = operacaoService.consultarOperacoesPorData(dataInicial.replace("\"", ""),dataFinal.replace("\"", ""));
		ContaValoresResponse valoresMes = service.calcularTotalOperacoesMes(operacoesMes);
		return ResponseEntity.ok().body(valoresMes);
	}
	
	@RequestMapping(value = "/consultar-valores-data/{numeroMes}/{numeroAno}", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ContaValoresResponse> consultarValoresData(@PathVariable Integer numeroMes, @PathVariable Integer numeroAno) throws Exception {
		List<OperacaoContaDTO> operacoesMes = operacaoService.consultarOperacoesPorMesAno(numeroMes, numeroAno);
		ContaValoresResponse valoresMes = service.calcularTotalOperacoesMes(operacoesMes);
		return ResponseEntity.ok().body(valoresMes);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<Conta>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="5") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="saldo") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {

		Page<Conta> list = service.findPage(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(list);

	}
	
}
