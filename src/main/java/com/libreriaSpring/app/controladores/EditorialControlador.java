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
import com.libreriaSpring.app.entidades.Editorial;
import com.libreriaSpring.app.errores.ErrorServicio;
import com.libreriaSpring.app.servicios.EditorialServicio;

@Controller
@RequestMapping("/editoriales")
public class EditorialControlador {
	
	@Autowired
	private EditorialServicio servicioEditorial;
		
	@GetMapping()
	public String listarEditoriales(ModelMap modelo) {
		List<Editorial> editoriales = servicioEditorial.listarEditoriales();
		// modelo.addAttribute vamos a enviar toda la lista a la tabla
		modelo.addAttribute("editoriales", editoriales);
		
		return "editoriales";
	}
	
	@GetMapping("/agregarEditorial")
	public String agregarEditorial(ModelMap modelo) {
		List<Editorial> editoriales = servicioEditorial.listarEditoriales();
		modelo.addAttribute("editoriales", editoriales);
		
		return "formAgregarEditorial";
	}
	
	@PostMapping("/agregarEditorial") 
	public String guardarEditorial(ModelMap modelo, @RequestParam String nombre) throws ErrorServicio{	
		
		try {
			servicioEditorial.crearEditorial(nombre);
			
			return "redirect:/editoriales";	
			
		} catch (ErrorServicio e) {
			modelo.put("Error", e.getMessage());
			// devolvemos los valores ingresados al formulario
			modelo.put("nombre", nombre);
			
			return "formAgregarEditorial";
		}
	}
	
	@GetMapping("/modificarEditorial/{id}")
	public String modificarEditorial(ModelMap modelo, @PathVariable String id) {
		
		Optional<Editorial> editoriales = servicioEditorial.buscarEditorialPorId(id);			

		modelo.addAttribute("nombre", editoriales.get().getNombre());
				
		return "formModificarEditorial";
	}
	
	@PostMapping("/modificarEditorial") 
	public String editarEditorial(ModelMap modelo, @RequestParam String id,  @RequestParam String nombre) throws ErrorServicio{
		
		try {
			servicioEditorial.modificarEditorial(id, nombre);
			
			return "redirect:/editoriales";
			
		} catch (ErrorServicio e) {
			modelo.put("Error", e.getMessage());
			// devolvemos los valores ingresados al formulario
			modelo.put("nombre", nombre);
					
			return "formAgregarEditorial";
		}	
	}
	
	@GetMapping("/altaEditorial/{id}")
	public String altaEditorial(@PathVariable String id) {
		
		try {
			servicioEditorial.editorialAlta(id);
			
			return "redirect:/editoriales";	
			
		} catch (Exception e) {
			
			return "redirect:/editoriales";
		}
	}
	
	@GetMapping("/bajaEditorial/{id}")
	public String bajaEditorial(@PathVariable String id) {
				
		try {
			servicioEditorial.editorialBaja(id);
			
			return "redirect:/editoriales";	
			
		} catch (Exception e) {
			
			return "redirect:/editoriales";
		}
		
				
	}
	
	@GetMapping("/eliminarEditorialPorId/{id}")
	public String eliminarEditorialPorId(@PathVariable String id) {
				
		try {
			servicioEditorial.eliminarEditorialPorId(id);
			
			return "redirect:/editoriales";	
			
		} catch (Exception e) {
			return "redirect:/editoriales";
		}		
	}

}