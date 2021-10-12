package com.libreriaSpring.app.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.libreriaSpring.app.entidades.Cliente;
import com.libreriaSpring.app.repositorios.ClienteRepositorio;

@Service
public class ClienteServicio {
	
	@Autowired
	private ClienteRepositorio dataCliente;
	
	
	
	@Transactional(readOnly = true)
	public List<Cliente> listarClientes() {	
		return dataCliente.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Cliente> listarClientesActivos() {
		return dataCliente.buscarClientesActivos();
	}
}
