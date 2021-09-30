package com.libreriaSpring.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.libreriaSpring.app.entidades.Editorial;
import com.libreriaSpring.app.servicios.EditorialServicio;

@Controller
@RequestMapping
public class EditorialControlador {
	
	@Autowired
	private EditorialServicio servicio;
		
	@GetMapping("/editoriales")
	public String listarEditoriales(Model model) {
		List<Editorial> editoriales = servicio.listarEditoriales();
		// model.add vamos a enviar toda la lista a la tabla
		model.addAttribute("editoriales", editoriales);
		return "editoriales";
	}
	
}