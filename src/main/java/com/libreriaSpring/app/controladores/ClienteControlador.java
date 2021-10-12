package com.libreriaSpring.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.libreriaSpring.app.entidades.Cliente;
import com.libreriaSpring.app.servicios.ClienteServicio;


@Controller
@RequestMapping("/clientes")
public class ClienteControlador {

	@Autowired
	private ClienteServicio servicioCliente;
	
	@GetMapping()
	public String listarClientes(ModelMap modelo) {
		List<Cliente> clientes = servicioCliente.listarClientes();
		// modelo.addAttribute vamos a enviar toda la lista a la tabla
		modelo.addAttribute("clientes", clientes);
		
		return "clientes";
	}
}
