package com.danilo.carteira.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.danilo.carteira.domain.Conta;
import com.danilo.carteira.dto.request.ContaRequest;
import com.danilo.carteira.dto.response.ContaResponse;

@Component
public class ContaMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ContaResponse toModel(Conta conta) {
		return modelMapper.map(conta, ContaResponse.class);
	}
	
	public List<ContaResponse> toCollectionModel(List<Conta> contas) {
		return contas.stream().map(this::toModel).collect(Collectors.toList());
	}
	
	public Conta toEntity(ContaRequest contaRequest) {
		return modelMapper.map(contaRequest, Conta.class);
	}
}
