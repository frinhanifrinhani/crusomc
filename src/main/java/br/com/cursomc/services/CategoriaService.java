package br.com.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.cursomc.domain.Categoria;
import br.com.cursomc.repositories.CategoriaRepository;
import br.com.cursomc.services.exceptions.DataIntegrityExecption;
import br.com.cursomc.services.exceptions.ObjectNotFoundExecption;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria find(Integer id) {
		
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		
		return categoria.orElseThrow(() -> new ObjectNotFoundExecption(
				"Objeto não encontrado! Id: " + id + ", Tipo: "+ Categoria.class.getName()));
		
	}
	
	public Categoria insert(Categoria categoria) {
		
		return categoriaRepository.save(categoria);
	}
	
	public Categoria update(Categoria categoria) {
		
		find(categoria.getId());
		
		return categoriaRepository.save(categoria);
	}

	public void delete(Integer id) {
		
		try {
			categoriaRepository.deleteById(id);
			
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityExecption("Não é possível excluir uma categoria que possui produtos");
		}
	}
}
