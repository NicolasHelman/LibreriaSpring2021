package com.libreriaSpring.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.libreriaSpring.app.entidades.Prestamo;
import com.libreriaSpring.app.servicios.PrestamoServicio;


@Controller
@RequestMapping("/prestamos")
public class PrestamoControlador {

	@Autowired
	private PrestamoServicio servicioPrestamo;
	
	@GetMapping()
	public String listarPrestamos(ModelMap modelo) {
		List<Prestamo> prestamos = servicioPrestamo.listarPrestamos();
		// modelo.addAttribute vamos a enviar toda la lista a la tabla
		modelo.addAttribute("prestamos", prestamos);
		
		return "prestamos";
	}
}
