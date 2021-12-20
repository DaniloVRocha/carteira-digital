package com.danilo.carteira.rest;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.danilo.carteira.domain.Cliente;
import com.danilo.carteira.dto.ClienteDTO;
import com.danilo.carteira.service.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteRest {
	
	@Autowired
	private ClienteService service;
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> listarTodos(){
		List<Cliente> list = service.listarTodos();
		List<ClienteDTO> objDTO = list.stream().map(x -> new ClienteDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(objDTO);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ClienteDTO> buscarId(@Valid @PathVariable Long id){
		Cliente cli = service.buscarId(id);
		ClienteDTO cliDTO = new ClienteDTO(cli);
		return ResponseEntity.ok().body(cliDTO);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserirCliente(@Valid @RequestBody Cliente cli){
		service.inserirCliente(cli);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(cli.getId()).toUri();
				return ResponseEntity.created(uri).build();
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
