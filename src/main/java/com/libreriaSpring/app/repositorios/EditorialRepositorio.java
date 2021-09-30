package com.libreriaSpring.app.repositorios;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.libreriaSpring.app.entidades.Editorial;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String>{
	
}
