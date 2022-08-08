package com.danilo.carteira.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.danilo.carteira.domain.Cliente;
import com.danilo.carteira.dto.request.CadastroClienteRequest;
import com.danilo.carteira.dto.response.ClienteResponse;

@Component
public class ClienteMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ClienteResponse toModel(Cliente cliente) {
		return modelMapper.map(cliente, ClienteResponse.class);
	}
	
	public Cliente toEntity(CadastroClienteRequest clienteRequest) {
		return modelMapper.map(clienteRequest, Cliente.class);
	}
	
}
