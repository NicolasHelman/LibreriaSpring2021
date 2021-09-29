package com.libreriaSpring.app.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "autor")
public class Autor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String nombre;
    private Boolean alta;
    
    public Autor() {
		// TODO Auto-generated constructor stub
	}
       
    
	public Autor(String id, String nombre, Boolean alta) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.alta = alta;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Boolean getAlta() {
		return alta;
	}
	public void setAlta(Boolean alta) {
		this.alta = alta;
	}
    
    

}
