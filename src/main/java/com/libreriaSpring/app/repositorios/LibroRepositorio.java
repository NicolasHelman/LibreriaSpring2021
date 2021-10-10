package com.libreriaSpring.app.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.libreriaSpring.app.entidades.Libro;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String>{	
	
	@Query("SELECT l FROM Libro l WHERE l.alta = :true")
	public List<Libro> buscarLibrosActivos();
	
	@Query("SELECT l FROM Libro l WHERE l.titulo LIKE %:titulo%")
	public List<Libro> buscarLibroPorTitulo(@Param("titulo") String titulo);
	
	@Query("SELECT l FROM Libro l INNER JOIN l.editorial e WHERE l.titulo LIKE :titulo AND e.nombre LIKE :nombre")
	public Libro validarTituloEditorial(@Param("titulo") String titulo, @Param("nombre") String nombre);
	
	@Query("SELECT l FROM Libro l WHERE l.isbn LIKE :isbn")
	public Libro validarIsbn(@Param("isbn") Long isbn);
	
}
