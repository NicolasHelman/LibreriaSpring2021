package com.libreriaSpring.app.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.libreriaSpring.app.entidades.Prestamo;

@Repository
public interface PrestamoRepositorio extends JpaRepository<Prestamo, String> {
	
	@Query("SELECT p FROM Prestamo p WHERE p.alta = :true")
	public List<Prestamo> buscarPrestamosActivos();
	
	@Query("SELECT p FROM Prestamo p WHERE p.cliente.nombre LIKE %:nombre%")
	public List<Prestamo> buscarPrestamoPorNombre(@Param("nombre") String nombre);
	
}
