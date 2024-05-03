package com.meethub.project.models;

public class PonerContraDTO {

	private String contrasena;
	private String contrasenaRepetida;
	
	public PonerContraDTO(String pass, String passRepeat) {
		this.contrasena = pass;
		this.contrasenaRepetida = passRepeat;
	}
	
	public PonerContraDTO() {
	}
	
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String pass) {
		this.contrasena = pass;
	}
	public String getContrasenaRepetida() {
		return contrasenaRepetida;
	}
	public void setContrasenaRepetida(String passRepeat) {
		this.contrasenaRepetida = passRepeat;
	}
	
	
	
}
