package br.com.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.cursomc.domain.Categoria;
import br.com.cursomc.dto.CategoriaDTO;
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
		categoria.setId(null);
		return categoriaRepository.save(categoria);
	}
	
	public Categoria update(Categoria categoria) {
		Categoria updateCategoria = find(categoria.getId());
		updateData(updateCategoria,categoria);
		return categoriaRepository.save(categoria);
	}

	public void delete(Integer id) {
		
		try {
			categoriaRepository.deleteById(id);
			
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityExecption("Não é possível excluir uma categoria que possui produtos");
		}
	}

	public List<Categoria> findAll() {
		
		return categoriaRepository.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return categoriaRepository.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO categoriaDto) {
		return new Categoria(
				categoriaDto.getId(),
				categoriaDto.getNome());
	}
	
	private void updateData(Categoria updateCategoria, Categoria categoria) {
		updateCategoria.setNome(categoria.getNome());
		
	}
}
