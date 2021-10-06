package com.libreriaSpring.app.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/libreria","/"})
public class Controlador {
	
	@GetMapping()
	public String index() {			
		return "index.html";
	}
	
}
