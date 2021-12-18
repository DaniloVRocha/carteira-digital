package com.danilo.carteira.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danilo.carteira.domain.Cliente;
import com.danilo.carteira.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	public List<Cliente> listarTodos(){
		List<Cliente> obj = repository.findAll();
		return obj;
	}
	
}
