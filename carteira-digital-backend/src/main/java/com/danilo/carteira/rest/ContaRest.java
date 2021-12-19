package com.danilo.carteira.rest;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.danilo.carteira.domain.Conta;
import com.danilo.carteira.dto.ContaDTO;
import com.danilo.carteira.service.ContaService;

@RestController
@RequestMapping(value="/contas")
public class ContaRest {
	
	@Autowired
	private ContaService service;
	
	@GetMapping
	public ResponseEntity<List<ContaDTO>> listarTodos(){
		List<Conta> list = service.listarTodos();
		List<ContaDTO> objDTO = list.stream().map(x -> new ContaDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(objDTO);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<ContaDTO> buscarId(@Valid @PathVariable Long id){
		Conta conta = service.buscarId(id);
		ContaDTO contaDTO = new ContaDTO(conta);
		return ResponseEntity.ok().body(contaDTO);
	}
	
	@PostMapping
	public ResponseEntity<Void> inserirConta(@Valid @RequestBody Conta conta){
		service.inserirConta(conta);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(conta.getId()).toUri();
				return ResponseEntity.created(uri).build();
	}	
}
