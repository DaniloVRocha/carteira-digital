package com.danilo.carteira.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danilo.carteira.domain.Cliente;
import com.danilo.carteira.dto.ClienteDTO;
import com.danilo.carteira.service.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteRest {
	
	@Autowired
	private ClienteService service;
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> listarTodos(){
		List<Cliente> list = service.listarTodos();
		List<ClienteDTO> objDTO = list.stream().map(x -> new ClienteDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(objDTO);
	}
	
	
}
