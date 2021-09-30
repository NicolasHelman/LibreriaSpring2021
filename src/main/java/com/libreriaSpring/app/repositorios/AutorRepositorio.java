package com.libreriaSpring.app.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.libreriaSpring.app.entidades.Autor;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor,String>{
	
}
