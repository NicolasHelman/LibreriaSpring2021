package com.libreriaSpring.app.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.libreriaSpring.app.entidades.Editorial;
import com.libreriaSpring.app.repositorios.EditorialRepositorio;

@Service
public class EditorialServicio {

	// Para implementar los metodos necesitamos la interfaz LibroRepositorio
	@Autowired
	private EditorialRepositorio data;
	
	public Editorial crearEditorial(Editorial editorial) {
		return data.save(editorial);
	}
	
	public void eliminarAutor(Editorial editorial) {
		data.delete(editorial);
	}
	
	public List<Editorial> listarEditoriales() {	
		return data.findAll();
	}

	public Optional<Editorial> buscarEditorialPorId(String id) {
		return data.findById(id);
	}	
	
}
