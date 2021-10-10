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
import com.libreriaSpring.app.errores.ErrorServicio;
import com.libreriaSpring.app.servicios.AutorServicio;

@Controller
@RequestMapping("/autores")
public class AutorControlador {
	
	@Autowired
	private AutorServicio servicioAutor;
		
	@GetMapping()
	public String listarAutores(ModelMap modelo) {
		List<Autor> autores = servicioAutor.listarAutores();
		// modelo.addAttribute vamos a enviar toda la lista a la tabla
		modelo.addAttribute("autores", autores);
		
		return "autores";
	}
	
	@PostMapping("/buscarAutor")
	public String listarAutoresPorNombre(ModelMap modelo, @RequestParam String nombre) throws ErrorServicio{
		
		try {
			List<Autor> autores =  servicioAutor.buscarAutorPorNombre(nombre);
			modelo.addAttribute("autores", autores);
			
			return "autores";
			
		} catch (Exception e) {
			modelo.put("ErrorBuscar", e.getMessage());
			// devolvemos los valores ingresados al formulario
		    modelo.put("nombre", nombre);
			
			return "autores";
		}		
	}	
	
	@GetMapping("/agregarAutor")
	public String agregarAutor(ModelMap modelo) {
		List<Autor> autores = servicioAutor.listarAutores();
		modelo.addAttribute("autores", autores);
		
		return "formAgregarAutor";
	}
	
	@PostMapping("/agregarAutor") 
	public String guardarAutor(ModelMap modelo, @RequestParam String nombre) throws ErrorServicio {
		
		try {
			servicioAutor.crearAutor(nombre);
			
			return "redirect:/autores";	
			
		} catch (ErrorServicio e) {
			modelo.put("Error", e.getMessage());
			// devolvemos los valores ingresados al formulario
			modelo.put("nombre", nombre);
			
			return "formAgregarAutor";
		}
	}
	
	@GetMapping("/modificarAutor/{id}")
	public String modificarAutor(ModelMap modelo, @PathVariable String id) {
		
		Optional<Autor> autores = servicioAutor.buscarAutorPorId(id);			

		modelo.addAttribute("nombre", autores.get().getNombre());
				
		return "formModificarAutor";
	}
	
	@PostMapping("/modificarAutor") 
	public String editarLibro(ModelMap modelo, @RequestParam String id,  @RequestParam String nombre) throws ErrorServicio{
		
		try {
			servicioAutor.modificarAutor(id, nombre);
			
			return "redirect:/autores";	
			
		} catch (ErrorServicio e) {
			modelo.put("Error", e.getMessage());
			// devolvemos los valores ingresados al formulario
			modelo.put("nombre", nombre);
					
			return "formModificarAutor";
		}
	}
	
	@GetMapping("/altaAutor/{id}")
	public String altaAutor(@PathVariable String id) {
		
		try {
			servicioAutor.autorAlta(id);
			
			return "redirect:/autores";	
			
		} catch (Exception e) {
			
			return "redirect:/autores";
		}
	}
	
	@GetMapping("/bajaAutor/{id}")
	public String bajaAutor(@PathVariable String id) {
				
		try {
			servicioAutor.autorBaja(id);
			
			return "redirect:/autores";	
			
		} catch (Exception e) {
			
			return "redirect:/autores";
		}		
	}
	
	@GetMapping("/eliminarAutorPorId/{id}")
	public String eliminarAutorPorId(@PathVariable String id) {
				
		try {
			servicioAutor.eliminarAutorPorId(id);
			
			return "redirect:/autores";	
			
		} catch (Exception e) {
			
			return "redirect:/autores";
		}
	}
	
	
	
}