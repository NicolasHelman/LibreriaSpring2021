package com.libreriaSpring.app.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.libreriaSpring.app.entidades.Libro;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro,String>{	
	
	@Query("SELECT l FROM Libro l WHERE l.alta = true")
	public List<Libro> buscarLibrosActivos();
	
	@Query("SELECT COUNT(l) > 0 FROM Libro l WHERE l.titulo = :titulo")
	public Boolean existsByTitle(@Param("titulo") String titulo);
}
