package com.danilo.carteira.rest;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.danilo.carteira.domain.OperacaoConta;
import com.danilo.carteira.dto.OperacaoContaDTO;
import com.danilo.carteira.service.ContaService;
import com.danilo.carteira.service.OperacaoContaService;

@RestController
@RequestMapping(value="/operacoes")
public class OperacaoContaRest {
	@Autowired
	private OperacaoContaService service;
	
	@Autowired
	private ContaService contaService;
	
	
	@GetMapping
	public ResponseEntity<List<OperacaoContaDTO>> listarTodos(){
		List<OperacaoConta> list = service.listarTodos();
		List<OperacaoContaDTO> objDTO = list.stream().map(x -> new OperacaoContaDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(objDTO);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<OperacaoContaDTO> buscarId(@Valid @PathVariable Long id){
		OperacaoConta op = service.buscarId(id);
		OperacaoContaDTO opDTO = new OperacaoContaDTO(op);
		return ResponseEntity.ok().body(opDTO);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<Void> inserirCliente(@Valid @RequestBody OperacaoConta op){
		service.inserirOperacao(op);
		contaService.updateValor(op.getConta().getId(), op);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(op.getId()).toUri();
				return ResponseEntity.created(uri).build();
	}	
}
