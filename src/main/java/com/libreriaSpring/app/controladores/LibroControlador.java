package com.libreriaSpring.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.libreriaSpring.app.entidades.Libro;
import com.libreriaSpring.app.servicios.LibroServicio;

@Controller
@RequestMapping
public class LibroControlador {
	
	@Autowired
	private LibroServicio servicio;
		
	@GetMapping("/libros")
	public String listarLibros(Model model) {
		List<Libro> libros = servicio.listarLibros();
		// model.add vamos a enviar toda la lista a la tabla
		model.addAttribute("libros", libros);
		return "libros";
	}
	
}
