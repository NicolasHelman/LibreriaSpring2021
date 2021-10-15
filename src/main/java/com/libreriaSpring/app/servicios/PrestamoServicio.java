package com.libreriaSpring.app.servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.libreriaSpring.app.entidades.Cliente;
import com.libreriaSpring.app.entidades.Libro;
import com.libreriaSpring.app.entidades.Prestamo;
import com.libreriaSpring.app.errores.ErrorServicio;
import com.libreriaSpring.app.repositorios.ClienteRepositorio;
import com.libreriaSpring.app.repositorios.LibroRepositorio;
import com.libreriaSpring.app.repositorios.PrestamoRepositorio;

@Service
public class PrestamoServicio {

	@Autowired
	private PrestamoRepositorio dataPrestamo;
	
	@Autowired
	private ClienteRepositorio dataCliente;
	
	@Autowired
	private LibroRepositorio dataLibro;
	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void crearPrestamo(String idCliente, String idLibro, Date fechaDevolucion) throws ErrorServicio{
		
		@SuppressWarnings("deprecation")
		Cliente c = dataCliente.getOne(idCliente);
		
		@SuppressWarnings("deprecation")
		Libro l = dataLibro.getOne(idLibro);
		
		Date hoy = new Date();
		
		validarPrestamo(c,l,hoy,fechaDevolucion);
				

		if (l.getEjemplaresRestantes() > 0) {
			Prestamo p = new Prestamo();
			p.setCliente(c);
			p.setLibro(l);	
			p.setFechaPrestamo(hoy);
			p.setFechaDevolucion(fechaDevolucion);	
			p.setAlta(true);
            p.getLibro().setEjemplaresPrestados(p.getLibro().getEjemplaresPrestados() + 1);
    		p.getLibro().setEjemplaresRestantes(p.getLibro().getEjemplaresRestantes() - 1);
    		
    		dataPrestamo.save(p);
        } else {
        	throw new ErrorServicio("*No hay stock del libro '"+l.getTitulo()+"'");
        }

	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void devolverPrestamo(String id) throws ErrorServicio{
		
					
		Optional<Prestamo> respuesta = dataPrestamo.findById(id);
		Prestamo p = respuesta.get();
				
		p.getLibro().setEjemplaresPrestados(p.getLibro().getEjemplaresPrestados() - 1);
		p.getLibro().setEjemplaresRestantes(p.getLibro().getEjemplaresRestantes() + 1);
				
		dataPrestamo.save(p);	
				
	    dataPrestamo.deleteById(id);

	}	
	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void modificarPrestamo(String id, String idCliente, String idLibro, Date fechaPrestamo, Date fechaDevolucion) throws ErrorServicio{
		
		@SuppressWarnings("deprecation")
		Cliente c = dataCliente.getOne(idCliente);
		@SuppressWarnings("deprecation")
		Libro l = dataLibro.getOne(idLibro);
		
		validarPrestamo(c,l,fechaPrestamo,fechaDevolucion);
		
		Optional<Prestamo> respuesta = dataPrestamo.findById(id);
		
		if (respuesta.isPresent()) {
			Prestamo p = respuesta.get();
			if (p.getId().equals(id)) {
				p.setCliente(c);
				p.setLibro(l);
				p.setFechaPrestamo(fechaPrestamo);
				p.setFechaDevolucion(fechaDevolucion);
				p.setAlta(true);
				
				dataPrestamo.save(p);			
			}else {
				throw new ErrorServicio("*No puede realizar la modificacion");
			}		
		}else {
			throw new ErrorServicio("No se encontró el prestamo solicitado");
		}
	}	
	
	
	@Transactional(readOnly = true)
	public List<Prestamo> listarPrestamos() {	
		return dataPrestamo.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Prestamo> listarPrestamosActivos() {
		return dataPrestamo.buscarPrestamosActivos();
	}
	
	@Transactional(readOnly = true)
	public List<Prestamo> buscarPrestamoPorNombre(String nombre) throws ErrorServicio {
		
		List<Prestamo> prestamo = dataPrestamo.buscarPrestamoPorNombre(nombre);
		
		if(!prestamo.isEmpty()) {
			return prestamo;
		}else {
			throw new ErrorServicio("*No se encontró el préstamo");
		}	
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Optional<Prestamo> buscarPrestamoPorId(String id) {
		return dataPrestamo.findById(id);
	}	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Prestamo prestamoAlta(String id) throws ErrorServicio{

		Optional<Prestamo> prestamos = dataPrestamo.findById(id);	
		
		if (prestamos.isPresent()) {
			Prestamo p = prestamos.get();

			p.setAlta(true);
			
			return dataPrestamo.save(p);
		}else {
			throw new ErrorServicio("No se encontro el prestamo solicitado");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Prestamo prestamoBaja(String id) throws ErrorServicio{

		Optional<Prestamo> prestamos = dataPrestamo.findById(id);	
		
		if (prestamos.isPresent()) {
			Prestamo p = prestamos.get();

			p.setAlta(false);
			
			return dataPrestamo.save(p);
		}else {
			throw new ErrorServicio("No se encontro el prestamo solicitado");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminarPrestamo(Prestamo prestamo) {
		dataPrestamo.delete(prestamo);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminarPrestamoPorId(String id) throws ErrorServicio {
		dataPrestamo.deleteById(id);
	}
	
	public void validarPrestamo(Cliente cliente, Libro libro, Date fechaPrestamo, Date fechaDevolucion) throws ErrorServicio{
		if(cliente.toString() == null) {
			throw new ErrorServicio("*No se encontró el cliente solicitado");
		}	
		if(libro.toString() == null) {
			throw new ErrorServicio("*No se encontró el libro solicitada");
		}	
		if(fechaDevolucion.after(fechaPrestamo) == false) {
			throw new ErrorServicio("*La fecha devolución es anterior o igual a la fecha prestamo");
		}
		
	}
	
}
