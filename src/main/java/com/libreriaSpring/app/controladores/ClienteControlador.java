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
import com.libreriaSpring.app.entidades.Cliente;
import com.libreriaSpring.app.errores.ErrorServicio;
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
	
	@PostMapping("/buscarCliente")
	public String listarClientesPorNombre(ModelMap modelo, @RequestParam String nombre) throws ErrorServicio{
		
		try {
			List<Cliente> clientes =  servicioCliente.buscarClientePorNombre(nombre);
			modelo.addAttribute("clientes", clientes);
			
			return "clientes";
			
		} catch (Exception e) {
			modelo.put("ErrorBuscar", e.getMessage());
			// devolvemos los valores ingresados al formulario
		    modelo.put("nombre", nombre);
			
			return "clientes";
		}		
	}	
	
	@GetMapping("/agregarCliente")
	public String agregarCliente(ModelMap modelo) {
		List<Cliente> clientes = servicioCliente.listarClientes();
		modelo.addAttribute("clientes", clientes);
		
		return "formAgregarCliente";
	}
	
	@PostMapping("/agregarCliente") 
	public String guardarCliente(ModelMap modelo, @RequestParam String nombre, @RequestParam Long dni, @RequestParam String telefono) throws ErrorServicio {
		
		try {
			servicioCliente.crearCliente(nombre, dni, telefono);
			
			return "redirect:/clientes";	
			
		} catch (ErrorServicio e) {
			modelo.put("Error", e.getMessage());
			// devolvemos los valores ingresados al formulario
			modelo.put("nombre", nombre);
			modelo.put("dni", dni);
			modelo.put("telefono", telefono);
			
			
			return "formAgregarCliente";
		}
	}
	
	@GetMapping("/modificarCliente/{id}")
	public String modificarCliente(ModelMap modelo, @PathVariable String id) {
		
		Optional<Cliente> clientes = servicioCliente.buscarClientePorId(id);			

		modelo.addAttribute("nombre", clientes.get().getNombre());
		modelo.addAttribute("dni", clientes.get().getDni());
		modelo.addAttribute("telefono",clientes.get().getTelefono());
				
		return "formModificarCliente";
	}
	
	@PostMapping("/modificarCliente") 
	public String editarCliente(ModelMap modelo, @RequestParam String id, @RequestParam String nombre, @RequestParam Long dni, @RequestParam String telefono) throws ErrorServicio{
		
		try {
			servicioCliente.modificarCliente(id, nombre, dni, telefono);
			
			return "redirect:/clientes";	
			
		} catch (ErrorServicio e) {
			modelo.put("Error", e.getMessage());
			// devolvemos los valores ingresados al formulario
			modelo.put("id", id);
			modelo.put("nombre", nombre);
			modelo.put("dni", dni);
			modelo.put("telefono", telefono);
					
			return "formModificarCliente";
		}
	}
	
	@GetMapping("/altaCliente/{id}")
	public String altaCliente(@PathVariable String id) {
		
		try {
			servicioCliente.clienteAlta(id);
			
			return "redirect:/clientes";	
			
		} catch (Exception e) {
			
			return "redirect:/clientes";
		}
	}
	
	@GetMapping("/bajaCliente/{id}")
	public String bajaCliente(@PathVariable String id) {
				
		try {
			servicioCliente.clienteBaja(id);
			
			return "redirect:/clientes";	
			
		} catch (Exception e) {
			
			return "redirect:/clientes";
		}		
	}
	
	@GetMapping("/eliminarClientePorId/{id}")
	public String eliminarClientePorId(@PathVariable String id) {
				
		try {
			servicioCliente.eliminarClientePorId(id);
			
			return "redirect:/clientes";	
			
		} catch (Exception e) {
			
			return "redirect:/clientes";
		}
	}
	
	
}
