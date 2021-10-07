package com.libreriaSpring.app.repositorios;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.libreriaSpring.app.entidades.Autor;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor,String>{
	
	@Query("SELECT a FROM Autor a WHERE a.alta = :true")
	public List<Autor> buscarAutoresActivos();
	
	@Query("SELECT a FROM Autor a WHERE a.nombre = :nombre")
	public Optional<Autor> buscarAutorPorNombre(@Param("nombre") String nombre);

}
