package br.com.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cursomc.domain.Categoria;
import br.com.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Optional<Categoria> buscar(Integer id) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		return categoria;
	}
}
