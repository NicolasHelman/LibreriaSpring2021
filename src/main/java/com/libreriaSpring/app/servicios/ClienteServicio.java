package com.libreriaSpring.app.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.libreriaSpring.app.entidades.Cliente;
import com.libreriaSpring.app.errores.ErrorServicio;
import com.libreriaSpring.app.repositorios.ClienteRepositorio;

@Service
public class ClienteServicio {
	
	@Autowired
	private ClienteRepositorio dataCliente;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void crearCliente(String nombre, Long dni, String telefono) throws ErrorServicio{
		
		validarCrearCliente(nombre,dni,telefono);
		
		Cliente c = new Cliente();
		
		c.setNombre(nombre);
		c.setDni(dni);
		c.setTelefono(telefono);
		c.setAlta(true);
		
		dataCliente.save(c);
			
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void modificarCliente(String id, String nombre, Long dni, String telefono) throws ErrorServicio {
			
		validarModificarCliente(nombre,dni,telefono);
		
		Optional<Cliente> clientes = dataCliente.findById(id);		
		
		if (clientes.isPresent()) {
			Cliente c = clientes.get();
			if(c.getId().equals(id)) {
				c.setNombre(nombre);
				c.setDni(dni);
				c.setTelefono(telefono);
				
				dataCliente.save(c);
			}else {
				throw new ErrorServicio("*No puede realizar la modificacion");
			}		
		}else {
			throw new ErrorServicio("*No se encontró el cliente solicitado");
		}
	}
	
	
	@Transactional(readOnly = true)
	public List<Cliente> listarClientes() {	
		return dataCliente.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Cliente> listarClientesActivos() {
		return dataCliente.buscarClientesActivos();
	}
	
	@Transactional(readOnly = true)
	public List<Cliente> buscarClientePorNombre(String nombre) throws ErrorServicio {
		
		List<Cliente> cliente = dataCliente.buscarClientePorNombre(nombre);
		
		if(!cliente.isEmpty()) {
			return cliente;
		}else {
			throw new ErrorServicio("*No se encontró el nombre del cliente");
		}	
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Optional<Cliente> buscarClientePorId(String id) {
		return dataCliente.findById(id);
	}	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Cliente clienteAlta(String id) throws ErrorServicio{

		Optional<Cliente> clientes = dataCliente.findById(id);	
		
		if (clientes.isPresent()) {
			Cliente c = clientes.get();

			c.setAlta(true);
			
			return dataCliente.save(c);
		}else {
			throw new ErrorServicio("No se encontró el cliente solicitado");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Cliente clienteBaja(String id) throws ErrorServicio{

		Optional<Cliente> clientes = dataCliente.findById(id);	
		
		if (clientes.isPresent()) {
			Cliente c = clientes.get();

			c.setAlta(false);
			
			return dataCliente.save(c);
		}else {
			throw new ErrorServicio("No se encontró el cliente solicitado");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminarCliente(Cliente cliente) {
		dataCliente.delete(cliente);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminarClientePorId(String id) throws ErrorServicio {
		dataCliente.deleteById(id);
	}
	
	public void validarCrearCliente(String nombre, Long dni, String telefono) throws ErrorServicio{	
		if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
			throw new ErrorServicio("*El nombre del cliente está incompleto");
		}
		if (dataCliente.validarNombreCliente(nombre) != null) {
			throw new ErrorServicio("*Ya existe un cliente con el mismo nombre");
		}	
		if (dni == null) {
			throw new ErrorServicio("*El dni del cliente está incompleto");
		}
		if (dni.toString().length() != 8) {
			throw new ErrorServicio("*El dni debe ser de 8 dígitos");
		}
		if (dataCliente.validarDniCliente(dni) != null) {
			throw new ErrorServicio("*Ya existe un cliente con el mismo dni");
		}	
		if (telefono == null || telefono.isEmpty() || telefono.contains("  ")) {
			throw new ErrorServicio("*El telefono del cliente está incompleto");
		}
		if (telefono.length() != 10) {
			throw new ErrorServicio("*El teléfono debe ser de 10 dígitos");
		}
		if (dataCliente.validarTelefonoCliente(telefono) != null) {
			throw new ErrorServicio("*Ya existe un cliente con el mismo teléfono");
		}
	}
	
	public void validarModificarCliente(String nombre, Long dni, String telefono) throws ErrorServicio{	
		if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
			throw new ErrorServicio("*El nombre del cliente está incompleto");
		}
		if (dni == null) {
			throw new ErrorServicio("*El dni del cliente está incompleto");
		}
		if (dni.toString().length() != 8) {
			throw new ErrorServicio("*El dni debe ser de 8 dígitos");
		}
		if (telefono == null || telefono.isEmpty() || telefono.contains("  ")) {
			throw new ErrorServicio("*El telefono del cliente está incompleto");
		}
		if (telefono.length() != 10) {
			throw new ErrorServicio("*El teléfono debe ser de 10 dígitos");
		}
	}
	
}
