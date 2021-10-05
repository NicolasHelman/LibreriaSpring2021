package com.libreriaSpring.app.errores;

public class ErrorServicio extends Exception {
	
	private static final long serialVersionUID = 7883636384872015753L;
	
	public ErrorServicio(String msn) {
		super(msn);
	}
}
