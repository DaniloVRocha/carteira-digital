package com.danilo.carteira.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.danilo.carteira.domain.Cliente;
import com.danilo.carteira.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public List<Cliente> listarTodos(){
		List<Cliente> obj = repository.findAll();
		return obj;
	}
	
	public Cliente buscarId(Long id) {
		Optional<Cliente> cli = repository.findById(id);
		return cli.orElseThrow(() -> new ObjectNotFoundException(
				"Cliente não Encontrado! Id: " + id + ", Tipo : " + Cliente.class.getName(), null));
	}
	
	public Cliente inserirCliente(Cliente cli) {
		cli.setId(null);
		cli.setSenha(pe.encode(cli.getSenha()));
		return repository.save(cli);
	}
}
