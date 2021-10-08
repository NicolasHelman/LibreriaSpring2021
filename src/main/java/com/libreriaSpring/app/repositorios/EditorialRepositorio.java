package com.libreriaSpring.app.repositorios;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.libreriaSpring.app.entidades.Editorial;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String>{
	
	@Query("SELECT e FROM Editorial e WHERE e.alta = true")
	public List<Editorial> buscarEditorialesActivos();
	
	@Query("SELECT e FROM Editorial e WHERE e.nombre LIKE %:nombre%")
	public List<Editorial> buscarEditorialPorNombre(@Param("nombre") String nombre);
}
