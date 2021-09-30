package com.libreriaSpring.app.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.libreriaSpring.app.entidades.Libro;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro,String>{	
	
}
