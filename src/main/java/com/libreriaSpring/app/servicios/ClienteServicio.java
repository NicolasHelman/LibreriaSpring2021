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
		
		Cliente c = new Cliente();
		
		c.setNombre(nombre);
		c.setDni(dni);
		c.setTelefono(telefono);
		c.setAlta(true);
		
		dataCliente.save(c);
			
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void modificarCliente(String id, String nombre, Long dni, String telefono) throws ErrorServicio {
		
		
		
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
			throw new ErrorServicio("No se encontro el cliente solicitado");
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
			throw new ErrorServicio("No se encontro el cliente solicitado");
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
	
}
