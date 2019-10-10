package br.com.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cursomc.domain.Cliente;
import br.com.cursomc.repositories.ClienteRepository;
import br.com.cursomc.services.exceptions.ObjectNotFoundExecption;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente buscar(Integer id) {
		
		Optional<Cliente> cliente = clienteRepository.findById(id);
		
		return cliente.orElseThrow(() -> new ObjectNotFoundExecption(
				"Objeto não encontrado! Id: " + id + ", Tipo: "+ Cliente.class.getName()));
		
	}
}
