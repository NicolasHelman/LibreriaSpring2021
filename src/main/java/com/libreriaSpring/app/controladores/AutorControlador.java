package com.libreriaSpring.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.libreriaSpring.app.entidades.Autor;
import com.libreriaSpring.app.servicios.AutorServicio;

@Controller
@RequestMapping
public class AutorControlador {
	
	@Autowired
	private AutorServicio servicio;
		
	@GetMapping("/autores")
	public String listarLibros(Model model) {
		List<Autor> autores = servicio.listarAutores();
		// model.add vamos a enviar toda la lista a la tabla
		model.addAttribute("autores", autores);
		return "autores";
	}
	
}