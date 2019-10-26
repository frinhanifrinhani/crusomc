package br.com.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cursomc.domain.Cidade;
import br.com.cursomc.domain.Cliente;
import br.com.cursomc.domain.Endereco;
import br.com.cursomc.domain.enums.TipoCliente;
import br.com.cursomc.dto.ClienteDTO;
import br.com.cursomc.dto.ClienteNewDTO;
import br.com.cursomc.repositories.ClienteRepository;
import br.com.cursomc.repositories.EnderecoRepository;
import br.com.cursomc.services.exceptions.DataIntegrityExecption;
import br.com.cursomc.services.exceptions.ObjectNotFoundExecption;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCriBCryptPasswordEncoder;
	
	public Cliente find(Integer id) {

		Optional<Cliente> cliente = clienteRepository.findById(id);

		return cliente.orElseThrow(() -> new ObjectNotFoundExecption(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));

	}
	
	@Transactional
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		cliente = clienteRepository.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente;
	}
	
	public Cliente update(Cliente cliente) {

		Cliente updateCliente = find(cliente.getId());
		updateData(updateCliente,cliente);
		return clienteRepository.save(updateCliente);
	}

	public void delete(Integer id) {

		try {
			clienteRepository.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityExecption("Não é possível excluir pois existem entidades relacionadas");
		}
	}

	public List<Cliente> findAll() {

		return clienteRepository.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO clienteDto) {
		return new Cliente(clienteDto.getId(),clienteDto.getNome(),clienteDto.getEmail(),null,null,null);
	}
	
	public Cliente fromDTO(ClienteNewDTO clienteNewDto) {
		Cliente cliente = new Cliente(
				null,
				clienteNewDto.getNome(),
				clienteNewDto.getEmail(),
				clienteNewDto.getCpfCnpj(), 
				TipoCliente.toEnum(clienteNewDto.getTipo()),
				bCriBCryptPasswordEncoder.encode(clienteNewDto.getSenha()));
		
		Cidade cidade = new Cidade(clienteNewDto.getCidadeId(), null, null);
		
		Endereco endereco = new Endereco(
				null,
				clienteNewDto.getLogradouro(),
				clienteNewDto.getNumero(),
				clienteNewDto.getComplemento(),
				clienteNewDto.getBairro(),
				clienteNewDto.getCep(),
				cliente,
				cidade);
		
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(clienteNewDto.getTelefone1());
		
		if(clienteNewDto.getTelefone2() != null) {
			cliente.getTelefones().add(clienteNewDto.getTelefone2());
		}
		
		if(clienteNewDto.getTelefone3() != null) {
			cliente.getTelefones().add(clienteNewDto.getTelefone3());
		}
		
		return cliente;
	}
	
	private void updateData(Cliente updateClinte, Cliente cliente) {
		updateClinte.setNome(cliente.getNome());
		updateClinte.setEmail(cliente.getEmail());
	}
}
