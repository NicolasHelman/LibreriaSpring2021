package com.libreriaSpring.app.servicios;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.libreriaSpring.app.entidades.Editorial;
import com.libreriaSpring.app.errores.ErrorServicio;
import com.libreriaSpring.app.repositorios.EditorialRepositorio;

@Service
public class EditorialServicio {

	// Para implementar los metodos necesitamos la interfaz LibroRepositorio
	@Autowired
	private EditorialRepositorio dataEditorial;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void crearEditorial(String nombre) throws ErrorServicio{
		
		validarEditorial(nombre);
		
		Editorial e = new Editorial();
		e.setNombre(nombre);
		e.setAlta(true);
		
		dataEditorial.save(e);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void modificarEditorial(String id, String nombre) throws ErrorServicio {
		
		validarEditorial(nombre);
		
		Optional<Editorial> editoriales = dataEditorial.findById(id);		
		
		if (editoriales.isPresent()) {
			Editorial e = editoriales.get();
			if(e.getId().equals(id)) {
				e.setNombre(nombre);
				
				dataEditorial.save(e);
			}else {
				throw new ErrorServicio("No puede realizar la modificacion");
			}		
		}else {
			throw new ErrorServicio("No se encontro la editorial solicitada");
		}
	}
	
	@Transactional(readOnly = true)
	public List<Editorial> listarEditoriales() {	
		return dataEditorial.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Editorial> listarEditorialesActivos() {
		return dataEditorial.buscarEditorialesActivos();
	}
	
	@Transactional(readOnly = true)
	public Editorial buscarEditorialPorNombre(String nombre) throws ErrorServicio {
		
		Optional<Editorial> editorial = dataEditorial.buscarEditorialPorNombre(nombre);
		
		if(!editorial.isEmpty()) {
			return editorial.get();
		}else {
			throw new ErrorServicio("*No se encontró el nombre de la editorial");
		}	
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Optional<Editorial> buscarEditorialPorId(String id) {
		return dataEditorial.findById(id);
	}	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Editorial editorialAlta(String id) throws ErrorServicio{

		Optional<Editorial> editoriales = dataEditorial.findById(id);	
		
		if (editoriales.isPresent()) {
			Editorial e = editoriales.get();

			e.setAlta(true);
			
			return dataEditorial.save(e);
		}else {
			throw new ErrorServicio("No se encontro la editorial solicitada");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Editorial editorialBaja(String id) throws ErrorServicio{

		Optional<Editorial> editoriales = dataEditorial.findById(id);	
		
		if (editoriales.isPresent()) {
			Editorial e = editoriales.get();

			e.setAlta(false);
			
			return dataEditorial.save(e);
		}else {
			throw new ErrorServicio("No se encontro la editorial solicitada");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminarEditorial(Editorial editorial) {
		dataEditorial.delete(editorial);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminarEditorialPorId(String id) throws ErrorServicio {
		dataEditorial.deleteById(id);
	}
	
	public void validarEditorial(String nombre) throws ErrorServicio{
		if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
			throw new ErrorServicio("*El nombre de la editorial está incompleto");
		}
	}
	
}
