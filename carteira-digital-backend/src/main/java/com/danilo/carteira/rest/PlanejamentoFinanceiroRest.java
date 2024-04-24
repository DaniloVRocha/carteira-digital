package com.danilo.carteira.rest;

import com.danilo.carteira.api.mapper.ContaMapper;
import com.danilo.carteira.domain.Conta;
import com.danilo.carteira.domain.PlanejamentoFinanceiro;
import com.danilo.carteira.dto.OperacaoContaDTO;
import com.danilo.carteira.dto.request.ContaRequest;
import com.danilo.carteira.dto.response.ContaResponse;
import com.danilo.carteira.dto.response.ContaValoresResponse;
import com.danilo.carteira.service.ContaService;
import com.danilo.carteira.service.OperacaoContaService;
import com.danilo.carteira.service.PlanejamentoFinanceiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value="/planejamentos")
public class PlanejamentoFinanceiroRest {
	
	@Autowired
	private PlanejamentoFinanceiroService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<PlanejamentoFinanceiro>> listarPlanejamentos(){
		List<PlanejamentoFinanceiro> planejamentos = service.listarTodos();
		return ResponseEntity.ok().body(planejamentos);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserirConta( @RequestBody PlanejamentoFinanceiro planejamento){
		service.inserirPlanejamento(planejamento);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(planejamento.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
/*
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
	

	
	@RequestMapping(value = "/consultarSaldoConta", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/consultarValoresData", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ContaValoresResponse> atualizarPreencherSaldoPorMes(@RequestParam("dataInicial") String dataInicial, @RequestParam("dataFinal") String dataFinal) throws Exception {
		List<OperacaoContaDTO> operacoesMes = operacaoService.consultarOperacoesPorData(dataInicial.replace("\"", ""),dataFinal.replace("\"", ""));
		ContaValoresResponse valoresMes = service.calcularTotalOperacoesMes(operacoesMes);
		return ResponseEntity.ok().body(valoresMes);
	}
	
	@RequestMapping(value = "/consultarValoresMesAno/{numeroMes}/{numeroAno}", method=RequestMethod.GET)
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

	}*/
	
}
