package com.danilo.carteira.rest;

import java.net.URI;
import java.util.List;

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

import com.danilo.carteira.domain.Conta;
import com.danilo.carteira.dto.ContaDTO;
import com.danilo.carteira.dto.ContaEdicaoDTO;
import com.danilo.carteira.dto.ContaValoresDTO;
import com.danilo.carteira.dto.OperacaoContaDTO;
import com.danilo.carteira.service.ContaService;
import com.danilo.carteira.service.OperacaoContaService;

@RestController
@RequestMapping(value="/contas")
public class ContaRest {
	
	@Autowired
	private ContaService service;
	@Autowired
	private OperacaoContaService operacaoService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ContaDTO> buscarId(@Valid @PathVariable Long id){
		Conta conta = service.buscarId(id);
		ContaDTO contaDTO = new ContaDTO(conta);
		return ResponseEntity.ok().body(contaDTO);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Conta>> buscarPorIdCliente(){
		List<Conta> contas = service.buscarPorIdCliente();
		return ResponseEntity.ok().body(contas);
	}
	
	@RequestMapping(value = "/atualizarValores", method = RequestMethod.GET)
	public ResponseEntity<ContaValoresDTO> atualizarPreencherSaldo(){
		ContaValoresDTO conta = service.atualizarPreencherSaldo();
		return ResponseEntity.ok().body(conta);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deletar(@Valid @PathVariable Long id){
		service.deletarConta(id);
		return ResponseEntity.ok().body("Conta ID: " + id + " Deletado com sucesso");
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserirConta(@Valid @RequestBody Conta conta){
		service.inserirConta(conta);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(conta.getId()).toUri();
				return ResponseEntity.created(uri).build();
	}	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> alterarConta(@Valid @RequestBody ContaEdicaoDTO conta, @Valid @PathVariable Long id){
		service.alterarConta(conta, id);
		return new ResponseEntity<>("Conta editada com sucesso", HttpStatus.OK);
	}	
	
	@RequestMapping(value = "/atualizarValores-data", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ContaValoresDTO> atualizarPreencherSaldoPorMes(@RequestParam("dataInicial") String dataInicial, @RequestParam("dataFinal") String dataFinal) throws Exception {
		List<OperacaoContaDTO> operacoesMes = operacaoService.consultarOperacoesPorData(dataInicial.replace("\"", ""),dataFinal.replace("\"", ""));
		ContaValoresDTO valoresMes = service.calcularTotalOperacoesMes(operacoesMes);
		return new ResponseEntity<>(valoresMes, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/consultar-valores-data/{numeroMes}/{numeroAno}", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ContaValoresDTO> consultarValoresData(@Valid @PathVariable Integer numeroMes, @Valid @PathVariable Integer numeroAno) throws Exception {
		List<OperacaoContaDTO> operacoesMes = operacaoService.consultarOperacoesPorMesAno(numeroMes, numeroAno);
		ContaValoresDTO valoresMes = service.calcularTotalOperacoesMes(operacoesMes);
		return new ResponseEntity<>(valoresMes, HttpStatus.OK);
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
