package com.danilo.carteira.rest;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ContaDTO>> listarTodos(){
		List<Conta> list = service.listarTodos();
		List<ContaDTO> objDTO = list.stream().map(x -> new ContaDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(objDTO);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ContaDTO> buscarId(@Valid @PathVariable Long id){
		Conta conta = service.buscarId(id);
		ContaDTO contaDTO = new ContaDTO(conta);
		return ResponseEntity.ok().body(contaDTO);
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
