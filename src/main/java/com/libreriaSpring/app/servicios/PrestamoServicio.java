package com.libreriaSpring.app.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.libreriaSpring.app.entidades.Prestamo;
import com.libreriaSpring.app.repositorios.PrestamoRepositorio;

@Service
public class PrestamoServicio {

	@Autowired
	private PrestamoRepositorio dataPrestamo;
	
	
	
	@Transactional(readOnly = true)
	public List<Prestamo> listarPrestamos() {	
		return dataPrestamo.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Prestamo> listarPrestamosActivos() {
		return dataPrestamo.buscarPrestamosActivos();
	}
}
