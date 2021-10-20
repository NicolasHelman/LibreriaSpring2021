package com.libreriaSpring.app.servicios;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.libreriaSpring.app.entidades.Autor;
import com.libreriaSpring.app.entidades.Editorial;
import com.libreriaSpring.app.entidades.Libro;
import com.libreriaSpring.app.errores.ErrorServicio;
import com.libreriaSpring.app.repositorios.AutorRepositorio;
import com.libreriaSpring.app.repositorios.EditorialRepositorio;
import com.libreriaSpring.app.repositorios.LibroRepositorio;

@Service
public class LibroServicio {

	// Para implementar los metodos necesitamos la interfaz LibroRepositorio
	@Autowired
	private LibroRepositorio dataLibro;
	
	@Autowired
	private AutorRepositorio dataAutor;
	
	@Autowired
	private EditorialRepositorio dataEditorial;
	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void crearLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, String idAutor, String idEditorial) throws ErrorServicio{
		
		@SuppressWarnings("deprecation")
		Autor a = dataAutor.getOne(idAutor);
		@SuppressWarnings("deprecation")
		Editorial e = dataEditorial.getOne(idEditorial);
		
		validarCrearLibro(isbn, titulo, anio, ejemplares, a, e, e.getNombre());
				
		Libro l = new Libro();
		l.setIsbn(isbn);
		l.setTitulo(titulo);
		l.setAnio(anio);
		l.setEjemplares(ejemplares);
		l.setEjemplaresPrestados(0);
        l.setEjemplaresRestantes(l.getEjemplares());
		l.setAlta(true);
		
		l.setAutor(a);
		l.setEditorial(e);
		
		dataLibro.save(l);
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void modificarLibro(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, String idAutor, String idEditorial) throws ErrorServicio{
		
		@SuppressWarnings("deprecation")
		Autor a = dataAutor.getOne(idAutor);
		@SuppressWarnings("deprecation")
		Editorial e = dataEditorial.getOne(idEditorial);
		
		validarModificarLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, a, e);
		
		Optional<Libro> respuesta = dataLibro.findById(id);
		
		if (respuesta.isPresent()) {
			Libro l = respuesta.get();
			if (l.getId().equals(id)) {
				l.setIsbn(isbn);
				l.setTitulo(titulo);
				l.setAnio(anio);
				l.setEjemplares(ejemplares);
				l.setEjemplaresPrestados(ejemplaresPrestados);
		        l.setEjemplaresRestantes(l.getEjemplares() - l.getEjemplaresPrestados());
				
				l.setAutor(a);
				l.setEditorial(e);
				
				dataLibro.save(l);
			}else {
				throw new ErrorServicio("*No puede realizar la modificacion");
			}		
		}else {
			throw new ErrorServicio("No se encontró el libro solicitado");
		}
	}	
	
	@Transactional(readOnly = true)
	public List<Libro> listarLibros() {	
		return dataLibro.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Libro> listarLibrosActivos() {
		return dataLibro.buscarLibrosActivos();
	}
	
	@Transactional(readOnly = true)
	public List<Libro> buscarLibroPorTitulo(String titulo) throws ErrorServicio {
		
		List<Libro> libro = dataLibro.buscarLibroPorTitulo(titulo);
		
		if(!libro.isEmpty()) {
			return libro;
		}else {
			throw new ErrorServicio("*No se encontró el título del libro");
		}	
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Optional<Libro> buscarLibroPorId(String id) {
		return dataLibro.findById(id);
	}	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Libro libroAlta(String id) throws ErrorServicio{

		Optional<Libro> libros = dataLibro.findById(id);	
		
		if (libros.isPresent()) {
			Libro l = libros.get();

			l.setAlta(true);
			
			return dataLibro.save(l);
		}else {
			throw new ErrorServicio("No se encontro el libro solicitado");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Libro libroBaja(String id) throws ErrorServicio{

		Optional<Libro> libros = dataLibro.findById(id);	
		
		if (libros.isPresent()) {
			Libro l = libros.get();

			l.setAlta(false);
			
			return dataLibro.save(l);
		}else {
			throw new ErrorServicio("No se encontro el libro solicitado");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminarLibro(Libro libro) {
		dataLibro.delete(libro);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminarLibroPorId(String id) throws ErrorServicio {
		dataLibro.deleteById(id);
	}
	
	public void validarCrearLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Autor autor, Editorial editorial, String nombreEditorial) throws ErrorServicio{
		if (isbn == null) {
			throw new ErrorServicio("*El isbn está incompleto");
		}			
		if (isbn.toString().length() < 10 || isbn.toString().length() > 13) {
			throw new ErrorServicio("*El Isbn debe ser de 10-13 dígitos");
		}
		if (dataLibro.validarIsbn(isbn) != null) {
			throw new ErrorServicio("*Ya existe un libro con el mismo isbn");
		}
		if (titulo == null || titulo.isEmpty() || titulo.contains("  ")) {
			throw new ErrorServicio("*El título está incompleto");
		}
		if (anio == null) {
			throw new ErrorServicio("*El año está incompleto");
		}
		if (anio < 1900 || anio > 2021) {
			throw new ErrorServicio("*El año ingresado es invalido");
		}
		if (ejemplares == null || ejemplares <= 0) {
			throw new ErrorServicio("*Los ejemplares están incompletos");
		}
		if(autor == null) {
			throw new ErrorServicio("*No se encontró el autor solicitado");
		}	
		if(editorial == null) {
			throw new ErrorServicio("*No se encontró la editorial solicitada");
		}	
		if( dataLibro.validarTituloEditorial(titulo,nombreEditorial) != null ) {
			throw new ErrorServicio("*Ya existe un libro con el mismo titulo y editorial");
		}
	}	
	
	public void validarModificarLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Autor autor, Editorial editorial) throws ErrorServicio{
		if (isbn == null) {
			throw new ErrorServicio("*El isbn está incompleto");
		}			
		if (isbn.toString().length() < 10 || isbn.toString().length() > 13) {
			throw new ErrorServicio("*El Isbn debe ser de 10-13 dígitos");
		}
		if (titulo == null || titulo.isEmpty() || titulo.contains("  ")) {
			throw new ErrorServicio("*El título está incompleto");
		}
		if (anio == null) {
			throw new ErrorServicio("*El año está incompleto");
		}
		if (anio < 1900 || anio > 2021) {
			throw new ErrorServicio("*El año ingresado es invalido");
		}
		if (ejemplares == null) {
			throw new ErrorServicio("*Los ejemplares están incompletos");
		}
		if (ejemplaresPrestados == null) {
			throw new ErrorServicio("*Los ejemplares están incompletos");
		}
		if(autor == null) {
			throw new ErrorServicio("*No se encontró el autor solicitado");
		}	
		if(editorial == null) {
			throw new ErrorServicio("*No se encontró la editorial solicitada");
		}	
	}	
}
