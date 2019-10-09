package br.com.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cursomc.domain.Categoria;
import br.com.cursomc.repositories.CategoriaRepository;
import br.com.cursomc.services.exceptions.ObjectNotFoundExecption;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria buscar(Integer id) {
		
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		
		return categoria.orElseThrow(() -> new ObjectNotFoundExecption(
				"Objeto não encontrado! Id: " + id + ", Tipo: "+ Categoria.class.getName()));
		
	}
}
