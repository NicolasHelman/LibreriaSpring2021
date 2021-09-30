package com.libreriaSpring.app.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.libreriaSpring.app.entidades.Libro;
import com.libreriaSpring.app.repositorios.LibroRepositorio;

@Service
public class LibroServicio {

	// Para implementar los metodos necesitamos la interfaz LibroRepositorio
	@Autowired
	private LibroRepositorio data;
	
	public Libro crearLibro(Libro libro) {
		return data.save(libro);
	}
	
	public void eliminarLibro(Libro libro) {
		data.delete(libro);
	}
	
	public List<Libro> listarLibros() {	
		return data.findAll();
	}

	public Optional<Libro> buscarLibroPorId(String id) {
		return data.findById(id);
	}	
	
}
