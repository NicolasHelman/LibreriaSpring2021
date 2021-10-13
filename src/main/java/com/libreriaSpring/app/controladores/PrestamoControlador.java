package com.libreriaSpring.app.controladores;

import java.util.Date;
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
import com.libreriaSpring.app.entidades.Libro;
import com.libreriaSpring.app.entidades.Prestamo;
import com.libreriaSpring.app.errores.ErrorServicio;
import com.libreriaSpring.app.servicios.ClienteServicio;
import com.libreriaSpring.app.servicios.LibroServicio;
import com.libreriaSpring.app.servicios.PrestamoServicio;


@Controller
@RequestMapping("/prestamos")
public class PrestamoControlador {

	@Autowired
	private PrestamoServicio servicioPrestamo;
	
	@Autowired
	private ClienteServicio servicioCliente;
	
	@Autowired
	private LibroServicio servicioLibro;
	
	@GetMapping()
	public String listarPrestamos(ModelMap modelo) {
		List<Prestamo> prestamos = servicioPrestamo.listarPrestamos();
		// modelo.addAttribute vamos a enviar toda la lista a la tabla
		modelo.addAttribute("prestamos", prestamos);
		
		return "prestamos";
	}
	
	@PostMapping("/buscarPrestamo")
	public String listarPrestamosPorNombre(ModelMap modelo, @RequestParam String nombre) throws ErrorServicio{
		
		try {
			List<Prestamo> prestamos =  servicioPrestamo.buscarPrestamoPorNombre(nombre);
			modelo.addAttribute("prestamos", prestamos);
			
			return "prestamos";
			
		} catch (Exception e) {
			modelo.put("ErrorBuscar", e.getMessage());
			// devolvemos los valores ingresados al formulario
		    modelo.put("nombre", nombre);
			
			return "prestamos";
		}		
	}	
	
	@SuppressWarnings("deprecation")
	@GetMapping("/agregarPrestamo")
	public String agregarPrestamo(ModelMap modelo) {
		List<Cliente> clientes = servicioCliente.listarClientes();
		List<Libro> libros = servicioLibro.listarLibros();
		modelo.addAttribute("clientes",clientes);
		modelo.addAttribute("libros",libros);
		Date fechaPrestamo = new Date();
		modelo.addAttribute("fechaPrestamo",fechaPrestamo.toLocaleString());
				
		return "formAgregarPrestamo";
	}
	
	@SuppressWarnings("deprecation")
	@PostMapping("/agregarPrestamo") 
	public String guardarPrestamo(ModelMap modelo, @RequestParam String idCliente, @RequestParam String idLibro, @RequestParam Date fechaDevolucion) throws ErrorServicio{
		
		try {
			servicioPrestamo.crearPrestamo(idCliente, idLibro, fechaDevolucion);
			
			return "redirect:/prestamos";	
			
		} catch (ErrorServicio e) {
			modelo.put("Error", e.getMessage());
			// devolvemos los valores ingresados al formulario
			List<Cliente> clientes = servicioCliente.listarClientes();
			List<Libro> libros = servicioLibro.listarLibros();
			modelo.put("clientes",clientes);
			modelo.put("libros",libros);
			Date fechaPrestamo = new Date();
			modelo.addAttribute("fechaPrestamo",fechaPrestamo.toLocaleString());
			modelo.put("fechaDevolucion", fechaDevolucion);
			
			return "formAgregarPrestamo";
		}		
	}
	
	@GetMapping("/modificarPrestamo/{id}")
	public String modificarPrestamo(ModelMap modelo, @PathVariable String id) {
		
		Optional<Prestamo> prestamos = servicioPrestamo.buscarPrestamoPorId(id);		
		List<Cliente> clientes = servicioCliente.listarClientes();
		List<Libro> libros = servicioLibro.listarLibros();
		
		modelo.addAttribute("clientes",clientes);
		modelo.addAttribute("libros",libros);
		modelo.addAttribute("fechaPrestamo",prestamos.get().getFechaPrestamo());
		modelo.addAttribute("fechaDevolucion",prestamos.get().getFechaDevolucion());

		return "formModificarPrestamo";
	}
	
	@PostMapping("/modificarPrestamo") 
	public String editarPrestamo(ModelMap modelo, @RequestParam String id, @RequestParam String idCliente, @RequestParam String idLibro, @RequestParam Date fechaPrestamo, @RequestParam Date fechaDevolucion) throws ErrorServicio{
		
		try {
			servicioPrestamo.modificarPrestamo(id, idCliente, idLibro, fechaPrestamo, fechaDevolucion);
			
			return "redirect:/libros";	
			
		} catch (ErrorServicio e) {
			modelo.put("Error", e.getMessage());
			// devolvemos los valores ingresados al formulario
			List<Cliente> clientes = servicioCliente.listarClientes();
			List<Libro> libros = servicioLibro.listarLibros();
			modelo.put("clientes",clientes);
			modelo.put("libros",libros);
			
			
			return "formModificarPrestamo";
		}
	}
	
	@GetMapping("/altaPrestamo/{id}")
	public String altaPrestamo(@PathVariable String id) {
		
		try {
			servicioPrestamo.prestamoAlta(id);
			
			return "redirect:/prestamos";	
			
		} catch (Exception e) {
			
			return "redirect:/prestamos";	
		}
	}
	
	@GetMapping("/bajaPrestamo/{id}")
	public String bajaPrestamo(@PathVariable String id) {
				
		try {
			servicioPrestamo.prestamoBaja(id);
			
			return "redirect:/prestamos";	
			
		} catch (Exception e) {
			
			return "redirect:/prestamos";	
		}
				
	}
	
	@GetMapping("/eliminarPrestamoPorId/{id}")
	public String eliminarPrestamoPorId(@PathVariable String id) {
				
		try {
			servicioPrestamo.eliminarPrestamoPorId(id);
			
			return "redirect:/prestamos";		
			
		} catch (Exception e) {
			
			return "redirect:/prestamos";	
		}
		
	}
}
