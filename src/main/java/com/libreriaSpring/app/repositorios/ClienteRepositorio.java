package com.libreriaSpring.app.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.libreriaSpring.app.entidades.Cliente;


@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String> {

	@Query("SELECT c FROM Cliente c WHERE c.alta = :true")
	public List<Cliente> buscarClientesActivos();
	
	@Query("SELECT c FROM Cliente c WHERE c.nombre LIKE %:nombre%")
	public List<Cliente> buscarClientePorNombre(@Param("nombre") String nombre);
	
	@Query("SELECT c FROM Cliente c WHERE c.nombre LIKE :nombre")
	public Cliente validarNombreCliente(@Param("nombre") String nombre);
	
	@Query("SELECT c FROM Cliente c WHERE c.dni LIKE :dni")
	public Cliente validarDniCliente(@Param("dni") Long dni);
	
	@Query("SELECT c FROM Cliente c WHERE c.telefono LIKE :telefono")
	public Cliente validarTelefonoCliente(@Param("telefono") String telefono);
	
}
