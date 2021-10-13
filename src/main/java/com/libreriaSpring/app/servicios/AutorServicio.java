package com.libreriaSpring.app.servicios;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.libreriaSpring.app.entidades.Autor;
import com.libreriaSpring.app.errores.ErrorServicio;
import com.libreriaSpring.app.repositorios.AutorRepositorio;

@Service
public class AutorServicio {

	// Para implementar los metodos necesitamos la interfaz LibroRepositorio
	@Autowired
	private AutorRepositorio dataAutor;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void crearAutor(String nombre) throws ErrorServicio {
		
		validarAutor(nombre);
		
		Autor a = new Autor();
		a.setNombre(nombre);
		a.setAlta(true);
		
		dataAutor.save(a);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void modificarAutor(String id, String nombre) throws ErrorServicio {
		
		validarAutor(nombre);
		
		Optional<Autor> autores = dataAutor.findById(id);		
		
		if (autores.isPresent()) {
			Autor a = autores.get();
			if(a.getId().equals(id)) {
				a.setNombre(nombre);
				
				dataAutor.save(a);
			}else {
				throw new ErrorServicio("*No puede realizar la modificacion");
			}		
		}else {
			throw new ErrorServicio("*No se encontro el autor solicitado");
		}
	}
	
	@Transactional(readOnly = true)
	public List<Autor> listarAutores() {	
		return dataAutor.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Autor> listarAutoresActivos() {
		return dataAutor.buscarAutoresActivos();
	}
	
	@Transactional(readOnly = true)
	public List<Autor> buscarAutorPorNombre(String nombre) throws ErrorServicio {
		
		List<Autor> autor = dataAutor.buscarAutorPorNombre(nombre);
		
		if(!autor.isEmpty()) {
			return autor;
		}else {
			throw new ErrorServicio("*No se encontró el nombre del autor");
		}	
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Optional<Autor> buscarAutorPorId(String id) {
		return dataAutor.findById(id);
	}	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Autor autorAlta(String id) throws ErrorServicio{

		Optional<Autor> autores = dataAutor.findById(id);	
		
		if (autores.isPresent()) {
			Autor a = autores.get();

			a.setAlta(true);
			
			return dataAutor.save(a);
		}else {
			throw new ErrorServicio("No se encontro el autor solicitado");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Autor autorBaja(String id) throws ErrorServicio{

		Optional<Autor> autores = dataAutor.findById(id);	
		
		if (autores.isPresent()) {
			Autor a = autores.get();

			a.setAlta(false);
			
			return dataAutor.save(a);
		}else {
			throw new ErrorServicio("No se encontro el autor solicitado");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminarAutor(Autor autor) {
		dataAutor.delete(autor);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminarAutorPorId(String id) throws ErrorServicio {
		dataAutor.deleteById(id);
	}
	
	public void validarAutor(String nombre) throws ErrorServicio{
		if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
			throw new ErrorServicio("*El nombre del autor está incompleto");
		}
		if (dataAutor.validarNombreAutor(nombre) != null) {
			throw new ErrorServicio("*Ya existe un autor con el mismo nombre");
		}
	}
}