package com.libreriaSpring.app.servicios;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.libreriaSpring.app.entidades.Autor;
import com.libreriaSpring.app.repositorios.AutorRepositorio;

@Service
public class AutorServicio {

	// Para implementar los metodos necesitamos la interfaz LibroRepositorio
	@Autowired
	private AutorRepositorio data;
	
	public Autor crearAutor(Autor autor) {
		return data.save(autor);
	}
	
	public void eliminarAutor(Autor autor) {
		data.delete(autor);
	}
	
	public List<Autor> listarAutores() {	
		return data.findAll();
	}

	public Optional<Autor> buscarAutorPorId(String id) {
		return data.findById(id);
	}	
	
}