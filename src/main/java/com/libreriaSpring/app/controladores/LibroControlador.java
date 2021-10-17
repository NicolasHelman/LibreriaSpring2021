package com.libreriaSpring.app.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.libreriaSpring.app.entidades.Autor;
import com.libreriaSpring.app.entidades.Editorial;
import com.libreriaSpring.app.entidades.Libro;
import com.libreriaSpring.app.errores.ErrorServicio;
import com.libreriaSpring.app.servicios.AutorServicio;
import com.libreriaSpring.app.servicios.EditorialServicio;
import com.libreriaSpring.app.servicios.LibroServicio;

@Controller
@RequestMapping("/libros")
public class LibroControlador {
	
	@Autowired
	private LibroServicio servicioLibro;
	
	@Autowired
	private AutorServicio servicioAutor;
	
	@Autowired
	private EditorialServicio servicioEditorial;
		
	@GetMapping()
	public String listarLibros(ModelMap modelo) {
		// modelo -> sirven para que nosotros insertaramos en ese modelo 
		// toda la informacion que vamos a mostrar en pantalla o podemos utilizar
		List<Libro> libros = servicioLibro.listarLibros();
		// modelo.addAttribute vamos a enviar toda la lista a la tabla
		modelo.addAttribute("libros", libros);
		
		return "/libro/libros";
	}
	
	@PostMapping("/buscarLibro")
	public String listarLibrosPorTitulo(ModelMap modelo, @RequestParam String titulo) throws ErrorServicio{
		
		try {
			List<Libro> libros =  servicioLibro.buscarLibroPorTitulo(titulo);
			modelo.addAttribute("libros", libros);
			
			return "/libro/libros";
			
		} catch (Exception e) {
			modelo.put("ErrorBuscar", e.getMessage());
			// devolvemos los valores ingresados al formulario
		    modelo.put("titulo", titulo);
			
			return "/libro/libros";
		}		
	}	
	
	@GetMapping("/agregarLibro")
	public String agregarLibro(ModelMap modelo) {
		List<Autor> autores = servicioAutor.listarAutores();
		List<Editorial> editoriales = servicioEditorial.listarEditoriales();
		modelo.addAttribute("autores",autores);
		modelo.addAttribute("editoriales",editoriales);
		
		return "/libro/formAgregarLibro";
	}
	
	@PostMapping("/agregarLibro") 
	public String guardarLibro(ModelMap modelo, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam String idAutor, @RequestParam String idEditorial) throws ErrorServicio{
		
		try {
			servicioLibro.crearLibro(isbn, titulo, anio, ejemplares, idAutor, idEditorial);
			
			return "redirect:/libro/libros";	
			
		} catch (ErrorServicio e) {
			modelo.put("Error", e.getMessage());
			// devolvemos los valores ingresados al formulario
			modelo.put("isbn", isbn);
			modelo.put("titulo", titulo);
			modelo.put("anio", anio);
			modelo.put("ejemplares", ejemplares);
			List<Autor> autores = servicioAutor.listarAutores();
			List<Editorial> editoriales = servicioEditorial.listarEditoriales();
			modelo.put("autores",autores);
			modelo.put("editoriales",editoriales);
			
			return "/libro/formAgregarLibro";
		}
			
	}
	
	@GetMapping("/modificarLibro/{id}")
	public String modificarLibro(ModelMap modelo, @PathVariable String id) {
		
		Optional<Libro> libros = servicioLibro.buscarLibroPorId(id);		
		List<Autor> autores = servicioAutor.listarAutores();
		List<Editorial> editoriales = servicioEditorial.listarEditoriales();
		
		modelo.addAttribute("isbn", libros.get().getIsbn());
		modelo.addAttribute("titulo", libros.get().getTitulo());
		modelo.addAttribute("anio", libros.get().getAnio());
		modelo.addAttribute("ejemplares", libros.get().getEjemplares());
		modelo.addAttribute("ejemplaresPrestados", libros.get().getEjemplaresPrestados());
		modelo.addAttribute("autores", autores);
		modelo.addAttribute("editoriales", editoriales);
				
		return "/libro/formModificarLibro";
	}
	
	@PostMapping("/modificarLibro") 
	public String editarLibro(ModelMap modelo, @RequestParam String id, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam String idAutor, @RequestParam String idEditorial) throws ErrorServicio{
		
		try {
			servicioLibro.modificarLibro(id, isbn, titulo, anio, ejemplares, ejemplaresPrestados, idAutor, idEditorial);
			
			return "redirect:/libro/libros";	
			
		} catch (ErrorServicio e) {
			modelo.put("Error", e.getMessage());
			// devolvemos los valores ingresados al formulario
			modelo.put("id", id);
			modelo.put("isbn", isbn);
			modelo.put("titulo", titulo);
			modelo.put("anio", anio);
			modelo.put("ejemplares", ejemplares);
			modelo.put("ejemplaresPrestados", ejemplaresPrestados);
			List<Autor> autores = servicioAutor.listarAutores();
			List<Editorial> editoriales = servicioEditorial.listarEditoriales();
			modelo.put("autores",autores);
			modelo.put("editoriales",editoriales);
			
			return "/libro/formModificarLibro";
		}
	}
	
	@GetMapping("/altaLibro/{id}")
	public String altaLibro(@PathVariable String id) {
		
		try {
			servicioLibro.libroAlta(id);
			
			return "redirect:/libros";	
			
		} catch (Exception e) {
			
			return "redirect:/libros";
		}
	}
	
	@GetMapping("/bajaLibro/{id}")
	public String bajaLibro(@PathVariable String id) {
				
		try {
			servicioLibro.libroBaja(id);
			
			return "redirect:/libros";
			
		} catch (Exception e) {
			
			return "redirect:/libros";
		}
				
	}
	
	@GetMapping("/eliminarLibroPorId/{id}")
	public String eliminarLibroPorId(@PathVariable String id) {
				
		try {
			servicioLibro.eliminarLibroPorId(id);
			
			return "redirect:/libros";	
			
		} catch (Exception e) {
			
			return "redirect:/libros";
		}
		
	}

}
