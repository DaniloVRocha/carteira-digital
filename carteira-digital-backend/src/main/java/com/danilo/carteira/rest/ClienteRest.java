package com.danilo.carteira.rest;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> listarTodos(){
		List<Cliente> list = service.listarTodos();
		List<ClienteDTO> objDTO = list.stream().map(x -> new ClienteDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(objDTO);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<ClienteDTO> buscarId(@Valid @PathVariable Long id){
		Cliente cli = service.buscarId(id);
		ClienteDTO cliDTO = new ClienteDTO(cli);
		return ResponseEntity.ok().body(cliDTO);
	}
	
	@PostMapping
	public ResponseEntity<Void> inserirCliente(@Valid @RequestBody Cliente cli){
		service.inserirCliente(cli);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(cli.getId()).toUri();
				return ResponseEntity.created(uri).build();
	}	
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value="/{id}")
	public ResponseEntity<String> deletarCliente(@Valid @PathVariable Long id){
		service.delete(id);
		return ResponseEntity.ok().body("Cliente Id: " + id + " Deletado com sucesso." );
	}
	
	@PutMapping("/{id}")
	public Cliente updatePessoa(@PathVariable Long id, @RequestBody @Valid Cliente cliente){
		return service.alterarCliente(cliente, id);	
	}
	
}
