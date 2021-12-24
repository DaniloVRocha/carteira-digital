package com.danilo.carteira.rest;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.danilo.carteira.domain.OperacaoConta;
import com.danilo.carteira.dto.OperacaoContaDTO;
import com.danilo.carteira.service.OperacaoContaService;

@RestController
@RequestMapping(value="/operacoes")
public class OperacaoContaRest {
	@Autowired
	private OperacaoContaService service;
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<OperacaoContaDTO>> listarTodos(){
		List<OperacaoConta> list = service.listarTodos();
		List<OperacaoContaDTO> objDTO = list.stream().map(x -> new OperacaoContaDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(objDTO);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<OperacaoContaDTO> buscarId(@Valid @PathVariable Long id){
		OperacaoConta op = service.buscarId(id);
		OperacaoContaDTO opDTO = new OperacaoContaDTO(op);
		return ResponseEntity.ok().body(opDTO);
	}
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserirOperacao(@Valid @RequestBody OperacaoConta op){
		service.inserirOperacao(op);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(op.getId()).toUri();
				return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}/{novoSaldo}", method=RequestMethod.POST)
	public ResponseEntity<String> ajustarSaldo(@Valid @PathVariable Long id,  @PathVariable Double novoSaldo ){
		service.ajustarSaldo(novoSaldo, id);
		return ResponseEntity.ok().body("Saldo da conta ID: "+ id + " Alterado para: R$"
		+ novoSaldo);
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
}
