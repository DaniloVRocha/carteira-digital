package com.danilo.carteira.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danilo.carteira.config.security.UserSS;
import com.danilo.carteira.domain.Cliente;
import com.danilo.carteira.domain.enums.Perfil;
import com.danilo.carteira.repository.ClienteRepository;
import com.danilo.carteira.service.exceptions.AuthorizationException;
import com.danilo.carteira.service.exceptions.DataIntegrityException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;

	public List<Cliente> listarTodos() {
		List<Cliente> obj = repository.findAll();
		return obj;
	}

	public Cliente buscarId(Long id) {

		UserSS user = UserService.authenticated();

		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso Negado");

		}

		Optional<Cliente> cli = repository.findById(id);
		return cli.orElseThrow(() -> new ObjectNotFoundException(
				"Cliente não Encontrado! Id: " + id + ", Tipo : " + Cliente.class.getName(), null));
	}

	public Cliente inserirCliente(Cliente cli) {
		cli.setId(null);
		cli.setSenha(pe.encode(cli.getSenha()));
		emailService.sendNewClientConfirmationHtmlEmail(cli);
		return repository.save(cli);
	}

	public Cliente alterarCliente(Cliente obj) {
		UserSS user = UserService.authenticated();
		if (obj.getId() != user.getId()) {
			throw new AuthorizationException("Acesso Negado");
		}
		Cliente newObj = buscarId(user.getId());
		updateData(newObj, obj);
		return repository.save(newObj);
	}

	public void delete(Long id) {
		buscarId(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException ex) {
			throw new DataIntegrityException("Não é possivel excluir uma cliente que tenha contas cadastradas");
		}
	}

	@Transactional
	private void updateData(Cliente newObj, Cliente obj) {
		if (obj.getNome() != null) {
			newObj.setNome(obj.getNome());
		}
		if (obj.getEmail() != null) {
			newObj.setEmail(obj.getEmail());
		}
		if (obj.getCpf() != null) {
			newObj.setCpf(obj.getCpf());
		}
		if (obj.getTipoCliente() != null) {
			newObj.setTipoCliente(obj.getTipoCliente());
		}
		if (obj.getAtivo() != null) {
			newObj.setAtivo(obj.getAtivo());
		}
		if (obj.getObservacoes() != null) {
			newObj.setObservacoes(obj.getObservacoes());
		}
	}
}
