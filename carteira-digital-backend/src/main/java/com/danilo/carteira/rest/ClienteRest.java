package com.danilo.carteira.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.danilo.carteira.api.mapper.ClienteMapper;
import com.danilo.carteira.domain.Cliente;
import com.danilo.carteira.dto.request.CadastroClienteRequest;
import com.danilo.carteira.dto.response.ClienteResponse;
import com.danilo.carteira.service.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteRest {
	
	@Autowired
	private ClienteService service;
	
	@Autowired
	private ClienteMapper mapper;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ClienteResponse> buscarId(@Valid @PathVariable Long id){
		Cliente cli = service.buscarId(id);
		ClienteResponse cliDTO = new ClienteResponse(cli);
		return ResponseEntity.ok().body(cliDTO);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ClienteResponse inserirCliente(@Valid @RequestBody CadastroClienteRequest clienteRequest){
		Cliente cliente = mapper.toEntity(clienteRequest);
		Cliente clienteSalvo = service.inserirCliente(cliente);
		return mapper.toModel(clienteSalvo);
	}	
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deletarCliente(@Valid @PathVariable Long id){
		service.delete(id);
		return ResponseEntity.ok().body("Cliente Id: " + id + " Deletado com sucesso." );
	}
	
	@RequestMapping(value = "/alterar", method = RequestMethod.PUT)
	public Cliente updateCliente(@RequestBody @Valid Cliente cliente){
		return service.alterarCliente(cliente);
	}
	
}
